package com.cmozie.ontap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.cmozie.utilities.PagerAdapter.TasteGoodFragment;
import com.cmozie.utilities.UserData;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends Activity {
	 private ActionBar actionBar;
	Button login;
	Button signUp;
	EditText username;
	EditText password;
	String usernameString;
	String passwordString;
	public ParseObject testObject;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        
        ParseObject.registerSubclass(UserData.class);
        Parse.initialize(this, "V9YIObiclS0AxwKGsekYLirCE7YezkJaKgx1va31", "K3fVvZf6gIQnDbjjHWoiCb2IuRMDe1QTyKRDyHkq");
        
         testObject = new ParseObject("OnTapData");
         
        ParseUser.enableAutomaticUser();
        //ParseACL defaultACL = new ParseACL();
        //defaultACL.setPublicReadAccess(true);
        
        //ParseACL.setDefaultACL(defaultACL, true);
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // if the user doesnt have an account display the main activity where they can sign up
           
        } else {
        	//else if the user already has registered send them to the preference class
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                Intent intent = new Intent(MainActivity.this, BeerPreferences.class);
                startActivity(intent);
                finish();
            } 
        }
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();
        
        actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        login = (Button) findViewById(R.id.login);
        
        login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(getApplicationContext(), BeerPreferences.class);
				//startActivity(intent);
				usernameString = username.getText().toString();
				passwordString = password.getText().toString();
				
				
				ParseUser.logInInBackground(usernameString, passwordString, new LogInCallback() {
					
					@Override
					public void done(ParseUser user, ParseException e) {
						// TODO Auto-generated method stub
						if (user != null)
							{
							Intent intent = new Intent(MainActivity.this, BeerPreferences.class);
							startActivity(intent);
							Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_LONG).show();
							finish();
						}else {
							Toast.makeText(
                                    getApplicationContext(),
                                    "No account found, please signup",
                                    Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		});
        
        
        signUp = (Button) findViewById(R.id.signUp);
        
        signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(getApplicationContext(), Register.class);
				//startActivity(intent);
				
				usernameString = username.getText().toString();
				passwordString = password.getText().toString();
				
				
				if (usernameString.equals("") && passwordString.equals("")) {
					Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();
 
				}else {
					
					testObject.put("userName", usernameString);
					testObject.put("password", passwordString);
					testObject.saveInBackground();
					
					ParseUser newUser = new ParseUser();
					newUser.setUsername(usernameString);
					newUser.setPassword(passwordString);
					newUser.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							// TODO Auto-generated method stub
							if (e == null) {
								Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
							}else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }
						}
					});
				}
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
