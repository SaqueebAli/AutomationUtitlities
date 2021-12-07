package JAVA_AutomationUtilities;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import javax.mail.Store;
/**@author SAQUEEB ALI
 * @version 1.0
 * 
 * */

public class MailClient {
/**
 * getGmailOTP is a static method which retrieves the otp from gmail
 * @return otp in int format
 * @throws Exception
 * */
	public static int getGmailOTP() throws Exception {
		EmailUtils emailUtils = new EmailUtils();
		Properties prop = new Properties();
		prop.load(new FileInputStream("./data/emailConfigData.property"));
		String fromemail = prop.getProperty("FromEmail");
		String Subject = prop.getProperty("Subject");
		Store connection = emailUtils.connectGmail(prop);
		emailUtils.getUnreadMessages(connection, "Inbox");
		List<String> emailText = emailUtils.getUnreadMessageByFromEmail(connection, "Inbox", fromemail, Subject);
		System.out.println(emailText);
		return otpRetriever(emailText);

	}
	/**
	 * getOutlookOTP is a static method which retrieves the otp from gmail
	 *
	 * @return otp in int format
	 * @throws Exception
	 * 
	 * */
	public static int getOutlookOTP() throws Exception {
		EmailUtils emailUtils = new EmailUtils();
		Properties prop = new Properties();
		prop.load(new FileInputStream("./data/emailConfigData.property"));
		String fromemail = prop.getProperty("FromEmail");
		String Subject = prop.getProperty("Subject");
		Store connection = emailUtils.connectOutlook(prop);
		emailUtils.getUnreadMessages(connection, "Inbox");
		List<String> emailText = emailUtils.getUnreadMessageByFromEmail(connection, "Inbox", fromemail, Subject);
		System.out.println(emailText);
		return otpRetriever(emailText);
	}
	
	public static int otpRetriever(List<String> emailText) throws Exception {
		FileLib fl = new FileLib();
		String[] OTP = new String[10];
		if (emailText.size() == 0) {
			String val = fl.getExcelValue("", "OTP", 0, 0);
			if (val != null) {
//		fetch old otp from excel
				OTP[0] = val;
				return Integer.parseInt(OTP[0]);
			} else {
				throw new Exception("No Email Received");

			}
		} else {
			OTP = ((String) emailText.get(0)).split("[^\\d]+");
//	System.out.println(Arrays.toString(OTP));
//	write otp in excel
			fl.setExcelValue("", "OTP", 0, 0, OTP[2]);
			return Integer.parseInt(OTP[2]);

		}
	}
}
