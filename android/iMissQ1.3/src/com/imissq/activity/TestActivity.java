package com.imissq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.imissq.R;
import com.imissq.base.MyService;
import com.imissq.config.Commons;
import com.imissq.model.MisqDataBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TestActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.tvUpart)
    TextView tvUpart;
    @ViewInject(R.id.tvTpart)
    TextView tvTpart;
    @ViewInject(R.id.tvJpart)
    TextView tvJpart;
    @ViewInject(R.id.tvHpart)
    TextView tvHpart;
    @ViewInject(R.id.ivTpart)
    ImageView ivTpart;
    @ViewInject(R.id.ivUpart)
    ImageView ivUpart;
    @ViewInject(R.id.ivJpart)
    ImageView ivJpart;
    @ViewInject(R.id.ivHpart)
    ImageView ivHpart;

    public static enum Part {
        tPart, uPart, jPart, hPart;
    }

    private Part currentPart = Part.tPart;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.main_test);
        ViewUtils.inject(this);
        tvTpart.setOnClickListener(this);
        tvUpart.setOnClickListener(this);
        tvJpart.setOnClickListener(this);
        tvHpart.setOnClickListener(this);
        viewStatusChanged();
    }

    private void viewStatusChanged() {
        // TODO Auto-generated method stub
        tvTpart.setBackgroundColor(getResources().getColor(R.color.transparent));
        tvUpart.setBackgroundColor(getResources().getColor(R.color.transparent));
        tvJpart.setBackgroundColor(getResources().getColor(R.color.transparent));
        tvHpart.setBackgroundColor(getResources().getColor(R.color.transparent));

        ivTpart.setImageResource(R.drawable.test_hand_no_solid);
        ivUpart.setImageResource(R.drawable.test_hand_no_solid);
        ivJpart.setImageResource(R.drawable.test_hand_no_solid);
        ivHpart.setImageResource(R.drawable.test_hand_no_solid);
        if (currentPart == Part.tPart) {
            tvTpart.setBackgroundResource(R.drawable.part_press_shape);
            ivTpart.setImageResource(R.drawable.test_hand);
        } else if (currentPart == Part.uPart) {
            tvUpart.setBackgroundResource(R.drawable.part_press_shape);
            ivUpart.setImageResource(R.drawable.test_hand);
        } else if (currentPart == Part.jPart) {
            tvJpart.setBackgroundResource(R.drawable.part_press_shape);
            ivJpart.setImageResource(R.drawable.test_hand);
        } else if (currentPart == Part.hPart) {
            tvHpart.setBackgroundResource(R.drawable.part_press_shape);
            ivHpart.setImageResource(R.drawable.test_hand);
        }
        showLoadDialog();
        startService(new Intent(this, MyService.class));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tvTpart:
                currentPart = Part.tPart;
                break;
            case R.id.tvUpart:
                currentPart = Part.uPart;
                break;
            case R.id.tvJpart:
                currentPart = Part.jPart;
                break;
            case R.id.tvHpart:
                currentPart = Part.hPart;
                break;
        }
        viewStatusChanged();
    }

    @Override
    public void connectSuccess() {
        // TODO Auto-generated method stub
        super.connectSuccess();
    }

    @Override
    public void waterCallBack(int[] water, float base) {
        // TODO Auto-generated method stub
        super.waterCallBack(water, base);
        Intent intent = new Intent(this, TestResultActivity.class);
        Bundle b = new Bundle();
        MisqDataBean data = new MisqDataBean();
        data.setAge(water[0]);

        //hacked
        //因jackey返回油水值相反，再没有libmissqtunnel.jar文件说明得情况下，先互换oil and base的值
        //Eric 2015年08月26日11:09:53
        data.setOil(water[2]);
        data.setBase(water[1]);


//		data.setOil(water[1]);
//		data.setBase(water[2]);
        data.setBaseWeight(base);
        b.putSerializable("key", data);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void connectFailed() {
        // TODO Auto-generated method stub
        super.connectFailed();
        dismissLoadDialog();
        Commons.showToast(this, "连接检测器失败");
    }

}
