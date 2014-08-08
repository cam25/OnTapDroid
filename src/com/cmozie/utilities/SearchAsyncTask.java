package com.cmozie.utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import android.widget.TextView;

public class SearchAsyncTask {
	TextView beerName;
	 String beerNam;

	 String description;

	public static HashMap<String, String> map;
	public static List<Map<String,String>> theDataArray;
	
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
		
		
		try {
			JSONObject json = new JSONObject(result);
			JSONArray data = json.getJSONArray("data");
			theDataArray = new ArrayList<Map<String,String>>();
			for (int i = 0; i < data.length(); i++) {
				JSONObject one = data.getJSONObject(i);
				map = new HashMap<String, String>();
				 map.put("name", one.getString("name"));
				
			
				 theDataArray.add(map);
				 
				Log.i("array", theDataArray.toString());
				// TODO Auto-generated method stub
				
				
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
