package com.example.tg.list;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tg.BookTable;
import com.example.tg.GuestInfo;
import com.example.tg.R;
import com.example.tg.list.helper.AlertDialogManager;
import com.example.tg.list.helper.ConnectionDetector;
import com.example.tg.list.helper.JSONParser;

public class SingleRestaurantActivity extends Activity 
{
	// Connection detector
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	// tracks JSONArray
	JSONArray cities = null;
	
	// Album id
	String city_id = null;
	String hotel_id = null;
	
	String city_name, hotel_name, area,address;

	Button btn_proceed;
	// single song JSON url
	// GET parameters album, song
	private static final String URL_HOTEL = "http://192.168.0.106/tablegrabber/json/restaurant.php";

	// ALL JSON node names
	private static final String TAG_NAME = "name";
	private static final String TAG_AREA = "area";
	private static final String TAG_CITY = "city";
	private static final String TAG_ADDRESS = "address";
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_restaurant);
		btn_proceed = (Button)findViewById(R.id.button1);
		cd = new ConnectionDetector(getApplicationContext());
		 
		btn_proceed.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent i = new Intent(SingleRestaurantActivity.this, BookTable.class);
				startActivity(i);
			}
		});
		
        // Check if Internet present
        if (!cd.isConnectingToInternet()) 
        {
            // Internet Connection is not present
            alert.showAlertDialog(SingleRestaurantActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        
        // Get city id, hotel id
        Intent i = getIntent();
        city_id = i.getStringExtra("city_id");
        hotel_id = i.getStringExtra("hotel_id");
        System.out.println("City ID: " +city_id);
        System.out.println("Hotel ID: " +hotel_id);
        // calling background thread
        new LoadSingleRestaurant().execute();
	}
	
	/**
	 * Background Async Task to get single restaurant information
	 * */
	class LoadSingleRestaurant extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SingleRestaurantActivity.this);
			pDialog.setMessage("Loading Hotel ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting song json and parsing
		 * */
		protected String doInBackground(String... args) 
		{
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			// post album id, song id as GET parameters
			params.add(new BasicNameValuePair("city", city_id));
			params.add(new BasicNameValuePair("hotel", hotel_id));

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_HOTEL, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("Single Resataurant JSON: ", json);

			try 
			{
				JSONObject jObj = new JSONObject(json);
				if(jObj != null)
				{
					hotel_name = jObj.getString(TAG_NAME);
					city_name = jObj.getString(TAG_CITY);
					area = jObj.getString(TAG_AREA);
					address = jObj.getString(TAG_ADDRESS);
				}			

			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) 
		{
			// dismiss the dialog after getting song information
			pDialog.dismiss();
			
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					
					TextView txt_hotel_name = (TextView) findViewById(R.id.hotel_title);
					TextView txt_city_name = (TextView) findViewById(R.id.city_name);
					TextView txt_area = (TextView) findViewById(R.id.area);
					TextView txt_address = (TextView) findViewById(R.id.address);
					
					// displaying restaurant data in view
					txt_hotel_name.setText(hotel_name);
					txt_city_name.setText(Html.fromHtml("<b>City:</b> " +city_name));
					txt_area.setText(Html.fromHtml("<b>Area :</b> " +area));
					txt_address.setText(Html.fromHtml("<b>Address :</b> " +address));
					
					
					// Change Activity Title with Song title
					setTitle(hotel_name);
				}
			});

		}

	}
}
