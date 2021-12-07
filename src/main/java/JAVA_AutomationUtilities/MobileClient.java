package JAVA_AutomationUtilities;

import java.util.Iterator;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;

/**@author SAQUEEB ALI
 * @version 1.0
 * 
 * */
public class MobileClient {
/**
 * This is class which contains static fetchOTP method to retrieve OTP
 * @param ACCOUNT_SID a String containing Account SID TWILIO account
 * @param AUTH_TOKEN a STring containing Auth token of TWILIO account
 * @throws Raise a custom exception
 * @return a otp in int format
 * */
	public static int fetchOTP(final String ACCOUNT_SID, final String AUTH_TOKEN) throws Raise {
		if (ACCOUNT_SID != null || AUTH_TOKEN != null) {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			ResourceSet<Message> messages = Message.reader().limit(1).read();
			Iterator iter = messages.iterator();
			if (iter.hasNext()) {
				int a = Integer.valueOf(((Message) iter.next()).getBody().split("[^\\d]+")[0]);
				return a;
			} else {
				throw new Raise("OTP not recieved");

			}
		} else {
			throw new Raise("couldn't find the environment variable \n \n Please create environment variable with your ACCOUNT_SID and AUTH_TOKEN");
		}
	}

}
