package com.f3ndot.toronto311;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class FirstRunActivity extends Activity {

	SharedPreferences sharedPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// load preferences and display who's logged in
    	sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.contains("anonymous_login") || sharedPref.contains("account_name")) {
        	Log.v("FIRST", "Login detected. Loading main app activity");
        	Log.v("PREF", "Shared preferences contain anonymous_login or account_name");
        	Toast toast;
        	if(sharedPref.getBoolean("anonymous_login", false) == true) {
        		Log.v("PREF", "Totally have anonymous_login value of true");
        		toast = Toast.makeText(getApplicationContext(), "Using the app Anonymously", Toast.LENGTH_LONG);
        	} else {
        		Log.v("PREF", "Totally have anonymous_login value of false. Should show account_name string");
        		toast = Toast.makeText(getApplicationContext(), "Signed in as "+sharedPref.getString("account_name", "BUG!"), Toast.LENGTH_LONG);
        	}
        	Log.v("PREF", "Showing toast");
        	toast.show();
        	startActivity(new Intent(this, MainActivity.class));
        } else {
        	Log.v("FIRST", "No login detected. Loading splash activity");
        	startActivity(new Intent(this, SplashActivity.class));
        }
        // kill off this activity, as it just "dynamically" specifies the launching activity
    	finish();
    }

}
