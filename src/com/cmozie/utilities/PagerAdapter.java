package com.cmozie.utilities;



import com.cmozie.ontap.R;
import com.cmozie.utilities.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.tastegoodfragment, container, false);
	         
	        return rootView;
	    }
		

	}
	public class TasteBadFragment extends Fragment {
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.tastebadfragment, container, false);
	         
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
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.beertionaryfragment, container, false);
	         
	        return rootView;
	    }

	}

}
