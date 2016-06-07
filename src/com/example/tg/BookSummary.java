package com.example.tg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookSummary extends Activity
{
	TextView tvbooksummary,tvrestaurantname, tvrestaurantaddress, tvguestsummary, tvamountdetails;
	TextView tvpersonaldetails, tvname, tvemailid, tvmobileno;
	Button btnchange, btnproceedtopayment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booksummary);
		
		tvbooksummary = (TextView) findViewById(R.id.tv_booksummary);
		tvrestaurantname = (TextView) findViewById(R.id.tv_restaurantname);
		tvrestaurantaddress = (TextView) findViewById(R.id.tv_restaurantaddress);
		tvguestsummary = (TextView) findViewById(R.id.tv_guestsummary);
		tvamountdetails = (TextView) findViewById(R.id.tv_amountdetails);
		tvpersonaldetails = (TextView) findViewById(R.id.tv_personaldetails);
		tvname = (TextView) findViewById(R.id.tv_name);
		tvemailid = (TextView) findViewById(R.id.tv_emailid);
		tvmobileno = (TextView) findViewById(R.id.tv_mobileno);
		
		btnchange = (Button) findViewById(R.id.btn_change);
		btnproceedtopayment = (Button) findViewById(R.id.btn_proceedtopayment);
		
		btnchange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(BookSummary.this, EditPersonalDetails.class);
				startActivity(i);
				
			}
		});
		
		btnproceedtopayment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i= new Intent(BookSummary.this, EditPersonalDetails.class);
				//startActivity(i);
				
			}
		});
		
	}

}
