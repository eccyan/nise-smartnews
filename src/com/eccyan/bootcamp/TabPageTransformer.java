
package com.eccyan.bootcamp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

;

;

public class TabPageTransformer implements PageTransformer {
    private static float MIN_SCALE = 0.75f;

    public TabPageTransformer() {
        super();
    }

    @Override
    public void transformPage(View page, float position) {
        ScrollView scrollView = (ScrollView) page.findViewById(R.id.card_scroll_view);
        LinearLayout cardContainer = (LinearLayout)scrollView.findViewById(R.id.card_container);
        /*
        Paint mShadow = new Paint();
        // radius=10, y-offset=2, color=black
        mShadow.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000);
        */
        GradientDrawable drawable = (GradientDrawable)cardContainer.getBackground();
        //drawable.setColor(Color.BLACK);
        
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
