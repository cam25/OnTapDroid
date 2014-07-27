package com.cmozie.ontap;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	Button signup;
	EditText username;
	EditText password;
	EditText email;
	String usernameString;
	String passwordString;
	String emailString;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        signup = (Button)findViewById(R.id.register);
      
        username = (EditText)findViewById(R.id.usernameSignup);
        password = (EditText)findViewById(R.id.passwordSignup);
        email = (EditText) findViewById(R.id.emailSignup);
        
       
        usernameString = username.getText().toString();
		passwordString = password.getText().toString();
		emailString = email.getText().toString();
		Log.i("Username", usernameString);
		
		
		username.setText("TESTING");
		password.setText("TESTING");
		Log.i("TEST", usernameString);
		signup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (usernameString.equals("TESTING") && passwordString.equals("TESTING")) {
					 
					Toast.makeText(getApplicationContext(),
		                    "Please complete the sign up form",
		                    Toast.LENGTH_LONG).show();
					
				}else {
					
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

}
