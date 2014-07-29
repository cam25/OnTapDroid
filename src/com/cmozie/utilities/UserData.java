package com.cmozie.utilities;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class UserData extends ParseObject {

	public UserData(){
		
	}
	
	public String getBeerName(){
		return getString("beerName");
		
	}
	
	public ParseUser getAuthor(){
		return getParseUser("userName");
	}
	
	public void  setBeerName(String beer){
		put("beerName", beer);
	}
}
