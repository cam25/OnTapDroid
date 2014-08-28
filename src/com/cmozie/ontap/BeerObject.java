package com.cmozie.ontap;

public class BeerObject {
	
	String user_name;
	String beer_id;
	String beer_name;
	String user_dislikes;
	
	public String getUserName(){
		return user_name;
	}
	
	public String getBeerName(){
		return beer_name;
	}
	public String getDislikes(){
		return user_dislikes;
	}
	
	public String getBeerId(){
		return beer_id;
	}
	
	public void setDislikes(String dislikes){
		user_dislikes = dislikes;
		
	}
	public void setUserName(String name){
		user_name = name;
		
	}
	
	public void setBeerName(String beerName){
		beer_name = beerName;
		
	}
	
	public void setBeerId(String beer){
		beer_id = beer;
	
	}

}



