
package com.eccyan.bootcamp;

import java.util.Locale;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class TabsFragment extends Fragment
        implements OnPageChangeListener {
    final private static String CURRENT_POSITION = "current position";
    final private static String TAGNAME_TAB = "tab";
    final private static int SCROLL_OFFSET = 52;

    public static String tagNameWithID(int id) {
        return String.format(Locale.JAPAN, "%d_%s", id, TAGNAME_TAB);
    }

    public TabsFragment() {
    }

    private ViewPager viewPager;
    private int currentPosition;
    private int lastScrollX;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_POSITION, 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POSITION, currentPosition);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        viewPager.setOnPageChangeListener(this);
        viewPager.setPageTransformer(true, new TabPageTransformer());


        removeAllTabs();
        TabPagerAdapter adapter = (TabPagerAdapter) viewPager.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            addTab(i, adapter.getTab(i));
        }

        viewPager.getAdapter().notifyDataSetChanged();

        this.viewPager = viewPager;
        
        setSectionLineColor(adapter.getTab(currentPosition).getColor());
    }

    public void addTab(int position, Tab tab) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_tab_container,
                TabFragment.newInstance(position, tab.getTitle(), tab.getColor()), tagNameWithID(position));

        fragmentTransaction.commit();
    }

    public void removeAllTabs() {
        if (viewPager == null) {
            return;
        }

        TabPagerAdapter adapter = (TabPagerAdapter) viewPager.getAdapter();

        LinearLayout tabContainer = (LinearLayout) getActivity()
                .findViewById(R.id.fragment_tab_container);

        for (int i = 0; i < adapter.getCount(); i++) {
            adapter.destroyItem(tabContainer, i, this);
        }
    }

    public int getScrollOffset() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SCROLL_OFFSET,
                displayMetrics);
    }
    
    private void setSectionLineColor(int color) {
        View sectionLine = getView().findViewById(R.id.tabs_section_line);
        sectionLine.setBackgroundColor(color);
    }

    private void scrollTo(int position, int offset) {
        TabPagerAdapter adapter = (TabPagerAdapter) viewPager.getAdapter();

        if (adapter.getCount() == 0) {
            return;
        }

        LinearLayout tabContainer = (LinearLayout) getActivity()
                .findViewById(R.id.fragment_tab_container);
        int newScrollX = tabContainer.getChildAt(position).getLeft() + offset;
        if (position > 0 || offset > 0) {
            newScrollX -= getScrollOffset();
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            HorizontalScrollView tabScrollView = (HorizontalScrollView) getActivity()
                    .findViewById(R.id.tab_scroll_view);

            tabScrollView.scrollTo(newScrollX, 0);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPosition = position;

        LinearLayout tabContainer = (LinearLayout) getActivity()
                .findViewById(R.id.fragment_tab_container);
        int childWidth = tabContainer.getChildAt(position).getWidth();
        scrollTo(position, (int) (positionOffset * childWidth));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            scrollTo(viewPager.getCurrentItem(), 0);
        }
    }

    @Override
    public void onPageSelected(int position) {
        TabPagerAdapter adapter = (TabPagerAdapter) viewPager.getAdapter();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        for (int i = 0; i < adapter.getCount(); i++) {
            TabFragment tabFragment = (TabFragment) fragmentManager
                    .findFragmentByTag(TabsFragment.tagNameWithID(i));
            tabFragment.setBackgroundToDefault();
        }
        
        TabFragment tabFragment = (TabFragment) fragmentManager
                .findFragmentByTag(TabsFragment.tagNameWithID(position));
        tabFragment.setBackgroundResourceToFocused();
        tabFragment.setBackgroundColorToFocused();
        
        setSectionLineColor(tabFragment.getTab().getColor());
    }
}
