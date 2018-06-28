package shree.pheramor.registration;

import android.support.v4.view.ViewPager;
import android.view.View;

class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    public static final float MAX_ROTATION = 90.0f;
    public void transformPage(View page, float position) {
        if (position <= 1.0 && position >= -1.0) {
            if (position >0) {

                final float rotation = position * MAX_ROTATION;
                page.setPivotX(0);
                page.setRotationY(rotation);
            } else if (position<0) {

                final float rotation = position * MAX_ROTATION;
                page.setPivotX(page.getWidth());
                page.setRotationY(rotation);
            } else {

                page.setRotationY(0);
            }
        } else {

            page.setRotationY(0);
        }
    }
}
