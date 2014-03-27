package com.eccyan.bootcamp;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {  
    public static final String TITLE = "title";
    public static final String COLOR = "color";

    public TabFragment() { }
    
    public static TabFragment newInstance(String title, int color) {
        TabFragment tabFragment = new TabFragment();
        
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putInt(COLOR, color);
        tabFragment.setArguments(bundle);
        
        return tabFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        TextView textView = (TextView)inflater.inflate(R.layout.fragment_tab, container, false);
        
        Bundle arguments = getArguments();
        textView.setText(arguments.getString(TITLE));
        
        // Set background colors.
        StateListDrawable backgroundTab = (StateListDrawable) textView.getBackground();
        LayerDrawable layerDrawable = (LayerDrawable) backgroundTab.getCurrent();
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.getDrawable(1);
        gradientDrawable.setColor(arguments.getInt(COLOR));   
        
        return textView;
    }    
}
