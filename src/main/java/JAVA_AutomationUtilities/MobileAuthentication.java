package JAVA_AutomationUtilities;

/**@author SAQUEEB ALI
 * @version 1.0
 * 
 * */
public class MobileAuthentication {
static String acc_sidV,auth_tokenV;
/**
 * Gets the Account SID and Auth token from the User's system variables and set them.
 * @param acc_sidV a String representing the name of the Account SID environment variable set by the user
 * @param auth_tokenV a String representing the name of the Auth Token environment variable set by the user
 * 
 * */	
MobileAuthentication(String acc_sidV,String auth_tokenV){
		MobileAuthentication.acc_sidV=System.getenv(acc_sidV);
		MobileAuthentication.auth_tokenV=System.getenv(auth_tokenV);
	}
/**
*returns the Auth token and Account SID 
*@return Array of String containing Account SID and Auth token retrieved from the users System.  
* */
	public String[] authorize() {
		String array[]= {acc_sidV,auth_tokenV};
		return array;
	}
}
