package com.eccyan.bootcamp;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.widget.ScrollView;
import com.nineoldandroids.*;

public class TabPageTransformer implements PageTransformer {
    
    public TabPageTransformer() {
        super();
    }

    @Override
    public void transformPage(View page, float position) {
        /*
        ScrollView scrollView = (ScrollView)page.findViewById(R.id.card_scroll_view);
        Drawable background = (Drawable)scrollView.getBackground();
        
        if (position >= 0) {
            background.setAlpha((int)(0xff * (1 - position)));
        }
        */
    }

}
