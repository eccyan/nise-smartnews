
package com.eccyan.bootcamp;

import java.util.zip.GZIPInputStream;

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
    final private static String TAG_TAB = "tab";
    final private static int SCROLL_OFFSET = 52;

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

        removeAllTabs();
        TabPagerAdapter adapter = (TabPagerAdapter) viewPager.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            addTab(adapter.getTab(i));
        }

        this.viewPager = viewPager;
    }

    public void addTab(Tab tab) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_tab_container,
                TabFragment.newInstance(tab.getTitle(), tab.getColor()), TAG_TAB);

        fragmentTransaction.commit();
    }

    public void removeAllTabs() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Fragment fragment = null;
        while ((fragment = fragmentManager.findFragmentByTag(TAG_TAB)) != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    public int getScrollOffset() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SCROLL_OFFSET,
                displayMetrics);
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
            HorizontalScrollView tabScrollView = (HorizontalScrollView)getActivity()
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
    }
}
