package com.cmozie.utilities;




import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cmozie.ontap.MoreDetails;

import com.cmozie.ontap.R;
import com.cmozie.utilities.*;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.cmozie.*;
import com.cmozie.fragmentclasses.*;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class PagerAdapter extends FragmentPagerAdapter {

	public PagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return new TasteGoodFragment();
		case 1:
			return new TasteBadFragment();
		case 2:
			return new SuggestionsFragment();
		case 3:
			return new BeertionaryFragment();
			
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}
	public class TasteGoodFragment extends Fragment {
		ProgressDialog loading;
		ListView goodBrews;
		public String brews;
		public ArrayList<String> foos;
		public ArrayAdapter<String>beers;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
			foos = new ArrayList<String>();
	        View rootView = inflater.inflate(R.layout.tastegoodfragment, container, false);
	        //View theList = rootView.findViewById(R.id.favoriteBrews);
		      goodBrews = (ListView) rootView.findViewById(R.id.favoriteBrews);
		      String [] allGoodBeers = new String [] {"Rebel IPA", "Boston Lager", "Snake Dog IPA", "Summer Shandy", "Blue Moon"};
		     // ParseUser currentUser = ParseUser.getCurrentUser();
		      //if (currentUser != null) {
		        // do stuff with the user
		      
		    //else if the user already has registered send them to the preference class
	            ParseUser currentUser = ParseUser.getCurrentUser();
	            if (currentUser != null) { 
	            	
	            	Log.i("test", currentUser.getUsername());
		      ParseQuery<ParseObject> query = ParseQuery.getQuery("TasteGood");
		      query.whereEqualTo("userName", currentUser.getUsername());
		     
		      query.findInBackground(new FindCallback<ParseObject>() {
		          public void done(List<ParseObject> tasteGoodBrews, ParseException e) {
		              if (e == null) {
		            	  for (int i=0; i< tasteGoodBrews.size(); i++){
		            		brews = tasteGoodBrews.get(i).getString("beerName");
		            		
		            		//Log.i("tastegood ", tasteGoodBrews.toString());
		            		  foos.add(brews);
		            		  
		            		  
		            		  

		            	  }
		            	  //Log.i("brews", brews);
		                  Log.i("score", "Retrieved " + tasteGoodBrews.size() + " scores");
		                  beers = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,foos);
		    		      
		    		      goodBrews.setAdapter(beers);
		              } else {
		                  Log.d("score", "Error: " + e.getMessage());
		              }
		          }
		      });
		      
		      
		       Log.i("foos size ", foos.size() + " items in array");
		    
		      goodBrews.setOnItemClickListener(new OnItemClickListener() {
  		    	  
   		    	 public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) 
   		       {
   		    		
   		    		 
   		           Intent n = new Intent(getActivity(), MoreDetails.class);
   		           
   		           Object obj = arg0.getItemAtPosition(position);
   		           String name = obj.toString();
   		           Log.i("obj", name);
   		           
   		           n.putExtra("beerName", name);
   		           
   		           n.putExtra("position", position);
   		           startActivity(n);
   		       }
				});
		      }
		    
	        return rootView;
	    }
		
		
		

	}
	
	
	public class TasteBadFragment extends Fragment {
		
		ListView badBrews;
		public ArrayAdapter<String>beers2;
		public ArrayList<String> foos2;
		public String brews2;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.tastebadfragment, container, false);
	         
	        foos2 = new ArrayList<String>();
		      badBrews = (ListView) rootView.findViewById(R.id.badBrews);
String [] allBadBeers = new String [] {"Third Shift", "90 Minute IPA", "Samuel Adams Cream Stout", "Harp", "Four Loco"};
		      
		ParseUser currentUser = ParseUser.getCurrentUser();
				if (currentUser != null) { 
	
		Log.i("test", currentUser.getUsername());
			ParseQuery<ParseObject> query = ParseQuery.getQuery("TasteBad");
				query.whereEqualTo("userName", currentUser.getUsername());

				query.findInBackground(new FindCallback<ParseObject>() {
			          public void done(List<ParseObject> tasteBadBrews, ParseException e) {
			              if (e == null) {
			            	  for (int i=0; i< tasteBadBrews.size(); i++){
			            		brews2 = tasteBadBrews.get(i).getString("beerID");
			            		
			            		
			            		  foos2.add(brews2);
			            		  
			            		  Log.i("tastebad", tasteBadBrews.toString());
				              } 
			            	  Log.i("score", "Retrieved " + tasteBadBrews.size() + " scores");
			            	  
			            	  
			                  beers2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,foos2);
			    		      
			                  
			                  
			    		      badBrews.setAdapter(beers2);
			              }else {
			                  Log.d("score", "Error: " + e.getMessage());
			              }
			          
			              
			              }
				      });
			            		  
				
				}
		      //ArrayAdapter<String>badBeers = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,allBadBeers);
		      
		      //badBrews.setAdapter(badBeers);
		      
		      badBrews.setOnItemClickListener(new OnItemClickListener() {
  		    	  
   		    	 public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) 
   		       {

   		           Intent n = new Intent(getActivity(), MoreDetails.class);
   		        Object obj = arg0.getItemAtPosition(position);
   		        String name = obj.toString();
   		     n.putExtra("beerName", name);
   		           n.putExtra("position", position);
   		           startActivity(n);
   		       }
				});
	        return rootView;
	    }
	}
	
	public class SuggestionsFragment extends Fragment {
		ImageButton tasteGoodButn;
		ImageButton tasteBadButn;
		public ParseObject onTapData;
		public  TextView beerLabel;
		public  ParseUser usersName;
		public  String userString;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.suggestionsfragment, container, false);
	         
	        beerLabel = (TextView) rootView.findViewById(R.id.beerNameLabel);
	        tasteGoodButn = (ImageButton) rootView.findViewById(R.id.tasteGood);
	        tasteBadButn = (ImageButton) rootView.findViewById(R.id.tasteBadButton);
	         usersName = ParseUser.getCurrentUser();
	         userString = usersName.getUsername().toString();
	         Log.i("Logged in as...", userString);
	         Log.i("beerName", beerLabel.getText().toString());
	       
	         
	        tasteGoodButn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					  
					  
					AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
					        getActivity());

					// Setting Dialog Title
					alertDialog2.setTitle("Taste Good!");

					// Setting Dialog Message
					alertDialog2.setMessage("Did you like this brew?");

				
					alertDialog2.setPositiveButton("Yes",
					        new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int which) {
					                // Write your code here to execute after dialog
					                Toast.makeText(getActivity(),
					                        "This beer will be added to the taste good beer section in the database.", Toast.LENGTH_SHORT)
					                        .show();
					               
					            }
					        });
					alertDialog2.setNegativeButton("NO",
					        new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int which) {
					                // Write your code here to execute after dialog
					                Toast.makeText(getActivity(),
					                        "Please select the taste bad button to add to your beers that you didnt like..", Toast.LENGTH_SHORT)
					                        .show();
					                dialog.cancel();
					            }
					        });

					// Showing Alert Dialog
					alertDialog2.show();
					
				}
			});
	        
	        
	        tasteBadButn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
					        getActivity());

					// Setting Dialog Title
					alertDialog2.setTitle("Taste Bad!");

					// Setting Dialog Message
					alertDialog2.setMessage("Did This brew taste bad?");

				
					alertDialog2.setPositiveButton("YES",
					        new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int which) {
					                // Write your code here to execute after dialog
					                Toast.makeText(getActivity(),
					                        "This beer will be added to the bad tasting beers section in the database", Toast.LENGTH_SHORT)
					                        .show();
					            }
					        });
					// Setting Negative "NO" Btn
					alertDialog2.setNegativeButton("NO",
					        new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int which) {
					                // Write your code here to execute after dialog
					                Toast.makeText(getActivity(),
					                        "Please select the taste good button to add to your beers that taste good.", Toast.LENGTH_SHORT)
					                        .show();
					                dialog.cancel();
					            }
					        });

					// Showing Alert Dialog
					alertDialog2.show();
				}
			});
	        return rootView;
	    }

	}
	public class BeertionaryFragment extends Fragment {

public  HashMap<String, String> map;
public  List<Map<String,String>> dataArray;
		ListView searchedBrews;
		public EditText searchField; 
		public Button searchButton;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.beertionaryfragment, container, false);
	        
	        
	  
		      		     
	        return rootView;
	    }
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);

		      searchedBrews = (ListView) getActivity().findViewById(R.id.searchedBrews);
		      
		      searchField = (EditText)getActivity().findViewById(R.id.searchField);
		      searchButton = (Button)getActivity().findViewById(R.id.searchButton);
		      
		      searchButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String search = searchField.getText().toString();

	    			 Log.i("search", search);
	    			getApiResults(search);
				}
			});
		      
		      /*searchField.setOnEditorActionListener(new OnEditorActionListener() {
					
			    	
			    	 @Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						// TODO Auto-generated method stub
			    		 
			    		 if (actionId == EditorInfo.IME_ACTION_GO) {
							
			    			 
			    			 getActivity();
			    			 
			    			 //hide keyboard
							InputMethodManager inputManager = (InputMethodManager)            
			    					  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
			    					    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),      
			    					    InputMethodManager.HIDE_NOT_ALWAYS);
						}
			    		 
						return false;
					}

				
				});*/
		      //String [] allSearchedBrews = new String [] {"Boston Lager", "Rebel IPA", "Cream Stout", "Irish Red", "Summer Ale"};
		      		      
		      		      
		      
		      		    //  ArrayAdapter<String>searches = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,allSearchedBrews);
		      
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
				
				
				progressIndicator = new ProgressDialog(getActivity());
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
						 
						
						ListAdapter adapter = new SimpleAdapter(getActivity(), dataArray, R.layout.listitems, new String[]{"name","company" },new int[]{R.id.listBeerType, R.id.listBeerCompany});
		      		      searchedBrews.setAdapter(adapter);
		      		      
		      		      searchedBrews.setOnItemClickListener(new OnItemClickListener() {
		      		    	  
		      		    	 public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) 
		      		       {

		      		           Intent n = new Intent(getActivity(), MoreDetails.class);
		      		           n.putExtra("position", position);
		      		         Object obj = arg0.getItemAtPosition(position);
		        		        String name = obj.toString();
		        		        String beerID = dataArray.get(position).get("id");
		        		        
		        		        Log.i("beerid", beerID);
		        		        n.putExtra("id", beerID);
		        		     n.putExtra("beerName", name);
		      		           
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

}
