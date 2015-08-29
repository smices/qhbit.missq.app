package com.imissq.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.imissq.R;

public class CommonLoadDialog extends Dialog {

    private Context context;
    private TextView tvMsg;

    public CommonLoadDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dailog_loading_layout);
        tvMsg = (TextView) findViewById(R.id.emptyText);
    }

    public void setMsg(String msg) {
        if (tvMsg != null) {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(msg);
        }
    }

}