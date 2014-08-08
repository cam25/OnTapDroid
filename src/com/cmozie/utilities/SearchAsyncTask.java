package com.cmozie.utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class SearchAsyncTask extends AsyncTask<URL, Void, String>{
	TextView beerName;
	 String beerNam;

	 String description;
	 Context context;
	 public static  List<Map<String,String>> dataArray;
	public static HashMap<String, String> map;
	public static List<Map<String,String>> theDataArray;
	
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
			
			
			progressIndicator = new ProgressDialog(context);
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
					//description
					if (one.has("description")) {
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
					
					//add map contents to array
					dataArray.add(map);
					 
					
						
						
					Log.i("array",String.valueOf(dataArray));
					// TODO Auto-generated method stub
					
					
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				

			}
			
		}

	}
		