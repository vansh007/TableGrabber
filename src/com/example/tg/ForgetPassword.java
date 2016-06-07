package com.example.tg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPassword extends Activity 
{
	TextView tvtablegrabber, tvforgetpassword, tvfptext;
	EditText etmobileno;
	Button btnsend;
	@Override
	protected void onCreate(Bundle fpt) 
	{
		// TODO Auto-generated method stub
		super.onCreate(fpt);
		setContentView(R.layout.activity_forgetpassword);
		tvtablegrabber = (TextView) findViewById (R.id.tv_tablegrabber);
		tvforgetpassword = (TextView) findViewById (R.id.tv_forgetpassword);
		tvfptext = (TextView) findViewById (R.id.tv_fptext);
		etmobileno = (EditText) findViewById (R.id.et_mobileno);
		btnsend = (Button) findViewById(R.id.btn_send);
		
		btnsend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				//Intent i = new Intent(ForgetPassword.this, Login.class);
				//startActivity(i);
				
				Toast.makeText(getApplicationContext(), "Password sent to your mobile", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
}
