package com.zzti.fengyongge.androiddevtool.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zzti.fengyongge.androiddevtool.R;

import java.util.List;


public class SweetAlertDialog extends Dialog implements View.OnClickListener {
    private View mDialogView;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;
    private Animation mErrorInAnim;
    private AnimationSet mErrorXInAnim;
    private AnimationSet mSuccessLayoutAnimSet;
    private Animation mSuccessBowAnim;
    private TextView mContentTextView;
    private TextView mMsgTextView;
    private EditText content_edit;
    private String mTitleText;
    private String mContentText;
    private String mMsgtext;
    private String mContentText1;
    private boolean mShowCancel;
    private String mCancelText;
    private String mConfirmText;
    private String mHintText;
    private String isPassword;
    private Context context;

    private int mAlertType;
    private Drawable mCustomImgDrawable;
    private TextView mConfirmButton;
    private TextView mCancelButton;
    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;

    public static final int NORMAL_TYPE = 0;
    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int CUSTOM_SPACIAL_TYPE = 5;
    public static interface OnSweetClickListener {
        public void onClick(SweetAlertDialog sweetAlertDialog);
    }

    public SweetAlertDialog(Context context) {
        this(context, NORMAL_TYPE);
    }

    public SweetAlertDialog(Context context, int alertType) {
        super(context, R.style.alert_dialog);
        this.context = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mAlertType = alertType;
        mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet)OptAnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
        // 2.3.x system don't support alpha-animation on layer-list drawable
        // remove it from animation set
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            List<Animation> childAnims = mErrorXInAnim.getAnimations();
            int idx = 0;
            for (;idx < childAnims.size();idx++) {
                if (childAnims.get(idx) instanceof AlphaAnimation) {
                    break;
                }
            }
            if (idx < childAnims.size()) {
                childAnims.remove(idx);
            }
        }
        mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
        mSuccessLayoutAnimSet = (AnimationSet)OptAnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        SweetAlertDialog.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sweetalertdialog);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mContentTextView = (TextView)findViewById(R.id.content_text);
        mMsgTextView = (TextView)findViewById(R.id.msg_text);
        content_edit = (EditText)findViewById(R.id.content_edit);
        mConfirmButton = (TextView)findViewById(R.id.confirm_button);
        mCancelButton = (TextView)findViewById(R.id.cancel_button);

        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        setContentText(mContentText);
       // setContentText(mContentText,mContentText1);
        setMsgtext(mMsgtext);
        showCancelButton(mShowCancel);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        setHintText(mHintText);
        
        setIsPassword(isPassword);
        changeAlertType(mAlertType, true);
    }

    private void restore () {

        mConfirmButton.setBackgroundResource(R.drawable.red_button_background);
    }

   

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        // call after created views
        if (mDialogView != null) {
            if (!fromCreate) {
                // restore all of views state before switching alert type
                restore();
            }
            switch (mAlertType) {
               
                case WARNING_TYPE:
                    mConfirmButton.setBackgroundResource(R.drawable.red_button_background);
                    break;
                case CUSTOM_IMAGE_TYPE:
                    setCustomImage(mCustomImgDrawable);
                    break;
                case CUSTOM_SPACIAL_TYPE://第五种情况
                    setCustomImage(mCustomImgDrawable);
                  //  setContentText(mContentText, mContentText1);
                    break;
            }
            
        }
    }

    public int getAlerType () {
        return mAlertType;
    }

    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }


    public String getTitleText () {
        return mTitleText;
    }
   

    public SweetAlertDialog setCustomImage (Drawable drawable) {
        mCustomImgDrawable = drawable;
        
        return this;
    }

    public SweetAlertDialog setCustomImage (int resourceId) {
        return setCustomImage(getContext().getResources().getDrawable(resourceId));
    }

    public String getContentText () {
        return mContentText;
    }
    public String getContentText1 () {
        return mContentText1;
    }

    public SweetAlertDialog setContentText (String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            mContentTextView.setVisibility(View.VISIBLE);
            mContentTextView.setText(mContentText);
            content_edit.setVisibility(View.GONE);
        }
        return this;
    }
    public SweetAlertDialog setMsgtext(String text) {
    	mMsgtext = text;
    	if (mMsgTextView != null && mMsgtext != null) {
    		mMsgTextView.setVisibility(View.VISIBLE);
    		mMsgTextView.setText(mMsgtext);
    		content_edit.setVisibility(View.GONE);
    	}
    	return this;
    }
   

    
    private void setParams() {
    	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	lp.setMargins(0, 10, 0, 5);
    	content_edit.setLayoutParams(lp);
	}

	public void setTextVisiable(){
    	
    	content_edit.setVisibility(View.GONE);
    	mContentTextView.setVisibility(View.VISIBLE);
    }
    public String getEditText(){
    	
    	return content_edit.getText().toString().trim();
    }
    public boolean isShowCancelButton () {
        return mShowCancel;
    }

    public SweetAlertDialog showCancelButton (boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getCancelText () {
        return mCancelText;
    }

    public SweetAlertDialog setCancelText (String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            mCancelButton.setText(mCancelText);
        }
        return this;
    }

    public String getConfirmText () {
        return mConfirmText;
    }

    public SweetAlertDialog setConfirmText (String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }
    
  
    public SweetAlertDialog setHintText (String text) {
    	mHintText = text;
    	if (content_edit != null && mHintText != null) {
    		content_edit.setHint(mHintText);
            content_edit.setHintTextColor(context.getResources().getColor(R.color.light_grey));
    	}
    	return this;
    }
    
    
    //判断是否为密码
    public SweetAlertDialog setIsPassword (String isPsd) {
    	isPassword =isPsd;
    	if (content_edit != null && isPassword != null) {
    		
    		if(isPassword.equals("1")){
    			
    			content_edit.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
    			content_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
    		
    		}
    		
    	}
    	return this;
    }
    
    
    
    
    

    public SweetAlertDialog setCancelClickListener (OnSweetClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public SweetAlertDialog setConfirmClickListener (OnSweetClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    @Override
	protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
    }

    @Override
	public void dismiss() {
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_button) {
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(SweetAlertDialog.this);
            } else {
                dismiss();
            }
        } else if (v.getId() == R.id.confirm_button) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(SweetAlertDialog.this);
            } else {
                dismiss();
            }
        }
    }
}
