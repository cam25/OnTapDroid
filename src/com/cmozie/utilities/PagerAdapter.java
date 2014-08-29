package com.cmozie.utilities;




import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.cmozie.ontap.BeerObject;
import com.cmozie.ontap.MoreDetails;

import com.cmozie.ontap.R;

import com.cmozie.utilities.*;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.cmozie.*;
import com.cmozie.fragmentclasses.*;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
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
		public  Map<String, String> map;
		public  List<Map<String,String>> dataArray;

		public ArrayList<BeerObject> beer_Array;
		public ArrayAdapter<String>beers;
		public String beerids;

		public ArrayList<String> beerDataArray; 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			foos = new ArrayList<String>();
			beer_Array = new ArrayList<BeerObject>();
			Context context = getActivity();




			setRetainInstance(true);


			View rootView = inflater.inflate(R.layout.tastegoodfragment, container, false);
			//View theList = rootView.findViewById(R.id.favoriteBrews);
			goodBrews = (ListView) rootView.findViewById(R.id.favoriteBrews);
			String [] allGoodBeers = new String [] {"Rebel IPA", "Boston Lager", "Snake Dog IPA", "Summer Shandy", "Blue Moon"};
			// ParseUser currentUser = ParseUser.getCurrentUser();
			//if (currentUser != null) {
			// do stuff with the user

			beerDataArray = new ArrayList<String>();



			ArrayList beerList = new ArrayList();


			//else if the user already has registered send them to the preference class
			ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser != null) { 

				Log.i("test", currentUser.getUsername());
				ParseQuery<ParseObject> query = ParseQuery.getQuery("TasteGood");
				query.whereEqualTo("userName", currentUser.getUsername());

				query.findInBackground(new FindCallback<ParseObject>() {
					public void done(List<ParseObject> tasteGoodBrews, ParseException e) {
						if (e == null) {

							dataArray = new ArrayList<Map<String,String>>();

							for (int i=0; i< tasteGoodBrews.size(); i++){

								BeerObject beer_object = new BeerObject();

								map = new HashMap<String, String>();

								//brews = tasteGoodBrews.get(0).getString("beerName");
								//beerids = tasteGoodBrews.get(0).getString("beerID");



								//Log.i("tastegood ", beerids);
								//foos.add(brews);

								map.put("name", tasteGoodBrews.get(i).getString("beerName"));
								map.put("id", tasteGoodBrews.get(i).getString("beerID"));

								dataArray.add(map);
								Log.i("MAP", map.toString());
								//map.put("name", brews);
								//map.put("id", beerids);

								//foos.add(brews);

								/*for (Object object : dataArray) {
								String test = dataArray.get;
							}*/

								//map.put("name",brews);
								// map.put("id",beerids);



								//Log.i("brews", map.toString());
								Log.i("DATA ARRAY", dataArray.toString());


								//dataArray.add(map);
							}
							//ListAdapter adapter = ListAdapter(this, R.layout.listitems, beer_Array));


							ListAdapter adapter = new SimpleAdapter(getActivity(), dataArray, R.layout.listitems, new String[]{"name","company" },new int[]{R.id.listBeerType, R.id.listBeerCompany});

							goodBrews.setAdapter(adapter);



							//Log.i("brews", brews);
							Log.i("score", "Retrieved " + tasteGoodBrews.size() + " scores");
							//beers = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,foos);



						} else {
							Log.d("score", "Error: " + e.getMessage());
						}


						//Log.i("foos size ", foos.size() + " items in array");

						goodBrews.setOnItemClickListener(new OnItemClickListener() {

							public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3) 
							{


								Intent n = new Intent(getActivity(), MoreDetails.class);

								Object obj = arg0.getItemAtPosition(position);
								String name = obj.toString();
								String brewID = dataArray.get(position).get("id");
								Log.i("obj", name);

								n.putExtra("beerName", name);
								n.putExtra("id", brewID);
								n.putExtra("position", position);
								startActivity(n);
							}
						});

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
			if (currentUser != null) 
			{ 

				Log.i("test", currentUser.getUsername());
				ParseQuery<ParseObject> query = ParseQuery.getQuery("TasteBad");
				query.whereEqualTo("userName", currentUser.getUsername());

				query.findInBackground(new FindCallback<ParseObject>() 
						{
					public void done(List<ParseObject> tasteBadBrews, ParseException e) 
					{
						if (e == null) 
						{
							for (int i=0; i< tasteBadBrews.size(); i++)
							{
								brews2 = tasteBadBrews.get(i).getString("beerName");


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

			badBrews.setOnItemClickListener(new OnItemClickListener() 
			{

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
		public  ProgressDialog loading;
		public  String beerNam;
		public  TextView beerDescription;
		public  String beerDescriptionString;
		public  String abvText;
		public  String breweryText;
		public  String styleText;
		public  String imageURLS;
		public  TextView abvTextView;
		public  TextView breweryTextView;
		public  TextView styleTextView;
		public  ImageView beerImg;
		public String beersID;
		public  URL url;
		public  String imagURL;
		public ParseUser user;
		public  List <String> aa;
		public  List <String> udislike;
		public  ArrayList<BeerObject> test;
		public  ArrayList<BeerObject> dtest2;
		public  BeerObject beerObject;
		public  ParseQuery<ParseObject> query;
		public String string;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.suggestionsfragment, container, false);

			beerLabel = (TextView) rootView.findViewById(R.id.beerNameLabel);

			beerImg = (ImageView)rootView.findViewById(R.id.beerImageSuggest);
			abvTextView = (TextView)rootView.findViewById(R.id.abvSuggestLabel);
			breweryTextView = (TextView) rootView.findViewById(R.id.brewerySuggestLabel);
			styleTextView = (TextView) rootView.findViewById(R.id.styleTextSuggest);
			beerDescription = (TextView) rootView.findViewById(R.id.beerDescriptionSuggest);



			tasteGoodButn = (ImageButton) rootView.findViewById(R.id.tasteGood);
			tasteBadButn = (ImageButton) rootView.findViewById(R.id.tasteBadButton);
			usersName = ParseUser.getCurrentUser();
			userString = usersName.getUsername().toString();
			Log.i("Logged in as...", userString);
			Log.i("beerName", beerLabel.getText().toString());


			ParseCloud.callFunctionInBackground("getUserData", new HashMap<String, Object>(), new FunctionCallback<String>() {
				public void done(String result, ParseException e) {
					if (e == null) {
						Log.i("results", result);
						getApiResults(result);
						
						string = result;
					}
				}
			});


			tasteGoodButn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					  ParseObject favoriteBrew = new ParseObject("TasteGood");
                     
                    favoriteBrew.put("beerID", string);
                    
                   
                    favoriteBrew.saveInBackground();
                   
                   
                    //userName2.add("userName", aa.toString());
                    ParseObject beerClass = new ParseObject("BeerClass");
                   
                    beerClass.put("beerID", string);
                   
                   
                  
                   
                    beerClass.saveInBackground();
				}
		});


			tasteBadButn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					  ParseObject favoriteBrew = new ParseObject("TasteBad");
                      
                    favoriteBrew.put("beerID", string);
                   
                    List <String> aa2 = new ArrayList <String>();
                   
                   
                    aa2.add(user.getUsername());
                    favoriteBrew.saveInBackground();
                   
                   
                    //userName2.add("userName", aa.toString());
                    ParseObject beerClass = new ParseObject("BeerClass");
                    beerClass.put("userName", user.getUsername());
                    beerClass.put("Name", beerLabel.getText().toString());
                    beerClass.put("beerID", beersID);
                   
                   
                    beerClass.put("userDislikes", user.getUsername());
                   
                    beerClass.saveInBackground();
				}
			});
			return rootView;
	}
	public  void getApiResults(String beer){

		String baseUrl = "http://api.brewerydb.com/v2/beer/"+beer+"/?withBreweries=Y&key=4b77a2665f85f929d4a87d30bbeae67b";



		String queryString;
		String queryString2;
		try {


			queryString = URLEncoder.encode(beer,"UTF-8");
			Log.i("query", queryString);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR-URL", "ENCODING ISSUE");
			queryString = "";
		}

		baseUrl = "http://api.brewerydb.com/v2/beer/"+beer+"?withLabels=Y&key=4b77a2665f85f929d4a87d30bbeae67b";
		URL finalURL;
		try {

			Log.i("baseUrl", baseUrl);
			finalURL = new URL(baseUrl);
			//finalURL2 = new URL(query);
			AsyncRequest queryRequest = new AsyncRequest();


			Log.i("FinalURL", finalURL.toString());


			queryRequest.execute(finalURL);

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", e.toString());
		}


	}


	private class AsyncRequest extends AsyncTask<URL, Void, String>{


		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			loading = new ProgressDialog(getActivity());
			loading.setMessage("Getting Info...");
			loading.setIndeterminate(false);
			loading.setCancelable(true);
			loading.show();

		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
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
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			try {
				JSONObject json = new JSONObject(result);
				JSONObject data = json.getJSONObject("data");







				//Log.i("first", firstDataset.toString());
				if (data.has("name")) {
					beerNam = data.getString("name");
					beerLabel.setText(beerNam);
					Log.i("beerName", beerNam);
				}
				if (data.has("abv")) {
					abvText = data.getString("abv");
					abvTextView.setText("(ABV) of Beer:" + abvText + "%");

				}
				if (data.has("id")) {

					beersID = data.getString("id");
					Log.i("id", beersID);

					//looping too much!!
					// getBreweryDetails(beersID);
				}
				if (data.has("labels")) {

					JSONObject labels = data.getJSONObject("labels");
					imageURLS = labels.getString("large");
					url = new URL(imageURLS);
					Log.i("URL", imageURLS);
					ImageRequest imageRecieve = new ImageRequest(); 
					//imageRecieve.execute(url);
					//Bitmap bmp = BitmapFactory.decodeFile(new java.net.URL(url).openStream());  
					//beerImg.setImageBitmap(bmp);
					new GetImage((ImageView) getActivity().findViewById(R.id.beerImageSuggest))
					.execute(imageURLS);
				}
				if (data.has("style")) {
					JSONObject style = data.getJSONObject("style");
					styleText = style.getString("name");
					styleTextView.setText(styleText);
				}

				if (data.has("description")) {
					beerDescriptionString = data.getString("description");
					beerDescription.setText(beerDescriptionString);
				}else if (data.has("style")) {

					JSONObject style = data.getJSONObject("style");

					String styleDescription = style.getString("description");




					Log.i("Style", styleDescription);
					beerDescription.setText(styleDescription);

				}else{

					beerDescription.setText("No Description Available");

				}







				loading.dismiss();
			} catch (JSONException e) {
				// TODO: handle exception

				e.printStackTrace();
				beerLabel.setText(beerNam);
				beerDescription.setText("No description");

			}

			catch (Exception e) {
				// TODO: handle exception
				beerLabel.setText("No Beer Title");
			}




		}

	}
	public  void getBreweryDetails(String beerId){


		String query = "http://api.brewerydb.com/v2/beer/"+beerId+"/?withBreweries=Y&key=4b77a2665f85f929d4a87d30bbeae67b";

		String queryString;
		String queryString2;
		try {


			queryString2 = URLEncoder.encode(query,"UTF-8");

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR-URL", "ENCODING ISSUE");
			queryString = "";
		}

		query = "http://api.brewerydb.com/v2/beer/"+beerId+"/?withBreweries=Y&key=4b77a2665f85f929d4a87d30bbeae67b";
		URL finalURL2;
		try {

			finalURL2 = new URL(query);
			Log.i("finalurl", finalURL2.toString());
			brewDetails breweryDetailRequest = new brewDetails();

			breweryDetailRequest.execute(finalURL2);




		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}


	}

	private class GetImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public GetImage(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
	private class ImageRequest extends AsyncTask<URL, Void, Drawable>
	{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Drawable doInBackground(URL... urls)
		{

			Drawable draw = null;
			Log.i("test", "image");
			draw = Network.LoadImageFromWebOperations(imageURLS);
			//Log.i("draw", draw.toString());
			return draw;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Drawable result) 
		{
			loading.dismiss();
			//set background

			beerImg.setBackground(result);


		}
	}
	private class brewDetails extends AsyncTask<URL, Void, String>{




		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
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
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			try {
				JSONObject json = new JSONObject(result);
				JSONObject data = json.getJSONObject("data");

				JSONArray breweryDetails = data.getJSONArray("breweries");
				JSONObject locationDetails = breweryDetails.getJSONObject(0);



				for (int i = 0; i < breweryDetails.length(); i++) {
					JSONObject one = breweryDetails.getJSONObject(i);
					JSONArray locations = locationDetails.getJSONArray("locations");


					//Log.i("two", locations.toString());


					if (one.has("name")) {
						String name = one.getString("name");
						Log.i("breweryName", name);
						breweryTextView.setText(name);

					}





				}

			} catch (Exception e) {
				// TODO: handle exception
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle("Not Available");
				alert.setMessage("Brewery details not available for this beer.");
				alert.setCancelable(false);
				alert.setPositiveButton("Alright", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
					}
				});
				alert.show();
				e.printStackTrace();


			}
		}
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
