package com.cmozie.ontap;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BeerPreferences extends Activity {

	Button next;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_preferences);
        
        next = (Button) findViewById(R.id.nextButton);
        
        next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), TasteGood.class);
				startActivity(intent);
			}
		});
       
    }
	
}
