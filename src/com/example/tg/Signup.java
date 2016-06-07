package com.example.tg;

import global.Global_variable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import json.ServiceHandler;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Signup extends Activity 
{
	
	
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
	
	
	

	TextView tvtablegrabber, tvregister;
	EditText etname, etpassword, etconfirmpassword, etemail, etmobileno;
	Button btnsubmit, btncancel;
	
	String str_name;
	String str_setpassword;
	String str_confirmpassword;
	String str_email;
	String str_mobileno;
	
	String encrypted;
	String insert_user;
	String jsonresponse;
	
//	String TAG_tg_registration_name;
//	String tAG_tg_registration_password;
//	String TAG_tg_registration_employee_contact_number;
//	String TAG_tg_registration_employee_email_id;
//	String TAG_tg_registration_employee_username;
//	String TAG_tg_registration_employee_password;
	
	@Override
	protected void onCreate(Bundle abc) 
	{
		// TODO Auto-generated method stub
		super.onCreate(abc);
		setContentView(R.layout.activity_signup);
		tvtablegrabber = (TextView) findViewById(R.id.tv_tablegrabber);
		tvregister = (TextView) findViewById(R.id.tv_register);
		etname = (EditText) findViewById(R.id.et_name);
		etpassword = (EditText) findViewById(R.id.et_password);
		etconfirmpassword = (EditText) findViewById(R.id.et_confirmpassword);
		etemail = (EditText) findViewById(R.id.et_email);
		etmobileno = (EditText) findViewById(R.id.et_mobileno);
		btnsubmit = (Button) findViewById(R.id.btn_submit);
		btncancel = (Button) findViewById(R.id.btn_cancel);
		
		btncancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(Signup.this, Login.class);
				startActivity(i);
				
			}
		});
				
		
		btnsubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
					// TODO Auto-generated method stub
				str_name = etname.getText().toString();
				str_setpassword = etpassword.getText().toString();
				str_confirmpassword = etconfirmpassword.getText().toString();
				
//				 final String strPwd = etpassword.getText().toString();
//			        final String strConfPwd = etconfirmpassword.getText().toString();
//			        
//			        if (strPwd.equals(strConfPwd))
//			        {
//			        	Toast.makeText(Signup.this, "Password match",Toast.LENGTH_LONG).show();
//			        }
//				
//			        else
//			        {
//			        	Toast.makeText(Signup.this, "Password does not match",Toast.LENGTH_LONG).show();
//			        	finish();
//			        }
				
				
				
				
				str_email = etemail.getText().toString();
				str_mobileno = etmobileno.getText().toString();
							//str_confirmpassword = et_registration_confirmpassword.getText().toString();
				try 
				{
					
					System.out.println("hii.......xoxo");
					
					insert_user = Global_variable.insert_user //+ "id="
//							+ URLEncoder.encode(encrypted, "utf-8")
							+ "&tg_registration_name="
							+ URLEncoder.encode(str_name, "utf-8")
							+ "&tg_registration_password="
							+ URLEncoder.encode(str_setpassword, "utf-8")
							+"&tg_registration_email="
//							+ URLEncoder.encode(str_confirmpassword,"utf-8")
//							+"tg_registration_"
							+URLEncoder.encode(str_email,"utf-8")
							+"&tg_registration_mobilenumber="
							+URLEncoder.encode(str_mobileno,"utf-8");
							
							System.out.println("kh_url"+insert_user);
				} 
				catch (UnsupportedEncodingException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					
				}
				
				new insert_user().execute();
				System.out.println("hello...hiii");
			}
		});
	}

    

	public class insert_user extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog p = new ProgressDialog(Signup.this);
		String json_response;
		//String TAG_str_name,TAG_str_password;
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			p.setMessage("Please Wait..");
			p.setCancelable(false);
			p.show();
			
			
		}
		@Override
		protected Void doInBackground(Void... params) 
		{
			// TODO Auto-generated method stub
			ServiceHandler sh= new ServiceHandler();
			json_response = sh.makeServiceCall(insert_user,ServiceHandler.GET);
			
			System.out.println("!!!!!json"+json_response);
			
			return null;
		}
		protected void onPostExecute(Void result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(p.isShowing())
			{
				p.dismiss();
			}
			if(json_response!=null)
			{
				Toast.makeText(Signup.this, "Inserted sucessfully",Toast.LENGTH_LONG).show();
				Intent i = new Intent(Signup.this,Login.class);
				startActivity(i);

			}
			else
			{
				 
				 Toast.makeText(Signup.this, "Data not sucessfully Inserted...Please insert again",Toast.LENGTH_LONG).show();
			}
			
		}
		
	}

}
	


