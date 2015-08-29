package com.imissq.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.imissq.R;
import com.imissq.base.AppMaterial;


/**
 * 标题栏 标 题栏分为 三个区域：左中右。 左右宽度自由，用于放置button。 中间填满空白。 三个容器内的组件都可单独设置和获取。
 */
public class NavigationBar extends RelativeLayout {

    private ViewGroup mFlNaviLeft;
    private ViewGroup mFlNaviMid;
    private ViewGroup mFlNaviRight;

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFlNaviLeft = (ViewGroup) findViewById(R.id.fl_navi_left);
        mFlNaviMid = (ViewGroup) findViewById(R.id.fl_navi_mid);
        mFlNaviRight = (ViewGroup) findViewById(R.id.fl_navi_right);

        // 背景颜色
        setBackgroundColor(AppMaterial.NUMBER_1_INT);
    }

    /**
     * 设置中间视图
     *
     * @param view
     */
    public void setMiddleView(View view) {
        mFlNaviMid.removeAllViews();
        mFlNaviMid.addView(view);
    }

    public View getMiddleView() {
        return (mFlNaviMid.getChildCount() > 0) ? mFlNaviMid.getChildAt(0) : null;
    }

    /**
     * 设置左侧视图
     *
     * @param view
     */
    public void setLeftView(View view) {
        for (int i = 0; i < mFlNaviLeft.getChildCount(); i++) {
            View childView = mFlNaviLeft.getChildAt(i);
            childView.clearAnimation();
            mFlNaviLeft.removeViewAt(i);
        }
        if (view != null) {
            mFlNaviLeft.addView(view);
        }
    }

    public View getLeftView() {
        return (mFlNaviLeft.getChildCount() > 0) ? mFlNaviLeft.getChildAt(0) : null;
    }

    /**
     * 设置右侧视图
     *
     * @param view
     */
    public void setRightView(View view) {
        for (int i = 0; i < mFlNaviRight.getChildCount(); i++) {
            View childView = mFlNaviRight.getChildAt(i);
            childView.clearAnimation();
            mFlNaviRight.removeViewAt(i);
        }
        if (view != null) {
            mFlNaviRight.addView(view);
        }
    }

    /**
     * 设置右侧视图
     *
     * @param view
     */
    public void setRightView(View view, FrameLayout.LayoutParams layoutParams) {
        for (int i = 0; i < mFlNaviRight.getChildCount(); i++) {
            View childView = mFlNaviRight.getChildAt(i);
            childView.clearAnimation();
            mFlNaviRight.removeViewAt(i);
        }
        if (view != null) {
            mFlNaviRight.addView(view, layoutParams);
        }
    }

    public View getRightView() {
        return (mFlNaviRight.getChildCount() > 0) ? mFlNaviRight.getChildAt(0) : null;
    }

    public static interface IProvideNavigationBar {
        NavigationBar getNavigationBar();

        void setupNavigationBar(int resId);

        // 添加顶部标题栏标题
        public void setTitle(int titleId);

        public void setTitle(CharSequence title);

        public void setTitleWithDigital(CharSequence title, CharSequence num);

        // 右边按钮
        public View addRightButtonText(String text, OnClickListener listener);

        public View addRightButtonText(int resId, OnClickListener listener);

        public View addRightButtonImage(int resid, OnClickListener listener);

        // 左边返回
        public View addLeftButtonText(String text, OnClickListener listener);

        public View addLeftButtonText(int resId, OnClickListener listener);

        public View addLeftButtonImage(int resid, OnClickListener listener);

        public View addBackBtn(final OnClickListener listener);

        // 添加顶部标题栏标题
        public void setMiddleView(View title);

        public void setLeftView(View left);

        public void setRightView(View right);
    }
}
