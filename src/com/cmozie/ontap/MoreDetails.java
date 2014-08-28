package com.cmozie.ontap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cmozie.utilities.Network;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

public class MoreDetails extends Activity {
	private static final int IO_BUFFER_SIZE = 0;
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
	public static String imageURLS;
	public static TextView abvTextView;
	public static TextView breweryTextView;
	public static TextView styleTextView;
	public static ImageView beerImg;
	public static URL url;
	public static String imagURL;
	public static String beersID;
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
    try {
		//setting shareprefrences equal to my static string filename	
	
			
			if (url != null) {
				url = new URL(imagURL);
				
			}

			ImageRequest imageRecieve = new ImageRequest(); 
			imageRecieve.execute(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
    	//beersID = extras.getString("id");
    	//Log.i("id", beersID);
    
    //Log.i("extras", extras.getString("id"));
			beerLabel.setText(extras.getString("beerName"));
			Log.i("beerName", extras.getString("beerName"));
			 getApiResults(beerLabel.getText().toString());
			 getBreweryDetails(beersID);
        //beerLabel.setText("Boston Lager");
        tasteGood.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
				        MoreDetails.this);

				// Setting Dialog Title
				alertDialog2.setTitle("Taste Good!");

				// Setting Dialog Message
				alertDialog2.setMessage("Did you like this brew?");

			
				alertDialog2.setPositiveButton("Yes",
				        new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				                // Write your code here to execute after dialog
				                Toast.makeText(MoreDetails.this,
				                        "This beer will be added to the taste good beer section in the database.", Toast.LENGTH_SHORT)
				                        .show();
				               
				            }
				        });
				alertDialog2.setNegativeButton("NO",
				        new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				                // Write your code here to execute after dialog
				                Toast.makeText(MoreDetails.this,
				                        "Please select the taste bad button to add to your beers that you didnt like..", Toast.LENGTH_SHORT)
				                        .show();
				                dialog.cancel();
				            }
				        });

				// Showing Alert Dialog
				alertDialog2.show();
				ParseObject likedBeer = new ParseObject("TasteGood");
				likedBeer.put("beerID", beerName);
				
				likedBeer.saveInBackground();
			}
		});
        
        tasteBad.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
				        MoreDetails.this);

				// Setting Dialog Title
				alertDialog2.setTitle("Taste Bad!");

				// Setting Dialog Message
				alertDialog2.setMessage("Did This brew taste bad?");

			
				alertDialog2.setPositiveButton("YES",
				        new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				                // Write your code here to execute after dialog
				                Toast.makeText(MoreDetails.this,
				                        "This beer will be added to the bad tasting beers section in the database", Toast.LENGTH_SHORT)
				                        .show();
				            }
				        });
				// Setting Negative "NO" Btn
				alertDialog2.setNegativeButton("NO",
				        new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				                // Write your code here to execute after dialog
				                Toast.makeText(MoreDetails.this,
				                        "Please select the taste good button to add to your beers that taste good.", Toast.LENGTH_SHORT)
				                        .show();
				                dialog.cancel();
				            }
				        });

				// Showing Alert Dialog
				alertDialog2.show();
				ParseObject notLiked = new ParseObject("TasteBad");
				notLiked.put("beerID", beerName);
				
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

			 baseUrl = "https://api.brewerydb.com/v2/search/?q="+queryString+"?hasLabels=Y&type=beer&key=4b77a2665f85f929d4a87d30bbeae67b&format=json";
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
						Log.i("beerName", beerNam);
					}
					if (firstDataset.has("abv")) {
						abvText = firstDataset.getString("abv");
						abvTextView.setText(abvText);
					}
					if (firstDataset.has("id")) {
						
						beersID = firstDataset.getString("id");
						Log.i("id", beersID);
						
						//looping too much!!
						// getBreweryDetails(beersID);
					}
					if (firstDataset.has("labels")) {
						
						JSONObject labels = firstDataset.getJSONObject("labels");
						 imageURLS = labels.getString("large");
						url = new URL(imageURLS);
						Log.i("URL", imageURLS);
						ImageRequest imageRecieve = new ImageRequest(); 
						//imageRecieve.execute(url);
						//Bitmap bmp = BitmapFactory.decodeFile(new java.net.URL(url).openStream());  
						//beerImg.setImageBitmap(bmp);
						new GetImage((ImageView) findViewById(R.id.imageView1))
			            .execute(imageURLS);
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
				AlertDialog.Builder alert = new AlertDialog.Builder(MoreDetails.this);
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
