package com.example.vovik.p_touch;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import static android.R.attr.x;

public class MainActivity extends Activity implements OnTouchListener {
    int width;
    StringBuilder sb = new StringBuilder();
    TextView tv;
    int upPI = 0;
    int downPI = 0;
    int[] a = new int[2];
    boolean inTouch = false;
    String result = "";




    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        tv = new TextView(this);
        tv.setTextSize(30);
        tv.setOnTouchListener(this);
        setContentView(tv);
        Point size = new Point();
        WindowManager w = getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            w.getDefaultDisplay().getSize(size);

            width = size.x;
         }
         else
            {
            Display d = w.getDefaultDisplay();
            width = d.getWidth();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // событие
        int actionMask = event.getActionMasked();
        // индекс касания
        int pointerIndex = event.getActionIndex();
        // число касаний
        int pointerCount = event.getPointerCount();

        switch (actionMask) {
            case MotionEvent.ACTION_DOWN: // первое касание
                inTouch = true;
            case MotionEvent.ACTION_POINTER_DOWN: {// последующие касания
                downPI = pointerIndex;
                for (int i = 0; i < 2; i++) {
                    a[i] = 0;
                    if (i < pointerCount) {
                        if (event.getX(i) < width / 2) {
                            a[i] = 1;
                        } else {
                            a[i] = 2;
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP: {// прерывание последнего касания
                inTouch = false;

                for (int i = 0; i < 2; i++) {
                    a[i] = 0;
                }
            }
            case MotionEvent.ACTION_POINTER_UP: {// прерывания касаний
                upPI = pointerIndex;

                for (int i = 0; i < 2; i++) {
                    a[i] = 0;
                    if (i < pointerCount) {
                        if (event.getX(i) < width / 2) {
                            a[i] = 1;
                        } else {
                            a[i] = 2;
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {// движение
                for (int i = 0; i < 2; i++) {
                    a[i] = 0;
                    if (i < pointerCount) {
                        if (event.getX(i) < width / 2) {
                            a[i] = 1;
                        } else {
                            a[i] = 2;
                        }
                    }
                }
                break;
            }
        }
        sb.setLength(0);
        for (int i = 0 ; i < 2 ; i++)
        {
            if (a[i] == 1)
            {
                sb.append("left");
            }
            if (a[i] == 2)
            {
                sb.append("right");
            }
        }
        tv.setText(sb.toString());
        return true;
    }
}