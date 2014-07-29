package com.cmozie.ontap;



import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

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
	private ParseObject OnTapParseData;
	Button next;
	EditText beerName;
	public String beerNameString;
	public String userString;
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
        
        
        
        
        next.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			OnTapParseData.put("beerName", beerNameString);
		        OnTapParseData.saveInBackground();
				 
				Intent intent = new Intent(getApplicationContext(), TasteGood.class);
				startActivity(intent);
			}
		});
       
    }
	
}
