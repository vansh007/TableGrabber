package com.example.tg;

import global.Global_variable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;

import com.example.tg.list.helper.AlertDialogManager;
import com.example.tg.list.helper.ConnectionDetector;
import com.example.tg.list.helper.JSONParser;

import json.ServiceHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class BookTable extends Activity implements OnClickListener
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
	
	
	private static final String URL_HOTEL = "http://192.168.0.102/tablegrabber/json/restaurant.php";

	// ALL JSON node names
	private static final String TAG_NAME = "name";
	private static final String TAG_AREA = "area";
	private static final String TAG_CITY = "city";
	private static final String TAG_ADDRESS = "address";
	
	
	
	
	int counter=0;
	TextView tvbooktableheading, tvrestname, tvguestname, tvemailid, tvmobileno, tvnoofguest, tvselectdatetime;
	EditText etnoofguest,etguestname,etemailid,etmobileno;
	Button btnminus,btnplus,btnbooktable;
	ScrollView scrollview;
	DatePicker datepicker;
	TimePicker timepicker;
	
	String str_guestname;
	String str_noofguest;
	String str_date;
	String str_time;
	String str_emailid;
	String str_mobileno;
	
	String encrypted;
	String insert_booking_detail;
	String jsonresponse;
	
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booktable);
		cd = new ConnectionDetector(getApplicationContext());
		
		tvbooktableheading = (TextView) findViewById(R.id.tv_bookatable);
//		tvrestname = (TextView) findViewById(R.id.tv_restaurantname);
		scrollview = (ScrollView)findViewById(R.id.scroll_view);
		tvguestname = (TextView) findViewById(R.id.tv_guestname);
		etguestname = (EditText)findViewById(R.id.et_guestname);
		tvnoofguest = (TextView) findViewById(R.id.tv_noofguest);
		btnminus = (Button) findViewById(R.id.btn_minus);
		etnoofguest = (EditText)findViewById(R.id.et_noofguest);
		btnplus = (Button) findViewById(R.id.btn_plus);
		tvselectdatetime = (TextView) findViewById(R.id.tv_selectdatetime);
		datepicker = (DatePicker)findViewById(R.id.datePicker);
		timepicker = (TimePicker)findViewById(R.id.timePicker);
		timepicker.setIs24HourView(true);
		tvemailid = (TextView) findViewById(R.id.tv_emailid);
		etemailid = (EditText) findViewById(R.id.et_emailid);
		tvmobileno = (TextView) findViewById(R.id.tv_mobileno);
		etmobileno = (EditText) findViewById(R.id.et_mobileno);
		btnbooktable = (Button) findViewById(R.id.btn_booktable);
		
		btnminus.setOnClickListener(this);
		btnplus.setOnClickListener(this);
		btnbooktable.setOnClickListener(this); 
					
	}
	
	public void onClick(View v)
	{
		if(v == btnminus && counter !=0)
		{
			counter--;
			etnoofguest.setText(Integer.toString(counter));
		}
		
		else if(v == btnplus)
		{
			counter++;
			etnoofguest.setText(Integer.toString(counter));
		}
		
		else if(v == btnbooktable)
		{
			str_guestname = etguestname.getText().toString();
			str_noofguest = etnoofguest.getText().toString();
//			str_date = datepicker.getText().toString();
//			str_time = timepicker.getText().toString();
			str_date = datepicker.getYear() + "-" + (datepicker.getMonth() + 1) + "-" + datepicker.getDayOfMonth();
			str_time = timepicker.getCurrentHour() + ":" + timepicker.getCurrentMinute() + ":" + "00";
			str_emailid = etemailid.getText().toString();
			str_mobileno = etmobileno.getText().toString();
			
			
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	        alertDialogBuilder.setTitle("Confirm Booking?");
	        alertDialogBuilder
	                .setMessage("Name : "+str_guestname+"\n"+"No of Guest : "+str_noofguest+"\n"+"Date : " +str_date+"\n"+"Time : "+str_time+"\n"+"Email : "+str_emailid+"\n"+"Mobile No. : "+str_mobileno)
	                .setCancelable(false)
	                .setPositiveButton("Confirm",
	                        new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog, int id) {
	                            	
	                            	try 
	                    			{
	                    				
	                    				System.out.println("hii.......xoxo");
	                    				
	                    				insert_booking_detail = Global_variable.insert_booking_detail 
	                    						+ "guest_name="
	                    						+ URLEncoder.encode(str_guestname, "utf-8")
	                    						+ "&no_of_guest="
	                    						+ URLEncoder.encode(str_noofguest, "utf-8")
	                    						+ "&date="
	                    						+ URLEncoder.encode(str_date, "utf-8")
	                    						+"&time="
	                    						+ URLEncoder.encode(str_time,"utf-8")
	                    						+"&emailid="
	                    						+URLEncoder.encode(str_emailid,"utf-8")
	                    						+"&mobileno="
	                    						+URLEncoder.encode(str_mobileno,"utf-8");
	                    						
	                    						System.out.println("MSV_url"+insert_booking_detail);
	                    						
	                    			} 
	                    			
	                    			catch (UnsupportedEncodingException e) 
	                    			{
	                    				// TODO Auto-generated catch block
	                    				e.printStackTrace();
	                    				
	                    			}
	                            	
	                    			new insert_booking_detail().execute();
	                    			System.out.println("hello...hiii");
	                                
	                            }
	                        })

	                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {

	                        dialog.cancel();
	                    }
	                });

	        AlertDialog alertDialog = alertDialogBuilder.create();
	        alertDialog.show();
			
			
			
			
			
			
			
			
//			Intent i = new Intent(BookTable.this, GuestInfo.class);	
//			startActivity(i);
		}
	}
	
	
	
	
	
	
	
	
	public void sendmail() 
	{
//		ProgressDialog p;
//		p = new ProgressDialog(LoginForm.this);
//		p.setMessage("Sending Mail...");
//		p.setCancelable(false);
//		p.show();

		final String username = "shahvansh00@gmail.com";
		final String password2 = "soccerlover143";


//	System.out.println("!!!!sendemail"+password+"---email"+email);
		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtps");
			props.put("mail.smtps.host", "smtp.gmail.com");
			props.put("mail.smtps.port", 465);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.quitwait", "false");
			javax.mail.Session sessionProps = javax.mail.Session
					.getDefaultInstance(props);
			sessionProps.setDebug(true);
			
			javax.mail.Message msg = new MimeMessage(sessionProps);
			msg.setSubject("Forgot password");
			
			msg.setText("You have received this message because you have Booked a table of Restaurant Through us." +"\n"+
					"Thanks For Booking through Tablegrabber." +"\n"+
					"Your Entered Details are" +"\n"+
					"Your Name is :" + str_guestname+"\n"+
					"Total Members are :" + str_noofguest+"\n"+
					"Your Particluar Time Slot is :" + str_time+"\n"+
					"Your Mobile No. is :" + str_mobileno+"\n"+
					
					"Your Email-Id is :" + str_emailid
					
					
					
					);

			javax.mail.Address toAddress = new InternetAddress(str_emailid);
			msg.setRecipient(javax.mail.Message.RecipientType.TO, toAddress);
			javax.mail.Address fromAddress = new InternetAddress(
					"shahvansh00@gmail.com");
			msg.setFrom(fromAddress);
			Transport transport = sessionProps.getTransport();
			transport.connect("smtp.gmail.com", 465, username, password2);
			
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

//			if (p.isShowing())
//				p.dismiss();

			runOnUiThread(new Runnable() {

				public void run() {

					Toast toast = Toast.makeText(BookTable.this,
							"Confirmation Sent to Your Email-Id..",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				}
			});
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	public class insert_booking_detail extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog p = new ProgressDialog(BookTable.this);
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
			json_response = sh.makeServiceCall(insert_booking_detail,ServiceHandler.GET);
			sendmail();
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
				
				Intent i = new Intent(BookTable.this, BookSummary.class);	
				startActivity(i);
				

			}
			else
			{
				 
				 Toast.makeText(BookTable.this, "Error...Please insert details again",Toast.LENGTH_SHORT).show();
			}
			
		}
		
	}
	
	
}
