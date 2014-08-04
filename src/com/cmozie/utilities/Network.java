
package com.cmozie.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class Network.
 */
public class Network {
	
	private static final int IO_BUFFER_SIZE = 0;
	static Boolean connection = false;
	static String connectionType = "NotReady";
	
	/**
	 * Gets the connection type.
	 *
	 * @param context the context
	 * @return the connection type
	 */
	public static String getConnectionType(Context context){
		networkData(context);
		return connectionType;
		
	}
	
	/**
	 * Gets the connection status.
	 *
	 * @param context the context
	 * @return the connection status
	 */
	public static Boolean getConnectionStatus (Context context){
		networkData(context);
		return connection;
		
	}

/**
 * Network data.
 *
 * @param context the context
 */
private static void networkData(Context context){
	ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo info = connectManager.getActiveNetworkInfo();
	 
	if (info != null) {
		if (info.isConnected()) {
			connectionType = info.getTypeName();
			connection = true;
		}
	}
}

/**
 * Load image from web operations.
 *
 * @param url the url
 * @return the drawable
 */
public static Drawable LoadImageFromWebOperations(String url) {
    try {
        InputStream inputStream = (InputStream) new URL(url).getContent();
        Drawable image = Drawable.createFromStream(inputStream, "src name");
        return image;
    } catch (Exception e) {
        return null;
    }
}


/**
 * URL string response.
 *
 * @param url the url
 * @return the string
 */
public static String URLStringResponse(URL url){
	String response = "";
	
	try {
		URLConnection urlConnect = url.openConnection();
		BufferedInputStream bin = new BufferedInputStream(urlConnect.getInputStream());
		
		byte[] bytes = new byte[1024];
		
		int readBytes = 0;
		
		StringBuffer bufferedResponse = new StringBuffer();
		
		while((readBytes = bin.read(bytes)) != -1){
			response = new String(bytes,0,readBytes);
			bufferedResponse.append(response);
			
		}
		return bufferedResponse.toString();
	
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	
	return response;
	
}
}
