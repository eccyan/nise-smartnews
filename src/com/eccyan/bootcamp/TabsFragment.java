
package com.eccyan.bootcamp;

import java.util.Locale;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

import android.R.integer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
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

    public static String tagNameWithID(int id) {
        return String.format(Locale.JAPAN, "%d_%s", id, TAGNAME_TAB);
    }

    public TabsFragment() {
    }

    private ViewPager viewPager;
    private int currentPosition;
    private int lastScrollX;

    private View sectionLineView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_POSITION, 0);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sectionLineView = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        sectionLineView = view.findViewById(R.id.tabs_section_line);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POSITION, currentPosition);
    }

    public int getMinimumUnscrallablePosition() {
        return Math.max(currentPosition - 1, 0);
    }

    public int getMaximumUnscrallablePosition() {
        return Math.min(currentPosition + 1, viewPager.getAdapter().getCount() - 1);
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

        TabPagerAdapter adapter = (TabPagerAdapter) viewPager.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            addTab(i, adapter.getTabAt(i));
        }

        setSectionLineColor(adapter.getTabAt(currentPosition).getColor());

        this.viewPager = viewPager;

        removeAllTabs();
        this.viewPager.getAdapter().notifyDataSetChanged();

    }
    
    public void setSectionLineColor(int color) {
        LayerDrawable layerDrawable = (LayerDrawable) sectionLineView.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.getDrawable(0);
        gradientDrawable.setColor(color);     
    }

    public void addTab(int position, Tab tab) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_tab_container,
                TabFragment.newInstance(position, tab.getTitle(), tab.getColor()),
                tagNameWithID(position));

        fragmentTransaction.commit();
    }

    private void removeTabFragmentAt(int position) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(getTabFragmentAt(position));

        fragmentTransaction.commit();
    }

    private TabFragment getTabFragmentAt(int position) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        return (TabFragment) fragmentManager
                .findFragmentByTag(tagNameWithID(position));
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
            if (getTabFragmentAt(i) != null) {
                removeTabFragmentAt(i);
            }
        }
    }

    public int getScrollOffset() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int tabWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80,
                displayMetrics);
        return (displayMetrics.widthPixels - tabWidth) / 2;
    }

    private void scrollTo(int position, int offset) {
        TabPagerAdapter adapter = (TabPagerAdapter) viewPager.getAdapter();

        if (adapter.getCount() == 0) {
            return;
        }

        LinearLayout tabContainer = (LinearLayout) getActivity()
                .findViewById(R.id.fragment_tab_container);
        View targetTab = tabContainer.getChildAt(position);
        int newScrollX = targetTab.getLeft() + offset;
        if (position > 0 || offset > 0) {
            newScrollX -= getScrollOffset();
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            HorizontalScrollView tabScrollView = (HorizontalScrollView) getActivity()
                    .findViewById(R.id.tab_scroll_view);

            AnimatorProxy.wrap(targetTab).setPivotY(-targetTab.getHeight() / 2);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(targetTab, "scaleY", 1, 40 / 36),
                    ObjectAnimator.ofInt(tabScrollView, "scrollX", newScrollX));
            animatorSet.setDuration(240).start();
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
