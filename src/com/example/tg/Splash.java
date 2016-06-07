package com.example.tg;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
public class Splash extends Activity
{

	@Override
	protected void onCreate(Bundle start) 
	{
		// TODO Auto-generated method stub
		super.onCreate(start);
		setContentView(R.layout.splash);
		
		Thread timer=new Thread()
        {
            public void run() 
            {
                try 
                {
                    sleep(5000);
                } 
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {
                    Intent i=new Intent(Splash.this,Login.class);
                    finish();
                    startActivity(i);
                }
            }
        };
        timer.start();
	
//        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
//        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
//        if(nInfo!=null && nInfo.isConnected())
//        {
//        	Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show();
//        	 
//        }
//        else
//        {
//        	Toast.makeText(this, "No Internet", Toast.LENGTH_LONG).show();
////        	System.exit(0);
//        	
//            }
	}
	
}
