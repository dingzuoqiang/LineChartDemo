package com.dzq.chartdemo.utils;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by dingzuoqiang on 2017/10/15.
 * Email: 530858106@qq.com
 */

public class CommonUtils {

    public static int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }

    public static int getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.bottom + bounds.height();
        return height;
    }
}
