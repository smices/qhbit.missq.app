package com.imissq.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.imissq.R;
import com.imissq.base.BaseApplication;
import com.imissq.config.Commons;
import com.imissq.views.PageIndicatorView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class FirstPageActivity extends BaseActivity implements OnPageChangeListener {

    @ViewInject(R.id.g_banner)
    ViewPager pages;
    @ViewInject(R.id.pagePoint)
    PageIndicatorView pagePoint;

    private int[] ivIds = {R.drawable.w2, R.drawable.w3, R.drawable.w4,
            R.drawable.w5, R.drawable.w6};

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.act_first_page);

        ViewUtils.inject(this);

        pagePoint.setTotalPage(ivIds.length);
        pagePoint.setCurrentPage(0);

        MyAdapter adapter = new MyAdapter();
        pages.setAdapter(adapter);

        pages.setOnPageChangeListener(this);
        pages.setCurrentItem(0);
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ivIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            if (object instanceof View) {
                ((ViewPager) container).removeView((View) object);
            }

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            View view = LayoutInflater.from(FirstPageActivity.this).inflate(
                    R.layout.view_page_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_view);
            iv.setBackgroundResource(ivIds[position]);
            Button btn = (Button) view.findViewById(R.id.btn_enter);
            if (position != ivIds.length - 1) {
                btn.setVisibility(View.GONE);
            } else {
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Commons.startHomeAct(FirstPageActivity.this);
                        BaseApplication.getInstance().getSettings().FIRST_START.setValue(false);
                    }
                });
            }
            ((ViewPager) container).addView(view, 0);
            return view;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        pagePoint.setCurrentPage(arg0);
    }

}
