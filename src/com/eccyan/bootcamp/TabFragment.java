
package com.eccyan.bootcamp;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment
        implements OnTouchListener {
    public static final String POSITION = "position";
    public static final String TITLE = "title";
    public static final String COLOR = "color";
    
    private int position;
    private Tab tab;
    private TextView tabView;

    public TabFragment() {
    }

    public static TabFragment newInstance(int position, String title, int color) {
        TabFragment tabFragment = new TabFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putString(TITLE, title);
        bundle.putInt(COLOR, color);
        tabFragment.setArguments(bundle);

        return tabFragment;
    }
    
    private static void setBackGroundColorForTextView(TextView textView, int color) {
        LayerDrawable layerDrawable = (LayerDrawable) textView.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.getDrawable(1);
        gradientDrawable.setColor(color);
    }
    
    private static int getPressedColor(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        
        return Color.argb(alpha, (red + 0x1f) / 2, (green + 0x1f) / 2, (blue + 0x1f) / 2);
    }
    
    public Tab getTab() {
        return tab;
    }
    
    public int getPosition() {
        return position;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        position = getArguments().getInt(POSITION);
        tab = new Tab(getArguments().getString(TITLE), getArguments().getInt(COLOR));
        tabView = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        tabView = (TextView) inflater.inflate(R.layout.fragment_tab, container, false);

        tabView.setText(tab.getTitle());
        setBackGroundColorForTextView(tabView, tab.getColor());

        tabView.setOnTouchListener(this);

        return tabView;
    }
    
    public void setBackgroundToDefault() {
        tabView.setBackgroundResource(R.drawable.background_tab_shape);
        
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        
        tabView.setPadding(0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, displayMetrics), 0, 0);
        setBackGroundColorForTextView(tabView, tab.getColor());
    }
    
    public void setBackgroundResourceToFocused() {
        tabView.setBackgroundResource(R.drawable.background_tab_shape_focused);
    }
    
    public void setBackgroundColorToFocused() {
        setBackGroundColorForTextView(tabView, tab.getColor());
    }
    
    public void setBackgroundColorToPressed() {
        setBackGroundColorForTextView(tabView, getPressedColor(tab.getColor()));
    }
    
    public void setBackgroundColorToNotPressed() {
        setBackGroundColorForTextView(tabView, tab.getColor());
    }
    
    public void setViewPagerItemPosition(int position) {
        ViewPager viewPager = (ViewPager)getActivity().findViewById(R.id.pager);
        
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        setBackgroundColorToNotPressed();
        
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setBackgroundColorToPressed();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            setViewPagerItemPosition(position);
        }
        
        return false;
    }
    
}
