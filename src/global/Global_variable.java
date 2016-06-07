package global;

public class Global_variable {   
	//host  files path....

//	public static String host_files_path="http://android.searchkarigar.com/car_rental/";
	public static String host_files_path="http://192.168.0.106/tablegrabber/";
	//php file access path
	public static String login_php_path = host_files_path+"tg/";
	

	public static String encrypt = login_php_path+"encrypt?";
	public static String decrypt = login_php_path+"decrypt?";
	public static String fetch_login_detail = login_php_path+"fetch_login_detail?";
	public static String fetch_city_detail = login_php_path+"fetch_city_detail?";
	
	public static String forgot_password = login_php_path+"forgot_password?";
	
	
	
	public static String insert_user = login_php_path+"insert_user?";
	public static String insert_booking_detail = login_php_path+"insert_booking_detail?";
	
	//public static String insert_relative = login_php_path+"insert_relative?";

	//public static String global_et_login_username;
	//public static String global_et_login_password;
	//public static String global_str_password;
	
	
	
	

	
	
	
	
	
}

