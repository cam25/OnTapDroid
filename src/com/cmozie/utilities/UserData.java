package com.cmozie.utilities;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("UserData")
public class UserData extends ParseObject {

	public UserData(){
		
	}
	public void setUser(ParseUser user)
	{
		put("userName",user);
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
