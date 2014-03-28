
package com.eccyan.bootcamp;

import java.util.Arrays;

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
    public static final String TITLE = "title";
    public static final String COLOR = "color";

    public TabFragment() {
    }

    public static TabFragment newInstance(String title, int color) {
        TabFragment tabFragment = new TabFragment();

        Bundle bundle = new Bundle();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        TextView textView = (TextView) inflater.inflate(R.layout.fragment_tab, container, false);

        Bundle arguments = getArguments();
        textView.setText(arguments.getString(TITLE));

        setBackGroundColorForTextView(textView, arguments.getInt(COLOR));

        textView.setOnTouchListener(this);

        return textView;
    }
    
    public void setBackgroundToDefault() {
        TextView tabView = (TextView)getView().findViewById(R.id.tab_text_view);
        tabView.setBackgroundResource(R.drawable.background_tab_shape);
        
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        
        tabView.setPadding(0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, displayMetrics), 0, 0);
        setBackGroundColorForTextView(tabView, getArguments().getInt(COLOR));
    }
    
    public void setBackgroundToFocused() {
        TextView tabView = (TextView)getView().findViewById(R.id.tab_text_view);
        tabView.setBackgroundResource(R.drawable.background_tab_shape_focused);
        setBackGroundColorForTextView(tabView, getArguments().getInt(COLOR));
    }
    
    public void setBackgroundToPressed() {
        TextView tabView = (TextView)getView().findViewById(R.id.tab_text_view);
        setBackGroundColorForTextView(tabView, getPressedColor(getArguments().getInt(COLOR)));
    }
    
    public void setBackgroundToNotPressed() {
        TextView tabView = (TextView)getView().findViewById(R.id.tab_text_view);
        setBackGroundColorForTextView(tabView, getArguments().getInt(COLOR));
    }
    
    public void setViewPagerItemToTabPosition() {
        ViewPager viewPager = (ViewPager)getActivity().findViewById(R.id.pager);
        String[] categoryNames = getResources().getStringArray(R.array.category_names);
        
        int position = Arrays.asList(categoryNames).indexOf(getArguments().getString(TITLE));
        viewPager.setCurrentItem(position);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        setBackgroundToNotPressed();
        
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setBackgroundToPressed();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            setViewPagerItemToTabPosition();
        }
        
        return true;
    }
    
}
