package com.cmozie.ontap;

import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.cmozie.utilities.Network;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MoreDetails extends Activity {
	public TextView beerLabel;
	public ImageButton tasteGood;
	public String beerName;
	public ImageButton tasteBad;
	public static ProgressDialog loading;
	public static String beerNam;
	public static TextView beerDescription;
	public static String beerDescriptionString;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moredetails);
        
     
      
         beerLabel = (TextView) findViewById(R.id.beerNameMore);
         beerName = beerLabel.getText().toString();
        tasteGood = (ImageButton)findViewById(R.id.tasteGoodButn);
        tasteBad = (ImageButton)findViewById(R.id.tasteBad);
        beerDescription = (TextView)findViewById(R.id.beerDescription);
        
        //beerLabel.setText("Boston Lager");
        tasteGood.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseObject gameScore = new ParseObject("OnTapData");
				gameScore.put("beerName", beerName);
				
				gameScore.saveInBackground();
			}
		});
        
        tasteBad.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseObject gameScore = new ParseObject("OnTapData");
				gameScore.put("beerName", beerName);
				
				gameScore.saveInBackground();
			}
		});
        
        getApiResults(beerName);
        
    }
	
	public  void getApiResults(String beer){

		String baseUrl = "http://api.brewerydb.com/v2/search/?q="+ beer +"/?description/?hasLabels=Y/&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";



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

			 baseUrl = "http://api.brewerydb.com/v2/search/?q="+queryString+"?hasLabels=Y&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
			URL finalURL;
					try {

				 finalURL = new URL(baseUrl);
				 //finalURL2 = new URL(query);
				 AsyncRequest queryRequest = new AsyncRequest();


				 	Log.i("FinalURL", finalURL.toString());


					 queryRequest.execute(finalURL);

			} catch (Exception e) {
				// TODO: handle exception
				Log.i("BAD URL", "URL MALFORMED");
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
			loading = new ProgressDialog(getApplicationContext());
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


				JSONObject labels = data.getJSONObject("labels");

				// url = new URL(labels.getString("large"));
				Log.i("WTDURL", result);

				if (data.has("name")) {

					beerNam = data.getString("name");
					beerLabel.setText(beerNam);
				}
				// TODO Auto-generated method stub
				//Log.i("URL", result);

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

				if (labels.has("large")) {
					//ImageRequest image = new ImageRequest();
					//image.execute(url);

				}

				Log.i("beer", beerNam);
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

}
