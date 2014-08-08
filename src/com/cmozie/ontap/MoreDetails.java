package com.cmozie.ontap;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cmozie.utilities.Network;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	public static String abvText;
	public static String breweryText;
	public static String styleText;
	
	public static TextView abvTextView;
	public static TextView breweryTextView;
	public static TextView styleTextView;
	public static ImageView beerImg;
	public static URL url;
	public static String imagURL;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moredetails);
        
     
        
        
         beerLabel = (TextView) findViewById(R.id.beerNameMore);
         beerName = beerLabel.getText().toString();
         
         abvTextView = (TextView)findViewById(R.id.abvText);
         breweryTextView = (TextView)findViewById(R.id.breweryText);
         styleTextView = (TextView)findViewById(R.id.styleText);
         beerImg = (ImageView)findViewById(R.id.imageView1);
         
         
        tasteGood = (ImageButton)findViewById(R.id.tasteGoodButn);
        tasteBad = (ImageButton)findViewById(R.id.tasteBad);
        beerDescription = (TextView)findViewById(R.id.beerDescription);
        Bundle extras = getIntent().getExtras();
        
    beerDescription.setMovementMethod(new ScrollingMovementMethod());
    
    
			beerLabel.setText(extras.getString("beerName"));
			
			 getApiResults(beerLabel.getText().toString());
		
        //beerLabel.setText("Boston Lager");
        tasteGood.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseObject likedBeer = new ParseObject("TasteGood");
				likedBeer.put("beerID", beerName);
				
				likedBeer.saveInBackground();
			}
		});
        
        tasteBad.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseObject notLiked = new ParseObject("TasteBad");
				notLiked.put("beerName", beerName);
				
				notLiked.saveInBackground();
			}
		});
        
       
        
    }
	
	
	public  void getApiResults(String beer){

		String baseUrl = "https://api.brewerydb.com/v2/search/?q="+ beer +"?hasLabels=Y/&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";



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

			 baseUrl = "https://api.brewerydb.com/v2/search/?q="+queryString+"&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
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
			loading = new ProgressDialog(MoreDetails.this);
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
				//JSONObject data = json.getJSONObject("data");
				JSONArray data = json.getJSONArray("data");
				
				
				
				for (int i = 0; i < data.length(); i++) {
					
					JSONObject firstDataset = data.getJSONObject(0);
					
					//Log.i("first", firstDataset.toString());
					if (firstDataset.has("name")) {
						beerNam = firstDataset.getString("name");
						beerLabel.setText(beerNam);
					}
					if (firstDataset.has("abv")) {
						abvText = firstDataset.getString("abv");
						abvTextView.setText(abvText);
					}
					if (firstDataset.has("labels")) {
						
						
					}
					if (firstDataset.has("style")) {
						JSONObject style = firstDataset.getJSONObject("style");
						styleText = style.getString("name");
						styleTextView.setText(styleText);
					}
					
					if (firstDataset.has("description")) {
						beerDescriptionString = firstDataset.getString("description");
						beerDescription.setText(beerDescriptionString);
					}else if (firstDataset.has("style")) {

						JSONObject style = firstDataset.getJSONObject("style");

						String styleDescription = style.getString("description");
						

						
						
						Log.i("Style", styleDescription);
						beerDescription.setText(styleDescription);

						}else{

							beerDescription.setText("No Description Available");

					}
					
					
					
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
	private class ImageRequest extends AsyncTask<URL, Void, Drawable>
    {

            /* (non-Javadoc)
             * @see android.os.AsyncTask#doInBackground(Params[])
             */
            @Override
            protected Drawable doInBackground(URL... urls)
            {
                    
        Drawable draw = null;
      
                draw = Network.LoadImageFromWebOperations(imagURL);
      
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
    
}
