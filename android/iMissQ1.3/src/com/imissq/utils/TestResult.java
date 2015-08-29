package com.imissq.utils;

import android.content.Context;
import android.widget.TextView;

import com.imissq.R;

public class TestResult {

    public static final String PERFECT = "婴儿般的Q弹美肌";
    public static final String EXCELLENCE = "水漾柔润的肌肤";
    public static final String GOOD = "水嫩饱满的肌肤";
    public static final String WARNING = "还不错的肌肤";
    public static final String BAD = "肌肤需要有更多的呵护";

    public static String getWaterTestReslut(int water) {
        String result = "";
        if (water >= 80) {
            result = PERFECT;
        } else if (water >= 65) {
            result = EXCELLENCE;
        } else if (water >= 50) {
            result = GOOD;
        } else if (water >= 30) {
            result = WARNING;
        } else {
            result = BAD;
        }
        return result;
    }

    public static void setTvTestData(TextView tvLeft, TextView tvRight, TextView tv, int water) {
        Context ctx = tv.getContext();
        String text = "";
        int color = ctx.getResources().getColor(R.color.black_3);
        if (water >= 80) {
            text = PERFECT + "(完美)";
            color = ctx.getResources().getColor(R.color.bluegreen);
        } else if (water >= 65) {
            text = EXCELLENCE + "(还不错)";
            color = ctx.getResources().getColor(R.color.orange);
        } else if (water >= 50) {
            text = EXCELLENCE + "(请保持)";
            color = ctx.getResources().getColor(R.color.orange);
        } else {
            text = EXCELLENCE + "(较差)";
            color = ctx.getResources().getColor(R.color.red);
        }

        tv.setText(text);
        tv.setTextColor(color);
        tvLeft.setTextColor(color);
        tvRight.setBackgroundColor(color);
    }

    public static void setTvTodayData(TextView tv, int water) {
        Context ctx = tv.getContext();
        String text = "";
        int color = ctx.getResources().getColor(R.color.black_3);
        if (water >= 80) {
            text = PERFECT + "";
            color = ctx.getResources().getColor(R.color.bluegreen);
        } else if (water >= 65) {
            text = EXCELLENCE + "";
            color = ctx.getResources().getColor(R.color.orange);
        } else if (water >= 50) {
            text = EXCELLENCE + "";
            color = ctx.getResources().getColor(R.color.orange);
        } else {
            text = EXCELLENCE + "";
            color = ctx.getResources().getColor(R.color.red);
        }
        text = "(上一次)" + text;
        tv.setText(text);
        tv.setTextColor(color);
    }

}
