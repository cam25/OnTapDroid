package com.cmozie.utilities;




import java.util.ArrayList;
import java.util.List;

import com.cmozie.ontap.MoreDetails;

import com.cmozie.ontap.R;
import com.cmozie.utilities.*;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
		      
		      ParseQuery<ParseObject> query = ParseQuery.getQuery("OnTapData");
		      query.orderByDescending("beerName");
		      query.findInBackground(new FindCallback<ParseObject>() {
		          public void done(List<ParseObject> tasteGoodBrews, ParseException e) {
		              if (e == null) {
		            	  for (int i=0; i< tasteGoodBrews.size(); i++){
		            		brews = tasteGoodBrews.get(0).getString("beerName");
		            		
		            		
		            		  foos.add(brews);
		            		  
		            		  
		            		 
		            		  
		        		     

		            	  }
		            	  Log.i("brews", brews);
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
   		           
   		           
   		           n.putExtra("position", position);
   		           startActivity(n);
   		       }
				});
		      
		    
	        return rootView;
	    }
		
		
		

	}
	public class TasteBadFragment extends Fragment {
		
		ListView badBrews;
		
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.tastebadfragment, container, false);
	         
	        
		      badBrews = (ListView) rootView.findViewById(R.id.badBrews);
String [] allBadBeers = new String [] {"Third Shift", "90 Minute IPA", "Samuel Adams Cream Stout", "Harp", "Four Loco"};
		      
		      
		      ArrayAdapter<String>badBeers = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,allBadBeers);
		      
		      badBrews.setAdapter(badBeers);
		      
		      badBrews.setOnItemClickListener(new OnItemClickListener() {
  		    	  
   		    	 public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) 
   		       {

   		           Intent n = new Intent(getActivity(), MoreDetails.class);
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
	        tasteBadButn = (ImageButton) rootView.findViewById(R.id.tasteBad);
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
		
		ListView searchedBrews;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.beertionaryfragment, container, false);
		      searchedBrews = (ListView) rootView.findViewById(R.id.searchedBrews);
		      String [] allSearchedBrews = new String [] {"Boston Lager", "Rebel IPA", "Cream Stout", "Irish Red", "Summer Ale"};
		      		      
		      		      
		      		      ArrayAdapter<String>searches = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,allSearchedBrews);
		      		      
		      		      searchedBrews.setAdapter(searches);
		      		      
		      		      searchedBrews.setOnItemClickListener(new OnItemClickListener() {
		      		    	  
		      		    	 public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) 
		      		       {

		      		           Intent n = new Intent(getActivity(), MoreDetails.class);
		      		           n.putExtra("position", position);
		      		           startActivity(n);
		      		       }
						});
		      		     
	        return rootView;
	    }

	}

}
