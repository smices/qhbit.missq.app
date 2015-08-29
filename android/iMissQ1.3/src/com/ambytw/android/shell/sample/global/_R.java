package com.ambytw.android.shell.sample.global;

import android.view.ViewGroup;

public class _R {

    public static void resize(ViewGroup.LayoutParams p, int w, int h) {

        if (w > 0) {
            p.width = w;
        }

        if (h > 0) {
            p.height = h;
        }
    }

}
