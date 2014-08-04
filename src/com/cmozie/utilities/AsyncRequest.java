
package com.cmozie.utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

	// TODO: Auto-generated Javadoc
/**
	 * The Class AsyncRequest.
	 */
	@SuppressLint("NewApi")
	public class AsyncRequest extends AsyncTask<URL, Void, String>{
		 TextView beerName;
		 String beerNam;

		 String description;

		public static HashMap<String, String> map;
		public static List<Map<String,String>> theDataArray;

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

