package com.cmozie.utilities;



import com.cmozie.ontap.R;
import com.cmozie.utilities.*;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmozie.*;
import com.cmozie.fragmentclasses.*;

public class PagerAdapter extends FragmentPagerAdapter {

	public PagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return new TasteGoodFragment();
		case 1:
			return new TasteBadFragment();
		case 2:
			return new SuggestionsFragment();
		case 3:
			return new BeertionaryFragment();
			
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}
	public class TasteGoodFragment extends Fragment {
		ProgressDialog loading;
		ListView goodBrews;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.tastegoodfragment, container, false);
	        //View theList = rootView.findViewById(R.id.favoriteBrews);
		      goodBrews = (ListView) rootView.findViewById(R.id.favoriteBrews);
		      String [] allGoodBeers = new String [] {"Rebel IPA", "Boston Lager", "Snake Dog IPA", "Summer Shandy", "Blue Moon"};
		      
		      
		      ArrayAdapter<String>beers = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,allGoodBeers);
		      
		      goodBrews.setAdapter(beers);
	        return rootView;
	    }
		

	}
	public class TasteBadFragment extends Fragment {
		
		ListView badBrews;
		
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.tastebadfragment, container, false);
	         
	        
		      badBrews = (ListView) rootView.findViewById(R.id.badBrews);
String [] allBadBeers = new String [] {"Third Shift", "90 Minute IPA", "Samuel Adams Cream Stout", "Harp", "Four Loco"};
		      
		      
		      ArrayAdapter<String>badBeers = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,allBadBeers);
		      
		      badBrews.setAdapter(badBeers);
	        return rootView;
	    }
	}
	
	public class SuggestionsFragment extends Fragment {
		
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.suggestionsfragment, container, false);
	         
	        return rootView;
	    }

	}
	public class BeertionaryFragment extends Fragment {
		
		ListView searchedBrews;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.beertionaryfragment, container, false);
		      searchedBrews = (ListView) rootView.findViewById(R.id.searchedBrews);
		      String [] allSearchedBrews = new String [] {"Boston Lager", "Rebel IPA", "Cream Stout", "Irish Red", "Summer Ale"};
		      		      
		      		      
		      		      ArrayAdapter<String>searches = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,allSearchedBrews);
		      		      
		      		      searchedBrews.setAdapter(searches);
	        return rootView;
	    }

	}

}
