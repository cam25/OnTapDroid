package com.cmozie.ontap;

import com.cmozie.utilities.PagerAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class TasteGood extends FragmentActivity implements ActionBar.TabListener {
	
	 private ActionBar actionBar;
	 private ViewPager viewPager;
	 private PagerAdapter pAdapter;
	 private String[] tabs = {"Taste Good", "Taste Bad", "Suggestions", "Beertionary"};
	 
	 //private ActionBar actionBar;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tastegood);
        
        viewPager = (ViewPager) findViewById(R.id.theViewPager);
        actionBar = getActionBar();
        pAdapter = new PagerAdapter(getSupportFragmentManager());
        
        viewPager.setAdapter(pAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        for (String tab_name : tabs) {
        	actionBar.addTab(actionBar.newTab().setText(tab_name)
        			.setTabListener(this));
        	
        }
        
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
	

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
