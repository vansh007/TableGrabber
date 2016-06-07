package com.example.tg.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import com.example.tg.R;
import com.example.tg.list.helper.AlertDialogManager;
import com.example.tg.list.helper.ConnectionDetector;
import com.example.tg.list.helper.JSONParser;

public class CityListActivity extends ListActivity 
{
	// Connection detector
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> citiesList;

	// albums JSONArray
	JSONArray cities = null;

	// albums JSON url
	private static final String URL_CITIES = "http://192.168.0.106/tablegrabber/json/cities.php";

	// ALL JSON node names
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_HOTELS_COUNT = "hotels_count";

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citylist);
		
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) 
        {
            // Internet Connection is not present
            alert.showAlertDialog(CityListActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

		// Hashmap for ListView
		citiesList = new ArrayList<HashMap<String, String>>();

		// Loading Albums JSON in Background Thread
		new LoadCities().execute();
		
		// get listview
		ListView lv = getListView();
		
		/**
		 * Listview item click listener
		 * RestaurantListActivity will be lauched by passing city id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) 
			{
				// on selecting a single album
				// TrackListActivity will be launched to show tracks inside the album
				Intent i = new Intent(getApplicationContext(), RestaurantListActivity.class);
				
				// send album id to tracklist activity to get list of songs under that album
				String city_id = ((TextView) view.findViewById(R.id.city_id)).getText().toString();
				i.putExtra("city_id", city_id);				
				
				startActivity(i);
			}
		});		
	}

	/**
	 * Background Async Task to Load all Cities by making http request
	 * */
	class LoadCities extends AsyncTask<String, String, String> 
	{

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(CityListActivity.this);
			pDialog.setMessage("Listing Cities ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Albums JSON
		 * */
		protected String doInBackground(String... args) 
		{
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_CITIES, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("Cities JSON: ", "> " + json);

			try 
			{				
				cities = new JSONArray(json);
				
				if (cities != null) {
					// looping through All albums
					for (int i = 0; i < cities.length(); i++) 
					{
						JSONObject c = cities.getJSONObject(i);

						// Storing each json item values in variable
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String hotels_count = c.getString(TAG_HOTELS_COUNT);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_HOTELS_COUNT, hotels_count);

						// adding Hashmap to ArrayList
						citiesList.add(map);
					}
				}
				else
				{
					Log.d("Cities: ", "null");
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
			// dismiss the dialog after getting all cities
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							CityListActivity.this, citiesList,
							R.layout.list_item_cities, new String[] { TAG_ID,
									TAG_NAME, TAG_HOTELS_COUNT }, new int[] {
									R.id.city_id, R.id.city_name, R.id.hotels_count });
					
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}