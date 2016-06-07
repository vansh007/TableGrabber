package com.example.tg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class RestaurantDetailsTab extends Activity {

	TextView tvrestaurantname,tvrestaurantaddress,tvmenu;
	Button btnbooktable;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurantdetailstab);
		tvrestaurantname = (TextView) findViewById(R.id.tv_restaurantname);
		tvmenu = (TextView) findViewById(R.id.tv_menu);
		tvrestaurantaddress = (TextView) findViewById(R.id.tv_restaurantaddress);
		btnbooktable = (Button) findViewById(R.id.btn_booktable);
		
		
		
		
		btnbooktable.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(RestaurantDetailsTab.this, BookTable.class);
				startActivity(i);
				
			}
		});
	
	}
}
