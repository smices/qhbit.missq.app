package com.imissq.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ambytw.android.missq.lib.MissQLib;
import com.imissq.R;
import com.imissq.activity.BaseActivity;
import com.imissq.activity.TestResultActivity;
import com.imissq.base.MyService;
import com.imissq.config.Commons;
import com.imissq.model.MisqDataBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TPartTestFragment extends BaseFragment implements View.OnClickListener {
    private BaseActivity act;

    @ViewInject(R.id.btn_t_one)
    Button btnOne;
    @ViewInject(R.id.btn_t_two)
    Button btnTwo;
    @ViewInject(R.id.btn_t_three)
    Button btnThree;

    private int currentIndex = 0;

    static final int ONE = 1;
    static final int TWO = 2;
    static final int THREE = 3;

    public static TPartTestFragment getInstance() {
        return new TPartTestFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        act = (BaseActivity) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.main_t_test, null);
        ViewUtils.inject(this, view);

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_t_one:
                startTest(ONE);
                break;
            case R.id.btn_t_two:
                startTest(TWO);
                break;
            case R.id.btn_t_three:
                startTest(THREE);
                break;
        }
    }

    private void startTest(int index) {
        currentIndex = index;
        MyService.connectClient();
        act.showLoadDialog();
    }

    @Override
    public void connectFailed() {
        // TODO Auto-generated method stub
        super.connectFailed();
        Commons.showToast(act, "连接失败");
        act.dismissLoadDialog();
    }

    @Override
    public void connectSuccess() {
        // TODO Auto-generated method stub
        super.connectSuccess();
        act.dismissLoadDialog();
    }

    @Override
    public void waterCallBack(int[] water, float base) {
        // TODO Auto-generated method stub
        super.waterCallBack(water, base);
        act.dismissLoadDialog();
        if (currentIndex == THREE) {
            Intent intent = new Intent(act, TestResultActivity.class);
            Bundle b = new Bundle();
            MisqDataBean data = new MisqDataBean();
// collection parameters begin
            data.setWater(water[0]);
            data.setOil(water[1]);
            data.setElastic(water[2]);
            data.setBaseWeight(base);
            data.setAge(water[3]);
            data.setSmooth(water[4]);
// collection parameters end
            b.putSerializable("key", data);
            intent.putExtras(b);
            startActivity(intent);
            MyService.uploadTestData(water, base);
        } else {
            Commons.showToast(act, "请测试下一个部位");
        }
    }

    @Override
    public MissQLib getMissQLib() {
        // TODO Auto-generated method stub
        return super.getMissQLib();
    }

}
