package com.imissq.views;

import com.imissq.R;
import com.imissq.utils.DeviceConfiger;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 对话框公共对话框,通过方法设置
 */
public class CommonDialog extends Dialog {
    private Context context;
    private EditText mEditText;
    private Button mFinishBtn;
    private Button mCancelBtn;
    private TextView tvContent;
    private TextView tvTitle;
    private TextView tvCollect;
    private LinearLayout layoutContentView;
    private OnFinishInterface finish;
    private InputMethodManager mInputManager;
    private int minInputCount;

    public CommonDialog(Context context, OnFinishInterface finish)
    {
        super(context, R.style.common_dialog);
        this.context = context;
        this.finish = finish;

        mInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setContentView(R.layout.layout_dailog_common);
        WindowManager.LayoutParams p = getWindow().getAttributes();// 设置对话框宽度
        p.width = DeviceConfiger.sWidth - DeviceConfiger.dp2px(15) * 2;
        getWindow().setAttributes(p);
        initViews();
    }

    /**
     * 
     * 设置弹框内容
     */
    public void setMessage(String message) {
        tvContent.setText(message);
    }

    public void setTitleGone() {
        tvTitle.setVisibility(View.GONE);
    }

    public void setBottomText(String leftText, String rightText) {
        mCancelBtn.setText(leftText);
        mFinishBtn.setText(rightText);
    }

    /**
     * 
     * 只有一个确定按钮
     */
    public void setConfirm(String confirm) {
        mCancelBtn.setVisibility(View.GONE);
        mFinishBtn.setBackgroundResource(R.drawable.bottom_corner_shape);
        mFinishBtn.setText(confirm);
    }

    /**
     * 
     * 是否显示EditText
     */
    public void setEditTextVisible() {
        mEditText.setVisibility(View.VISIBLE);
    }

    /**
     * 
     * 是否显示EditText Hint
     */
    public void setEditTextHint(String hint) {
        mEditText.setHint(hint);
    }

    /**
     * 
     * 设置EditText最多输入的个数
     */
    public void setEditTextMaxEms(int maxems) {
        mEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxems) });
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        tvTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        tvTitle.setText(titleId);
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public void setTvContent(TextView tvContent) {
        this.tvContent = tvContent;
    }

    public void setInputTypePassword() {
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void setTextMinInputCount(int count) {
        minInputCount = count;
    }

    public void setContentView(View view) {
        layoutContentView.removeAllViews();
        layoutContentView.addView(view);
    }

    public void setCollect() {
        tvCollect.setVisibility(View.VISIBLE);
        mFinishBtn.setText("编辑");
        mCancelBtn.setVisibility(View.VISIBLE);

    }

    private void initViews() {
        mEditText = (EditText) this.findViewById(R.id.dlg_edit_text);
        mFinishBtn = (Button) this.findViewById(R.id.btn_confirm);
        mCancelBtn = (Button) this.findViewById(R.id.btn_cancel);
        tvContent = (TextView) this.findViewById(R.id.textView2);
        tvCollect = (TextView) this.findViewById(R.id.textView3);
        layoutContentView = (LinearLayout) this.findViewById(R.id.layoutContentView);
        tvTitle = (TextView) this.findViewById(R.id.tvTitle);
        mFinishBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                String msg = mEditText.getText().toString();
                if (mEditText.isFocused()) {
                    if (!TextUtils.isEmpty(msg)) {
                        if (msg.length() < minInputCount) {
                            //ToastUtils.showMsg(context, "最少输入" + minInputCount + "个字符");
                        } else {
                            if (finish != null) {
                                finish.onConfirm(msg);
                            }
                            cancel();
                        }
                    } else {
                        //ToastUtils.showMsg(context, "最少输入" + minInputCount + "个字符");
                    }
                } else {
                    if (finish != null) {
                        finish.onConfirm("");
                    }
                    dismiss();
                }

            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                finish.onCancel("");
                cancel();
            }
        });

        setOnDismissListener(new OnDismissListener()
        {

            @Override
            public void onDismiss(DialogInterface dialog) {
                mInputManager.hideSoftInputFromInputMethod(mEditText.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        }, 200);
    }

    public static interface OnFinishInterface {
        void onConfirm(String msg);

        void onCancel(String msg);
    }
}
