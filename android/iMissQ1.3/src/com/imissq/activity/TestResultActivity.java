package com.imissq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.imissq.R;
import com.imissq.config.Commons;
import com.imissq.model.MisqDataBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TestResultActivity extends BaseActivity implements OnClickListener {

    private MisqDataBean data;

    @ViewInject(R.id.tvOil)
    TextView tvOil;
    @ViewInject(R.id.tvWater)
    TextView tvWater;

    @ViewInject(R.id.tvQ)
    TextView tvQ;


    @ViewInject(R.id.tvTotal)
    TextView tvTotal;


    @ViewInject(R.id.btn_again)
    Button btnAgain;
    @ViewInject(R.id.btn_history)
    Button btnHistory;


    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.act_test_result);
        ViewUtils.inject(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            data = (MisqDataBean) b.getSerializable("key");
        }
        if (data == null) {
            Commons.showToast(this, "数据为空");
            return;
        }

        tvOil.setText("肌肤油值： " + data.getOil() + "");
        tvWater.setText("肌肤含水量： " + data.getBase() + "");

        //hacked：弹性这块不知道具体是哪个数值，无法绑定，先用age字段
        tvQ.setText("肌膚活化度： " + data.getAge() + "");

        tvTotal.setText(data.getOil() + data.getAge() + "");
        btnAgain.setOnClickListener(this);    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_again:
                finish();
                return;
            case R.id.btn_history:
                Intent intent = new Intent(TestResultActivity.this, RecordActivity.class);
                startActivity(intent);
                break;
        }
    }

}
