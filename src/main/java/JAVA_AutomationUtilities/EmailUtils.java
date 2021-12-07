package JAVA_AutomationUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;


/**@author SAQUEEB ALI
 * @version 1.0
 * 
 * */

/**
 * EmailUtils contains method to connectGmail and ConnectOutlook
 * */
public class EmailUtils {

	
/**
*ConnectGmail method is used to connect to Gmail 
*@param p takes property file containing gmail_username and gmail_password as input 
*@return connection object of type Store
*
* */	

		public Store connectGmail(Properties p) {
		    Store store = null;
		    try {
		    	String username, password;
				Properties prop;
		      if ((username = p.getProperty("gmail_username")) == null) {
		        throw new Exception("Please provide email address in properties file using key gmail_username"); 
		      }
		      if ((password = p.getProperty("gmail_password")) == null) {
		    	  throw new Exception("Please provide email password in properties file using key gmail_password "); 
		    	}
		      (prop = new Properties()).setProperty("mail.imap.ssl.enable", "true");
		      (store = (Session.getInstance(prop)).getStore("imap")).connect("imap.gmail.com", username, password);
		    } catch (Exception e) {
		         e.printStackTrace();
		    } 
		    return store;
		  }
		/**
		*ConnectOutlook method is used to connect to Outlook 
		*@param p takes property file containing outlook_username and outlook_password as input 
		*@return connection object of type Store
		*
		* */
		public Store connectOutlook(Properties p) {
			Store store = null;
			try {
				String username, password;
				Properties prop;
				if ((username = p.getProperty("outlook_username")) == null) {
					throw new Exception("Please provide email address in properties file using key outlook_username"); 
				}
				if ((password = p.getProperty("outlook_password")) == null) {
					
					throw new Exception("Please provide email password in properties file using key outlook_password "); 
				}
				(prop = new Properties()).setProperty("mail.imap.ssl.enable", "true");
				(store = ( Session.getInstance(prop)).getStore("imap")).connect("outlook.office365.com", username, password);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return store;
		}
		/**
		 * Gets unread Messages from the folder
		 * @param connection takes the session as Store created while connecting to the email server
		 * @param folder takes the target folder name in String format from where the unread messages are to retrieved. 
		 * @throws MessagingException
		 * @return Arrays of Message retrieved from the given folder.
		 * */			
		public Message[] getUnreadMessages(Store connection, String folder) throws MessagingException {
		    Folder folder1;
		    (folder1 = connection.getFolder(folder)).open(2);
		    return  folder1.search((SearchTerm)new FlagTerm(new Flags(Flags.Flag.SEEN), false));
		  }
		/**
		 * Filters unread Messages from the folder by "from email"
		 * @param connection takes the session as Store created while connecting to the email server
		 * @param folder takes the target folder name in String format from where the unread messages are to retrieved.
		 * @param fromEmail takes senders email in String format.
		 * @param subject takes the Subject of the email in String format 
		 * @throws Exception
		 * @return Arrays of Message retrieved from the given folder post filtering done by "From email"
		 * */

		public List<String> getUnreadMessageByFromEmail(Store connection, String folder, String fromEmail, String subject) throws Exception {
		    ArrayList<String> arrayList = new ArrayList<String>();
		    try {
		      Message[] arrayOfMessage;
		      int i;
		      byte b;
		      arrayOfMessage = getUnreadMessages(connection, folder);
		      for (i = (arrayOfMessage).length, b = 0; b < i; ) {
		        Message message;
		        Address[] arrayOfAddress;
		        if ((arrayOfAddress = (message = arrayOfMessage[b]).getFrom()) != null && arrayOfAddress.length > 0) {
		          if (arrayOfAddress[0].toString().contains(fromEmail) && 
		            message.getSubject().contains(subject))
		        	  arrayList.add(parser((Part)message)); 
		        } else {
		          System.out.println("[INFO] : No messages received from the provided mail");
		        } 
		        b++;
		      } 
		    } catch (MessagingException e) {
		      e.printStackTrace();
		    } 
		    return arrayList;
		  }
		
		/**
		 * Parses the "Multipart/alternative" format message object to readable message in String format. 
		 * @param message takes message object of type Part
		 * @throws MessagingException
		 * @throws IOException
		 * @return message in String format.
		 * */
		private String parser(Part message) throws IOException, MessagingException {

		    try {
		        Object content = message.getContent();
		        if (content instanceof Multipart) {
		            StringBuffer messageContent = new StringBuffer();
		            Multipart multipart = (Multipart) content;
		            int n=multipart.getCount();
		            for (int i = 0; i < n; i++) {
		                Part part = multipart.getBodyPart(i);
		                if (part.isMimeType("text/plain")) {
		                    messageContent.append(part.getContent().toString());
		                }
		            }
		            return messageContent.toString();
		        }
		        return content.toString();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return "";
		}
		
}
