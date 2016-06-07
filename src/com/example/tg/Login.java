package com.example.tg;

import global.Global_variable;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import json.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.tg.list.CityListActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	
	
	public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
	
	
	TextView tvtablegrabber, tvlogin,tvforgetpassword,tvskiplogin;
	EditText etusername,etpassword;
	EditText editText1,frgtemail;
	Button btnlogin, btnsignup;
	
	
	String strCustomerUsername;
	String strCustomerPassword,email1;

	public static final String TAG_CUSTOMER_EMAIL = "tg_registration_email";
	public static final String TAG_CUSTOMER_PASSWORD = "tg_registration_password";

	String str_username, str_password,jsonstr,str_encrypted_password,str_email;
	String fetch_login_detail,forgot_password;
	
	
	String  encrypted;
	String TAG_tg_registration_name,TAG_tg_registration_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initializeWidget();
		setOnClickListener();
		
		
		
		
		btnsignup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this, Signup.class);
				startActivity(i);
				
			}
		});
		
		tvforgetpassword.setOnClickListener(new View.OnClickListener() {
			
			
			
			
			@Override
			public void onClick(View view) {

				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);

				AlertDialog.Builder alert = new AlertDialog.Builder(
						Login.this);

				alert.setTitle("Enter Your Email Id");

				frgtemail = new EditText(Login.this);
				alert.setView(frgtemail);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Editable value = frgtemail.getText();

								

							  if (value.length() == 0) {

									Toast toast = Toast.makeText(
											Login.this,
											"This field cannot be null",
											Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
							  else
							  {
								  
								 System.out.println("!!!!password"+value.toString());
								  
								 try {
										
										
										
										forgot_password = Global_variable.forgot_password+"id=db125b3a31f0f12db5e9def66614763f"+"&tg_registration_email="
												+ value.toString();
										
									
												
										System.out.println("!!!!urlforgot"+forgot_password);
										
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
									new forgotpassword().execute();

							  }

							}

						});
			

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							
							}
						});

				alert.show();

			}
		});
}

public void get_password(String string) {
	
}

/** GetForgotPwdDetails */
public class forgotpassword extends AsyncTask<Void, Void, Void> {
	ProgressDialog p1;
	String jsonStr;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Showing progress dialog
		 p1 = new ProgressDialog(Login.this);
		 p1.setMessage("Please wait...");
		 p1.setCancelable(false);
		 p1.show();
//		
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		String temp_password = null ;
		ServiceHandler sh = new ServiceHandler();
		jsonStr = sh.makeServiceCall(forgot_password, ServiceHandler.GET);
	
		System.out.println("!!!!json"+jsonStr);
		
		if (jsonStr.toString().equals("not found")) {

			runOnUiThread(new Runnable() {

				public void run() {

					Toast.makeText(getApplicationContext(),
							"Invalid Email Id", Toast.LENGTH_LONG).show();
				}
			});

		} else if (jsonStr != "") {
			try {

				JSONArray result_array = new JSONArray(jsonStr);
				System.out.println("!!!!jsonresult"+result_array);

				for (int i = 0; i < result_array.length(); i++) {
					JSONObject jsonObject = result_array.getJSONObject(i);

					email1 = jsonObject.getString(TAG_CUSTOMER_EMAIL);
					 temp_password = jsonObject
							.getString(TAG_CUSTOMER_PASSWORD);
					try {
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				System.out.println("!!!!response"+email1+"--"+temp_password);
				sendmail(temp_password, email1);
			} catch (JSONException e) {
				e.printStackTrace();

			}

		} else {

		}
		return null;

	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		// Dismiss the progress dialog
		 if (p1.isShowing())
		 p1.dismiss();
	}
}

public void sendmail(String password, String email) 
{
//	ProgressDialog p;
//	p = new ProgressDialog(LoginForm.this);
//	p.setMessage("Sending Mail...");
//	p.setCancelable(false);
//	p.show();

	final String username = "shahvansh00@gmail.com";
	final String password2 = "soccerlover143";


System.out.println("!!!!sendemail"+password+"---email"+email);
	try {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.port", 465);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtps.quitwait", "false");
		javax.mail.Session sessionProps = javax.mail.Session
				.getDefaultInstance(props);
		sessionProps.setDebug(true);
		
		javax.mail.Message msg = new MimeMessage(sessionProps);
		msg.setSubject("Forgot password");
		
		msg.setText("Your Password is :" + password);

		javax.mail.Address toAddress = new InternetAddress(email);
		msg.setRecipient(javax.mail.Message.RecipientType.TO, toAddress);
		javax.mail.Address fromAddress = new InternetAddress(
				"shahvansh00@gmail.com");
		msg.setFrom(fromAddress);
		Transport transport = sessionProps.getTransport();
		transport.connect("smtp.gmail.com", 465, username, password2);
		
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();

//		if (p.isShowing())
//			p.dismiss();

		runOnUiThread(new Runnable() {

			public void run() {

				Toast toast = Toast.makeText(Login.this,
						"Your Password Sent To Your Registed Id..",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

			}
		});
	} catch (MessagingException e) {
		throw new RuntimeException(e);
	}

}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	switch (keyCode) {
	case KeyEvent.KEYCODE_BACK:
		onBackPressed();
		return true;
	}
	return super.onKeyDown(keyCode, event);
}



	

	private void initializeWidget() {
		// TODO Auto-generated method stub
	




	tvtablegrabber = (TextView) findViewById(R.id.tv_tablegrabber);
	tvlogin = (TextView) findViewById(R.id.tv_login);
	tvforgetpassword =(TextView) findViewById(R.id.tv_forgetpassword);
	tvskiplogin = (TextView) findViewById(R.id.tv_skiplogin);
	etusername = (EditText) findViewById(R.id.et_username);
	etpassword = (EditText) findViewById(R.id.et_password);
	btnlogin = (Button) findViewById(R.id.btn_login);
	//btncancel = (Button) findViewById(R.id.btn_cancel);
	btnsignup = (Button) findViewById(R.id.btn_signup);
	
	}
	private void setOnClickListener() {
		// TODO Auto-generated method stub


		tvskiplogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this, CityListActivity.class);
				startActivity(i);
				
			}
		});
		
		
		btnlogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				str_username = etusername.getText().toString();
				str_password = etpassword.getText().toString();
					
					// validation
				if(etusername.getText().length()==0 && etpassword.getText().length()==0)
				{
					Toast.makeText(getApplicationContext(), "Enter Username & Password", Toast.LENGTH_LONG).show();
				}
				else
				{
	
				try 
				{
					System.out.println("kh_url_before_try");
//					fetch_login_detail = Global_variable.fetch_login_detail+ "id="
//							+ URLEncoder.encode(encrypted, "utf-8")
//							+ "&tg_registration_name="
//							+ URLEncoder.encode(str_username, "utf-8")
//							+ "&tg_registration_password="
//							+ URLEncoder.encode(str_password, "utf-8");
						
					
					fetch_login_detail = Global_variable.fetch_login_detail
							+ "tg_registration_name="
							+ URLEncoder.encode(str_username, "utf-8");
//							+ "&tg_registration_password="
//							+ URLEncoder.encode(str_password, "utf-8");
//						
					System.out.println("kh_url"+fetch_login_detail);
					} 
				catch (Exception e) 
				{
					
					System.out.println("kh_url_else"+fetch_login_detail);
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				new fetch_login().execute();
			}
			}
		});
	}
	
	public class fetch_login extends AsyncTask<Void, Void, Void> 
	{
		ProgressDialog p = new ProgressDialog(Login.this);
		String jsonstr;
		JSONArray json_object;

		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
//			p.setMessage("Loading....Please wait....");
//			p.setCancelable(false);
//			p.show();
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			// TODO Auto-generated method stub
			ServiceHandler sh = new ServiceHandler();
			jsonstr=sh.makeServiceCall(fetch_login_detail, ServiceHandler.GET);
			System.out.println("kh_json..."+jsonstr);
			
			
			
			
			if(jsonstr!= null)
			{
				try 	
				{
					JSONArray resultArray = new JSONArray(jsonstr);
					for(int i=0;i<resultArray.length();i++)
					{
						JSONObject json_object= resultArray.getJSONObject(i);
						TAG_tg_registration_name = json_object.getString("tg_registration_name");					
						TAG_tg_registration_password = json_object.getString("tg_registration_password");
						System.out.println("kh_username"+TAG_tg_registration_name);
						System.out.println("kh_password"+TAG_tg_registration_password);
					}
				
				} 
				catch (Exception e) 
				{
					// TODO: handle exception
					e.printStackTrace();
				}
			}	
			
			return null;
		}

		//@SuppressWarnings("null")
		@Override
		protected void onPostExecute(Void result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result); 
//			if(p.isShowing())
//			{
//				p.dismiss();
//			}
			//String str_password = null;
			try 
			{
				//str_password = T2_encrypt_decrypt.bytesToHex(obj.encrypt(str_password));
				str_encrypted_password = str_password;
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(str_username.equals(TAG_tg_registration_name))
			{
				if(str_password.equals(TAG_tg_registration_password))
				{
					
					//System.out.println("admin"+rb_admin.isChecked());
					//System.out.println("employee"+rb_employee.isChecked());
					Intent i = new Intent(Login.this, CityListActivity.class);
					startActivity(i);
				}
					
				Toast.makeText(Login.this,"Login successfully....",Toast.LENGTH_LONG).show();
					
			 }
				
			else
			{
					Toast.makeText(Login.this,"please check username and password ", Toast.LENGTH_LONG).show();
					
			}
				
		
		}
				
				
				//Intent i = new Intent(Login.this, SelectRegion.class);
				//startActivity(i);
				
	}
		
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
