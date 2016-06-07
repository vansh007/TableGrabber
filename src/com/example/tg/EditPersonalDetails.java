package com.example.tg;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditPersonalDetails extends Activity{

	TextView tvguestinfo;
	EditText etguestname, etemailid, etmobileno,etaddress;
	Button btnproceed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editpersonaldetails);
		
		tvguestinfo = (TextView) findViewById(R.id.tv_guestinfo);
		etguestname = (EditText) findViewById(R.id.et_guestname);
		etemailid = (EditText) findViewById(R.id.et_emailid);
		etmobileno =(EditText) findViewById(R.id.et_mobileno);
		etaddress = (EditText) findViewById(R.id.et_address);
		btnproceed = (Button) findViewById(R.id.btn_Proceed);
		
		btnproceed .setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(EditPersonalDetails.this, BookSummary.class);
				startActivity(i);
							}
		});
				
	}

}
