package com.cmozie.ontap;

public class BeerObject {
	
	String user_name;
	String beer_id;
	
	public String getUserName(){
		return user_name;
	}

	public String getBeerId(){
		return beer_id;
	}
	
	
	public void setUserName(String name){
		user_name = name;
		
	}
	
	public void setBeerId(String beer){
		beer_id = beer;
	
	}

}



