package com.example.tg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchRestaurant extends Activity{

	TextView tvtabblegrabber, tvsearchheading, tvrestnameloc;
	EditText etrestnameloc;
	Button btnsearchnow, btnnearbyrest;
	@Override
	protected void onCreate(Bundle searchrest) {
		// TODO Auto-generated method stub
		super.onCreate(searchrest);
		setContentView(R.layout.activity_search_restaurant);
		tvtabblegrabber = (TextView) findViewById(R.id.tv_tablegrabber);
		tvsearchheading = (TextView) findViewById(R.id.tv_searchheading);
		tvrestnameloc = (TextView) findViewById(R.id.tv_searchrestname);
		etrestnameloc = (EditText) findViewById(R.id.et_searchbyname);
		btnsearchnow =(Button) findViewById(R.id.btn_searchnow);
		btnnearbyrest= (Button) findViewById(R.id.btn_nearbyrest);
		
		btnnearbyrest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(SearchRestaurant.this, SearchResult.class);
				startActivity(i);
				
			}
		});
		
		btnsearchnow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(SearchRestaurant.this, SearchResult.class);
				startActivity(i);
				
			}
		});
	}

}
