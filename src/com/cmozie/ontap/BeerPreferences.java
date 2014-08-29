package com.cmozie.ontap;



import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cmozie.utilities.Network;
import com.cmozie.utilities.UserData;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BeerPreferences extends Activity {
	public ParseObject OnTapParseData;
	public static Button preferenceSearch;
	public EditText beerName;
	public String beerNameString;
	public String userString;
	public UserData userData;
	public static ListView preferenceListView;
	public  HashMap<String, String> map;
	public  List<Map<String,String>> dataArray;
	public ArrayList<String> foos2;
	public String brews2;
	public static ParseUser usersName;
	public static String beersName;
	public static String beerID;
	public static ParseUser userName2;
	public static ParseObject userLikes;
	public static List <String> aa;
	public static ArrayList<BeerObject> test;
	public static BeerObject beerObject;
	public static ParseQuery<ParseObject> query;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_preferences);
        
        preferenceSearch = (Button) findViewById(R.id.searchBeer);
        beerName = (EditText) findViewById(R.id.beerNameText);
        
        preferenceListView = (ListView) findViewById(R.id.preferenceList);
        
        beerNameString = beerName.getText().toString();
        
        InputMethodManager imm2 = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
if(imm2 != null){
imm2.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
}
        
        if (beerName.isSelected()) {
        	InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
if(imm != null){
imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
}
		}
        
        //Parse.initialize(this, "V9YIObiclS0AxwKGsekYLirCE7YezkJaKgx1va31", "K3fVvZf6gIQnDbjjHWoiCb2IuRMDe1QTyKRDyHkq");
        OnTapParseData = new ParseObject("OnTapData");
       // testObject.put("userName", usernameString);
		//testObject.put("password", passwordString);
		//testObject.saveInBackground();
        
      usersName = ParseUser.getCurrentUser();
        //ParseUser usersPassword = ParseUser.g
        userString = usersName.getUsername().toString();
        Log.i("Logged in as  - ", userString);
        
        OnTapParseData.put("userName", userString);
        //OnTapParseData.saveInBackground();
         userData = new UserData();
         foos2 = new ArrayList<String>();
         
        preferenceSearch.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 InputMethodManager imm2 = (InputMethodManager)
			                getSystemService(Context.INPUT_METHOD_SERVICE);
			if(imm2 != null){
			imm2.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
				
				String search = beerName.getText().toString();

   			 Log.i("search", search);
   			 getApiResults(search);
				//beerName.setText("");
				
			/*	OnTapParseData.put("beerID", beerName.getText().toString());
				OnTapParseData.saveInBackground();
				beerName.setText(beerName.getText().toString());*/
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
				  
   			 
			}
		});
       
    }
	public void getApiResults(String beer){

		String baseUrl = "http://api.brewerydb.com/v2/search/?q="+ beer +"/?description/?hasLabels=Y/&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
		

			
			String queryString;
			String queryString2;
			try {
				

				queryString = URLEncoder.encode(beer,"UTF-8");

			} catch (Exception e) {
				// TODO: handle exception
				Log.e("ERROR-URL", "ENCODING ISSUE");
				queryString = "";
			}
			
			 baseUrl = "http://api.brewerydb.com/v2/search/?q="+queryString+"?hasLabels=Y&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
			URL finalURL;
					try {
				
				 finalURL = new URL(baseUrl);
				 //finalURL2 = new URL(query);
				 SearchAsyncTask queryRequest = new SearchAsyncTask();
				
				 	
				 	Log.i("FinalURL", finalURL.toString());

				
					 queryRequest.execute(finalURL);

			} catch (Exception e) {
				// TODO: handle exception
				Log.i("BAD URL", "URL MALFORMED");
			}
					
					

				}
	public class SearchAsyncTask extends AsyncTask<URL, Void, String>{
		TextView beerName;
		 String beerNam;

		 String description;
		 Context context;

		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */

			
			ProgressDialog progressIndicator;
			
			/* (non-Javadoc)
			 * @see android.os.AsyncTask#onPreExecute()
			 */
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
				
				progressIndicator = new ProgressDialog(BeerPreferences.this);
				progressIndicator.setMessage("Getting Info...");
				progressIndicator.setIndeterminate(false);
				progressIndicator.setCancelable(true);
				progressIndicator.show();
			}

			/* (non-Javadoc)
			 * @see android.os.AsyncTask#doInBackground(Params[])
			 */
			protected String doInBackground(URL... urls) {
				// TODO Auto-generated method stub
				String reply = "";
				for (URL url : urls) {
				reply = Network.URLStringResponse(url);	
				}
				return reply;
			}

			/* (non-Javadoc)
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				
				
				
				progressIndicator.dismiss();
				try {
					JSONObject json = new JSONObject(result);
					JSONArray data = json.getJSONArray("data");
					//JSONObject label =;
					dataArray = new ArrayList<Map<String,String>>();
					
					for (int i = 0; i < data.length(); i++) {
						
						
						
						JSONObject one = data.getJSONObject(i);
						
						map = new HashMap<String, String>();
						
						
						//beer name
						if (one.has("name")) {
							 map.put("name", one.getString("name"));
						}else{
							map.put("name", "N/A");
						}
						
						if (one.has("id")) {
							map.put("id", one.getString("id"));
						}
						//description
						/*if (one.has("description")) {
							map.put("description", one.getString("description"));
						}else if (one.has("style")) {
							
						JSONObject style = one.getJSONObject("style");
						
						String styleDescription = style.getString("description");
						
						
						Log.i("Style", styleDescription);
						map.put("description", style.getString("description"));
							
						}else{
						
							map.put("description", "No Description Available");
						
					}
						//label
						if (one.has("labels")) {
							JSONObject image = one.getJSONObject("labels");
							
							map.put("labels", image.getString("large"));
							Log.i("map", map.toString());

						}
						
						//abv 
						if (one.has("abv")) {
							 map.put("abv", one.getString("abv"));
						}else {
							map.put("abv", "N/A");
						}
						//id for brewery
						if (one.has("id")) {
							map.put("id", one.getString("id"));
						}
						//type
						if (one.has("type")) {
							map.put("type", one.getString("type"));
						}else {
							map.put("type", "N/A");
						}
						
						//available
						if (one.has("available")) {
							JSONObject available = one.getJSONObject("available");
							map.put("available", available.getString("name"));
						}else {
							map.put("available", "N/A");
						}
						
						//style
						if (one.has("style")) {
							JSONObject style = one.getJSONObject("style");
							JSONObject category = style.getJSONObject("category");
							
							map.put("style", category.getString("name"));
						}else {
							map.put("style", "N/A");
						}
						*/
						//add map contents to array
						dataArray.add(map);
						
						
						ListAdapter adapter = new SimpleAdapter(BeerPreferences.this, dataArray, R.layout.preferencelistitems, new String[]{"name","company" },new int[]{R.id.beerType, R.id.beerCompany});
		      		      preferenceListView.setAdapter(adapter);
		      		      
		      		      preferenceListView.setOnItemClickListener(new OnItemClickListener() {
		      		    	  
		      		    	 public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) 
		      		       {

		      		           Intent n = new Intent(BeerPreferences.this, TasteGood.class);
		      		           n.putExtra("position", position);
		      		         Object obj = arg0.getItemAtPosition(position);
		        		        String name = obj.toString();
		        		      beerID = dataArray.get(position).get("id");
		        		      
		        		       beersName = dataArray.get(position).get("name");
		        		       
		        		        Log.i("beerid", beerID);
		        		        
		        		        n.putExtra("id", beerID);
		        		        
		        		     n.putExtra("beerName", name);
		        		     
		        		     final ParseUser user = ParseUser.getCurrentUser();
		        	         if (user != null) {
		        	             // initialize a bunch of stuff and go to the main activity
		        	         

		        	         Log.i("user", user.getUsername());
		        	        // ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("TasteGood");
		        	         //innerQuery.selectKeys(Arrays.asList("beerID","userName"));
		        	         
		        		     query = ParseQuery.getQuery("BeerClass");
                             //query.whereMatchesKeyInQuery("userName", "userName", innerQuery);
                           query.whereEqualTo("Name", beersName);
                           //query.include("userLikes");
                           //query.selectKeys(Arrays.asList("userLikes"));;
		        	         //ParseQuery<ParseObject> query = ParseQuery.getQuery("BeerClass");
                             //query.whereEqualTo("Name", beersName);
                      query.findInBackground(new FindCallback<ParseObject>() {
                    
                    	  BeerObject beerObject = new BeerObject();
                    	  
                          public void done(List<ParseObject> objects, ParseException e) {
                              if (e == null) {
                                
                            	  aa = new ArrayList <String>();
                                  test = new ArrayList<BeerObject>();
                                  
                                  if (objects.size() > 0) {
                                	  
                                	  ParseObject beerParseObject = null;
                                	  
                                      for (int k = 0; k < objects.size(); k++) {
                                    	  Object beer_object = objects.get(k);
                                    	  
                                    	   beerParseObject = (ParseObject)beer_object;
                                    	  ParseObject testObject = (ParseObject)objects.get(0);
                                    	  
                                    	  
                                    	  String userNameString = ((ParseObject) beer_object).get("userLikes").toString();
                                    	  String beerIDString = ((ParseObject) beer_object).getString("beerID").toString();
                                    	  
                                    	  
                                    	  beerObject.setUserName(userNameString);
                                    	  beerObject.setBeerId(beerIDString);
                                    	 
                                    	  
                                    	  Log.i("TESTARRAY", beerObject.getUserName() + " " + beerObject.getBeerId());
                                    	  
                                    	  test.add(beerObject);
                                    	 
                                    	  
                                    	  
										 //userLikes = (ParseObject) test.get(0);
										 
                                         //aa.add(userLikes.get("beerID").toString());
                                        
                              
                                                //ArrayList currentBeers = (ArrayList) Arrays.asList(objects);
                                               
									}
                                       

                                                 // Log.i("parse", userLikes.get(user.getUsername()).toString());
                                                  
       
                                                
                                                  boolean hasLiked = false;
                                                 
                                                 for (int i = 0; i < test.size(); i++) {
                                                	 Log.i("Array Count ",String.valueOf(test.size()));
                                                	 //Log.i("userTESTING",test.get(i).getUserName().toString());
                                                	 
                                                	 Log.i("NullCheck", beerObject == null ? "Is Null" : "Is not Null");
                                                	 Log.i("NullCheck", beerID == null ? "Is Null" : "Is not Null");
                                                	 
                                                	 aa.add(test.get(i).getBeerId());
                                                	 Log.i("aa", aa.toString());
                                                    
                                                		 
													}
                                                 for (int j = 0; j < aa.size(); j++) {
 													
                                                	 String name = aa.get(j).toString();
                                                	 Log.i("index of", name);
                                                	 if(name.compareTo(beerID)==0){
                                  	                    Log.i("TAG", "Match");
                                  	                    
                                  	                    hasLiked = true;
                                  	            }
											
                                                          
                                                	 String[] adduserTest;
                                                	 
                                             	}
                                                
                                                 
                                            	 /*
                                            		 for(String test : aa ){
                                            	            if(test.contentEquals(user.getUsername())){
                                            	                    Log.i("TAG", "Match");
                                            	            }else{
                                            	                Log.i("TAG", "Not a match");
                                            	                Log.i("TAG", test);
                                            	            }
                                            	        }*/                 
                                                  
                                                 
                                                 
                                                        if (!hasLiked) {
                                                          
                                                          //aa.add(usersName.getUsername());
                                                          Log.i("has Not ", "liked");
                                                         
                                                          aa.add(usersName.getUsername());
                                                          ParseObject favoriteBrew = new ParseObject("TasteGood");
                                                          
                                                          favoriteBrew.put("userName", usersName.getUsername());
                                                          favoriteBrew.put("beerName", beersName);
                                                          favoriteBrew.put("beerID", beerID);
                                                          //favoriteBrew.saveInBackground();
                                                          
                                                          beerParseObject.put("userLikes", usersName.getUsername());
                                                          
                                                        
                                                                 }
                                             	
                                                         // beerParseObject.saveInBackground();    
                                                         //beerObject.saveInBackground();
                                          //ParseObject *currentBeer =
 
                           
                                                       
                                                 }else{
                                                	 Log.i("else", "else");
                                                	 
                                                          List <String> aa = new ArrayList <String>();
                                                          aa.add(usersName.getUsername());
                                                         
                                                         
                                                         ParseObject favoriteBrew = new ParseObject("TasteGood");
                                                          favoriteBrew.put("userName", usersName.getUsername());
                                          favoriteBrew.put("beerName", beersName);
                                          favoriteBrew.put("beerID", beerID);
                                         
                                          List <String> aa2 = new ArrayList <String>();
                                         
                                         
                                          aa2.add(usersName.getUsername());
                                          favoriteBrew.saveInBackground();
                                         
                                         
                                          //userName2.add("userName", aa.toString());
                                          ParseObject beerClass = new ParseObject("BeerClass");
                                          beerClass.put("userName", usersName.getUsername());
                                          beerClass.put("Name", beersName);
                                          beerClass.put("beerID", beerID);
                                         
                                         
                                          beerClass.add("userLikes", user.getUsername());
                                         
                                          beerClass.saveInBackground();
                                          
                                                        
                                  }
                                  
                              } else {
                                  Log.d("score", "Error: " + e.getMessage());
                              }
                          }
                      
                      });
		        	         }
                    startActivity(n);
                }
                         });
							
						//Log.i("array",String.valueOf(dataArray));
						// TODO Auto-generated method stub
		      		      
						
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					

				}
				
			}
			

		}
			
	
	
}

