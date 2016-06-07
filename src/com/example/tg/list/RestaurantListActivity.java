package com.example.tg.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tg.R;
import com.example.tg.list.helper.AlertDialogManager;
import com.example.tg.list.helper.ConnectionDetector;
import com.example.tg.list.helper.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantListActivity extends ListActivity 
{
	// Connection detector
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> restaurantsList;

	// tracks JSONArray
	JSONArray cities = null;
	
	// Album id
	String city_id, city_name;

	// tracks JSON url
	// id - should be posted as GET params to get restaurant list (ex: id = 5)
	private static final String URL_CITIES = "http://192.168.0.106/tablegrabber/json/city_restaurants.php";

	// ALL JSON node names
	private static final String TAG_HOTELS = "hotels";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_CITY = "city";
	private static final String TAG_AREA = "area";
	private static final String TAG_ADDRESS = "address";

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurantlist);
		
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) 
        {
            // Internet Connection is not present
            alert.showAlertDialog(RestaurantListActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        
        // Get album id
        Intent i = getIntent();
        city_id = i.getStringExtra("city_id");

		// Hashmap for ListView
		restaurantsList = new ArrayList<HashMap<String, String>>();

		// Loading tracks in Background Thread
		new LoadRestaurants().execute();
		
		// get listview
		ListView lv = getListView();
		
		/**
		 * Listview on item click listener
		 * SingleRestaurantActivity will be lauched by passing city id, song id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// On selecting single restaurant get hotel information
				Intent i = new Intent(getApplicationContext(), SingleRestaurantActivity.class);
				
				// to get hotel information
				// both city id and hotel id is needed
				String city_id = ((TextView) view.findViewById(R.id.city_id)).getText().toString();
				String hotel_id = ((TextView) view.findViewById(R.id.hotel_id)).getText().toString();
				
//				Toast.makeText(getApplicationContext(), "City Id: " + city_id  + ", Hotel Id: " + hotel_id, Toast.LENGTH_SHORT).show();
//				
				i.putExtra("city_id", city_id);
				i.putExtra("hotel_id", hotel_id);
				
				startActivity(i);
			}
		});	

	}

	/**
	 * Background Async Task to Load all restaurants under one city
	 * */
	class LoadRestaurants extends AsyncTask<String, String, String> 
	{

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(RestaurantListActivity.this);
			pDialog.setMessage("Loading Restaurants ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting tracks json and parsing
		 * */
		protected String doInBackground(String... args) 
		{
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			// post album id as GET parameter
			params.add(new BasicNameValuePair(TAG_ID, city_id));

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_CITIES, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("Restaurant List JSON: ", json);

			try 
			{
				JSONObject jObj = new JSONObject(json);
				if (jObj != null) 
				{
					String city_id = jObj.getString(TAG_ID);
					city_name = jObj.getString(TAG_CITY);
					cities = jObj.getJSONArray(TAG_HOTELS);

					if (cities != null) 
					{
						// looping through All songs
						for (int i = 0; i < cities.length(); i++) 
						{
							JSONObject c = cities.getJSONObject(i);

							// Storing each json item in variable
							String hotel_id = c.getString(TAG_ID);
							// track no - increment i value
							String restaurant_no = String.valueOf(i + 1);
							String name = c.getString(TAG_NAME);
							String area = c.getString(TAG_AREA);
							String address = c.getString(TAG_ADDRESS);

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							map.put("city_id", city_id);
							map.put(TAG_ID, hotel_id);
							map.put("restaurant_no", restaurant_no + ".");
							map.put(TAG_NAME, name);
							map.put(TAG_AREA, area);
							map.put(TAG_ADDRESS, address);
							

							// adding HashList to ArrayList
							restaurantsList.add(map);
						}
					} 
					else 
					{
						Log.d("Cities: ", "null");
					}
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
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all tracks
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							RestaurantListActivity.this, restaurantsList,
							R.layout.list_item_restaurant, new String[] { "city_id", TAG_ID, "restaurant_no",
									TAG_NAME, TAG_AREA , TAG_ADDRESS}, new int[] {
									R.id.city_id, R.id.hotel_id, R.id.restaurant_no, R.id.city_name, R.id.hotel_area });
					// updating listview
					setListAdapter(adapter);
					
					// Change Activity Title with Album name
					setTitle(city_name);
				}
			});

		}

	}
}