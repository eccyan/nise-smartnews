
package com.eccyan.bootcamp;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   
        FragmentManager fragmentManager = getSupportFragmentManager();
        TabsFragment tabs = (TabsFragment) fragmentManager.findFragmentById(R.id.tabs);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        TabPagerAdapter adapter = new TabPagerAdapter(fragmentManager, getTabs());

        pager.setAdapter(adapter);
        
        tabs.setViewPager(pager);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private Tab[] getTabs() {
        TypedArray categoryColors = getResources().obtainTypedArray(R.array.category_colors);
        String[] categoryNames = getResources().getStringArray(R.array.category_names);

        Tab[] tabs = new Tab[categoryNames.length];
        for (int i = 0; i < tabs.length; i++) {
            tabs[i] = new Tab(categoryNames[i],
                    categoryColors.getColor(i % categoryColors.length(), 0));
        }

        categoryColors.recycle();
        return tabs;
    }
}
