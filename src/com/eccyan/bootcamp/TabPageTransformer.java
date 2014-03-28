
package com.eccyan.bootcamp;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;


public class TabPageTransformer implements PageTransformer {

    public TabPageTransformer() {
        super();
    }

    @Override
    public void transformPage(View page, float position) {
        FrameLayout frameLayout = (FrameLayout) page.findViewById(R.id.card_frame_layout);
        View cardPaperView = frameLayout.findViewById(R.id.card_paper_view);
        View cardShadowView = frameLayout.findViewById(R.id.card_shadow_view);

        int pageWidth = frameLayout.getWidth();
        int cardPaperWidth = cardPaperView.getWidth();
        int cardShadowWidth = cardShadowView.getWidth();

        ViewHelper.setRotation(cardPaperView, 2f);
        ViewHelper.setRotation(cardShadowView, 2f);
        
        ViewHelper.setAlpha(cardPaperView, 1);
        ViewHelper.setAlpha(cardShadowView, 1);
        
        if (position < -1) {
            ViewHelper.setAlpha(cardPaperView, 0);
            ViewHelper.setAlpha(cardShadowView, 0);
        } else if (position <= 0) {
            ViewHelper.setTranslationX(frameLayout, pageWidth * -position);
            ViewHelper.setTranslationX(cardPaperView, cardPaperWidth * position);
            ViewHelper.setPivotX(cardPaperView, cardPaperWidth);
            ViewHelper.setScaleX(cardPaperView, -position);
        } else if (position <= 1) {
            ViewHelper.setTranslationX(frameLayout, pageWidth * -position);
            ViewHelper.setTranslationX(cardShadowView, cardShadowWidth * position);
            ViewHelper.setAlpha(cardPaperView, 0);
        } else {
            ViewHelper.setAlpha(cardPaperView, 1);
            ViewHelper.setAlpha(cardShadowView, 1);
        }
        
    }

}
