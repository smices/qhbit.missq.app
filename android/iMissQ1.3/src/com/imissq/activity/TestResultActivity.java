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
    private double skinCombinedAverage;

    @ViewInject(R.id.tvOil)
    TextView tvOil;

    @ViewInject(R.id.tvElastic)
    TextView tvElastic;

    @ViewInject(R.id.tvWater)
    TextView tvWater;

    @ViewInject(R.id.tvSmooth)
    TextView tvSmooth;

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

        tvOil.setText("肌肤油量值： " + data.getOil() + "");
        tvSmooth.setText("肌肤嫩滑度： " + data.getSmooth() + "");
        tvWater.setText("肌肤含水量： " + data.getWater() + "");
        //hacked：弹性这块不知道具体是哪个数值，无法绑定，先用age字段
        tvElastic.setText("肌肤弹性值：" + data.getElastic() + "");

        ///??????????????
        //tvTotal.setText(data.getOil() + data.getAge() + "");

        //计算综合值
        java.text.DecimalFormat   df=new java.text.DecimalFormat("##.##");
        skinCombinedAverage =
                (
                    Math.round(
                        Math.round(data.getOil() / (65 - 20)) +
                        Math.round(data.getWater() / (65-20)) +
                        Math.round(data.getElastic() / (65-20))+
                        Math.round(data.getSmooth() / (65-20))
                ) / 4 ) * 100;

        tvTotal.setText(df.format(skinCombinedAverage + ""));

        btnAgain.setOnClickListener(this);
    }


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
