
package com.eccyan.bootcamp;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.nineoldandroids.view.ViewHelper;

;

;

public class TabPageTransformer implements PageTransformer {

    public TabPageTransformer() {
        super();
    }

    @Override
    public void transformPage(View page, float position) {
        ScrollView scrollView = (ScrollView) page.findViewById(R.id.card_scroll_view);
        /*
         * Paint mShadow = new Paint(); // radius=10, y-offset=2, color=black
         * mShadow.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000);
         */

        int pageWidth = scrollView.getWidth();
        if (position < -1) {
        } else if (position <= 0) {
            ViewHelper.setTranslationX(scrollView, pageWidth * -position);
        } else if (position <= 1) {
            ViewHelper.setTranslationX(scrollView, pageWidth * -position);
        } else {
        }
    }

}
