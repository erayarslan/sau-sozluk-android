package com.guguluk.sausozluk.util;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float diffX = e2.getX() - e1.getX();
                if (diffX > 200) {
                    onSwipeRight();
                } else if(diffX < -200) {
                    onSwipeLeft();
                }
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public void onSwipeRight() {
        //
    }

    public void onSwipeLeft() {
        //
    }
}
