package com.eccyan.bootcamp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private Tab[] tabs;

    public TabPagerAdapter(FragmentManager fragmentManager, Tab[] tabs) {
        super(fragmentManager);
        
        this.tabs = tabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position].getTitle();
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public Fragment getItem(int position) {
        return SuperAwesomeCardFragment.newInstance(position);
    }
    
    public Tab getTab(int position) {
        return tabs[position];   
    }

}
