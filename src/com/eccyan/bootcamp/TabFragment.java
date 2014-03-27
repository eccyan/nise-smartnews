
package com.eccyan.bootcamp;

import java.util.Arrays;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment
        implements OnClickListener {
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
    
    public static void setBackGroundColorForTextView(TextView textView, int color) {
        LayerDrawable layerDrawable = (LayerDrawable) textView.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.getDrawable(1);
        gradientDrawable.setColor(color);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        TextView textView = (TextView) inflater.inflate(R.layout.fragment_tab, container, false);

        Bundle arguments = getArguments();
        textView.setText(arguments.getString(TITLE));

        setBackGroundColorForTextView(textView, arguments.getInt(COLOR));

        textView.setOnClickListener(this);

        return textView;
    }

    @Override
    public void onClick(View view) {
        ViewPager viewPager = (ViewPager)getActivity().findViewById(R.id.pager);
        String[] categoryNames = getResources().getStringArray(R.array.category_names);
        
        int position = Arrays.asList(categoryNames).indexOf(getArguments().getString(TITLE));
        viewPager.setCurrentItem(position);
        
        view.setBackgroundResource(R.drawable.background_tab_shape_focused);
        setBackGroundColorForTextView((TextView)view, getArguments().getInt(COLOR));
    }
}
