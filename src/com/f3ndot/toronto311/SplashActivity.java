package com.f3ndot.toronto311;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends Activity {

	SharedPreferences sharedPref;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_splash);
        
        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Button login_btn = (Button) findViewById(R.id.splash_login_btn);
        Button anonymous_btn = (Button) findViewById(R.id.splash_anonymous_btn);
        AccountManager am = AccountManager.get(this);
        final Account[] accounts = am.getAccounts();
        final AlertDialog.Builder account_dialog_builder = new AlertDialog.Builder(this);
        account_dialog_builder.setTitle(R.string.login_dialog_title);

        login_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("MAIN", "I clicked the login button!");
				Log.v("MAIN", "---------------------------");
				switch (accounts.length) {
				case 0:
					account_dialog_builder.setMessage(R.string.login_no_accounts);
			        account_dialog_builder.setNeutralButton(R.string.login_ok, new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) { 
			                // do nothing
			            }
			         });
			        
					break;
				default:
					String[] account_names = new String[accounts.length];
					for (int i = 0; i < accounts.length; i++) {
						account_names[i] = accounts[i].name;
					}
					account_dialog_builder.setItems(account_names, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Log.v("ACCOUNTS", "Chose index "+which+", which is '"+accounts[which].toString()+"'");
							SharedPreferences.Editor editor = sharedPref.edit();
							editor.putBoolean("anonymous_login", false);
							editor.putString("account_name", accounts[which].name);
							editor.commit();
					        startActivity(new Intent(getApplicationContext(), MainActivity.class));
					        finish();
		               }
					});
					break;
				}
				AlertDialog account_dialog = account_dialog_builder.create();
				account_dialog.show();
			}
		});
        anonymous_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("MAIN", "I clicked the anonymous button!");
		        SharedPreferences.Editor editor = sharedPref.edit();
		        editor.putBoolean("anonymous_login", true);
		        if(editor.commit()) {
		        	Log.v("ANONY", "Saved boolean to preferences!");
		        } else {
		        	Log.e("ANONY", "FAILED SAVE boolean to preferences!");
		        }
		        startActivity(new Intent(getApplicationContext(), MainActivity.class));
		        finish();
			}
		});
             
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
  
}
