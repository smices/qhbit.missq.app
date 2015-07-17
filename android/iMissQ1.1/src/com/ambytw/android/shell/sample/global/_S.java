package com.ambytw.android.shell.sample.global;

public class _S {

    private static int w = 0;

    private static int h = 0;

    private static int w1o2 = 0;

    private static int h1o4 = 0;

    private static int w1o30 = 0;

    private static float f = 0;

    private static float f1p5 = 0;

    public static float f1p5() {
        return f1p5;
    }

    public static int w() {
        return w;
    }

    public static int h() {
        return h;
    }

    public static int w1o2() {
        return w1o2;
    }

    public static int h1o4() {
        return h1o4;
    }

    public static int w1o30() {
        return w1o30;
    }

    public static void setWidth(int width) {
        w = width;
        w1o2 = w / 2;
        w1o30 = w / 30;
    }

    public static void setHeight(int height) {
        h = height;
        h1o4 = h / 4;
    }

    public static void setF(float s) {
        f = s;
        f1p5 = f * 3 / 2;
    }
}
