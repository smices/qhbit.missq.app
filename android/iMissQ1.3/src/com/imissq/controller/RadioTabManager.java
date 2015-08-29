package com.imissq.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.imissq.views.TagView;

import java.util.HashMap;

public class RadioTabManager implements OnCheckedChangeListener {
    private final FragmentManager mFragmentManager;
    private final int mContainerId;
    private final Context mContext;
    private final RadioGroup mRadioGroup;
    private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
    TabInfo mLastTab;
    private final BuildViewFactory mTabViewFactory;
    private final boolean isShowHide;

    public View addTab(final String tag, Class<?> clss, Bundle args) {
        // 构建tab
        View radioButton = mTabViewFactory.tabView(tag, mRadioGroup);
        radioButton.setTag(tag);
        // 添加到radioGroup�?
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0,
                LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        mRadioGroup.addView(radioButton, params);
        radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setCurrFragmentByTag(tag);
            }
        });

        TabInfo info = new TabInfo(tag, clss, args);
        info.fragment = mFragmentManager.findFragmentByTag(tag);
        if (isShowHide) {
            if (info.fragment != null) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.hide(info.fragment);
                ft.commitAllowingStateLoss();
            }
        } else {
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.detach(info.fragment);
                ft.commitAllowingStateLoss();
            }
        }
        mTabs.put(tag, info);
        return radioButton;
    }

    // 对外界传递tab发生变化
    public interface TabChangeListener {
        public void onChange(TabInfo mLastTab);
    }

    TabChangeListener tabListener = null;

    public void setTabChangeListener(TabChangeListener listener) {
        this.tabListener = listener;
    }

    public RadioTabManager(Context context, FragmentManager fragmentManager, RadioGroup radioGroup, int containerId,
                           BuildViewFactory tabViewFactory, boolean isShowHide) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mRadioGroup = radioGroup;
        mContainerId = containerId;
        mTabViewFactory = tabViewFactory;
        mRadioGroup.setOnCheckedChangeListener(this);
        this.isShowHide = isShowHide;
    }

    public RadioTabManager(Context context, FragmentManager fragmentManager, RadioGroup radioGroup, int containerId,
                           BuildViewFactory tabViewFactory) {
        this(context, fragmentManager, radioGroup, containerId, tabViewFactory, false);
    }

    public void setCurrFragmentByTag(String tag) {
        //TagView radioButton = (TagView) mRadioGroup.findViewWithTag(tag);
        int count = mRadioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            TagView radioButton = (TagView) mRadioGroup.getChildAt(i);
            if (tag.equals(radioButton.getTag())) {
                radioButton.setSelected(true);
            } else {
                radioButton.setSelected(false);
            }
            showHideFragment(tag);
        }

    }

    public Fragment getCurrFragment() {
        if (mLastTab != null) {
            return mLastTab.fragment;
        }
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        View view = group.findViewById(checkedId);
        String tab = (String) view.getTag();
        if (isShowHide) {
            showHideFragment(tab);
        } else {
            replaceFragment(tab);
        }
    }

    private void replaceFragment(String tab) {
        TabInfo newTab = mTabs.get(tab);
        if (mLastTab != newTab) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(mContext, newTab.clss.getName(), newTab.args);
                    ft.replace(mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }
            ft.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
            mLastTab = newTab;

            // tab变化
            if (tabListener != null) {
                tabListener.onChange(mLastTab);
            }
        }
    }

    private void showHideFragment(String tab) {
        TabInfo newTab = mTabs.get(tab);
        if (mLastTab != newTab) {

            FragmentTransaction ft = mFragmentManager.beginTransaction();
            //
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.hide(mLastTab.fragment);
                    mLastTab.fragment.onPause();
                }
            }
            //
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(mContext, newTab.clss.getName(), newTab.args);
                    ft.add(mContainerId, newTab.fragment, newTab.tag);
                } else {
                    if (mLastTab != null && mLastTab.fragment != null) {
                        mLastTab.fragment.onResume();
                    }
                    ft.show(newTab.fragment);
                }
            }
            //
            ft.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
            mLastTab = newTab;

            // tab变化
            if (tabListener != null) {
                tabListener.onChange(mLastTab);
            }
        }
    }

    public static final class TabInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public String getTag() {
            return tag;
        }

        public Class<?> getClss() {
            return clss;
        }

        public Bundle getArgs() {
            return args;
        }

    }

    public interface BuildViewFactory {
        View tabView(String tag, ViewGroup parent);
    }
}
