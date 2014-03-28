
package com.eccyan.bootcamp;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.widget.ScrollView;
import com.nineoldandroids.view.ViewHelper;;

;

public class TabPageTransformer implements PageTransformer {
    private static float MIN_SCALE = 0.75f;

    public TabPageTransformer() {
        super();
    }

    @Override
    public void transformPage(View page, float position) {
        ScrollView scrollView = (ScrollView) page.findViewById(R.id.card_scroll_view);
        int pageWidth = scrollView.getWidth();

        if (position < -1) {
            ViewHelper.setAlpha(scrollView, 0);
        } else if (position <= 0) {
            ViewHelper.setTranslationX(scrollView, pageWidth * -position);
        } else if (position <= 1) {
            ViewHelper.setTranslationX(scrollView, pageWidth * -position);
        } else { 
            ViewHelper.setAlpha(scrollView, 0);
        }
    }

}
