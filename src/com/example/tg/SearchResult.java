package com.example.tg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SearchResult extends Activity {

	TextView tvsearchresult,tvtbcity,tvrestaurantlist;
	
	@Override
	protected void onCreate(Bundle searchresult) {
		// TODO Auto-generated method stub
		super.onCreate(searchresult);
		setContentView(R.layout.activity_search_result);
		tvsearchresult = (TextView) findViewById(R.id.tv_searchresult);
		tvtbcity = (TextView) findViewById(R.id.tv_tbcity);
		tvrestaurantlist = (TextView) findViewById(R.id.tv_restaurantlist);
		
		tvrestaurantlist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(SearchResult.this, RestaurantDetailsTab.class);
				startActivity(i);
				
			}
		});
	}

}
