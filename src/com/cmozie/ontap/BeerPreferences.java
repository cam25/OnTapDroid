package com.cmozie.ontap;



import java.util.List;

import com.cmozie.utilities.UserData;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BeerPreferences extends Activity {
	public ParseObject OnTapParseData;
	Button next;
	public EditText beerName;
	public String beerNameString;
	public String userString;
	public UserData userData;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_preferences);
        
        next = (Button) findViewById(R.id.nextButton);
        beerName = (EditText) findViewById(R.id.beerNameText);
        
        beerNameString = beerName.getText().toString();
        
        
        
        //Parse.initialize(this, "V9YIObiclS0AxwKGsekYLirCE7YezkJaKgx1va31", "K3fVvZf6gIQnDbjjHWoiCb2IuRMDe1QTyKRDyHkq");
        OnTapParseData = new ParseObject("OnTapData");
       // testObject.put("userName", usernameString);
		//testObject.put("password", passwordString);
		//testObject.saveInBackground();
        
        ParseUser usersName = ParseUser.getCurrentUser();
        //ParseUser usersPassword = ParseUser.g
        userString = usersName.getUsername().toString();
        Log.i("Logged in as  - ", userString);
        
        OnTapParseData.put("userName", userString);
        //OnTapParseData.saveInBackground();
         userData = new UserData();
        
         ParseUser user = ParseUser.getCurrentUser();
	      ParseQuery<ParseObject> query = ParseQuery.getQuery("OnTapData");
	        query.whereEqualTo("userName", user);
	        query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					// TODO Auto-generated method stub
					if (e == null) {
						Log.i("Success", "Query");
						Log.i("objects", objects.toString());
					}else {
						Log.i("Somethin", "wrong");
					}
				}
			});
        if (beerName.getText().toString().equals("")) {
			
		}else {
			
			Intent intent = new Intent(getApplicationContext(), TasteGood.class);
			startActivity(intent);
		}
      
        next.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//beerName.setText("");
				
				OnTapParseData.put("beerID", beerName.getText().toString());
				OnTapParseData.saveInBackground();
				beerName.setText(beerName.getText().toString());
			/*ParseUser user = ParseUser.getCurrentUser();
			
			OnTapParseData.put("beerName", beerNameString);
			OnTapParseData.put("userName", user);
		        OnTapParseData.saveInBackground();*/
				 /* userData.setBeerName(beerNameString);
				  userData.setUser(ParseUser.getCurrentUser());
				  
				  userData.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						setResult(Activity.RESULT_OK);
						finish();
					}
				});*/
				  
				Intent intent = new Intent(getApplicationContext(), TasteGood.class);
				startActivity(intent);
			}
		});
       
    }
	
}
