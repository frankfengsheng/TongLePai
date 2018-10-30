package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.base.BaseActivity;

import org.w3c.dom.Text;

/**
 * Created by cheng on 2018/6/27.
 */

public abstract class TitleActivity extends BaseActivity {
    protected TextView mMiddleTv;
    private RelativeLayout mMidLayout;
    private View mTitleView;
    private RelativeLayout mRightLayout;
    protected ImageView mLeftIv;
    private ImageView rightImage;
    private TextView tvRight;

    protected void onCreate(@Nullable Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        initTitleView();
        setDefaultBackBtn();
    }

    private void initTitleView() {
        mTitleView = findViewById(R.id.layout_title);
        mRightLayout = (RelativeLayout) mTitleView.findViewById(R.id.title_right_layout);
        mLeftIv = (ImageView) mTitleView.findViewById(R.id.title_left_back_iv);
        mMiddleTv = (TextView) mTitleView.findViewById(R.id.title_middle_tv);
        tvRight= (TextView) mTitleView.findViewById(R.id.tv_title_right);
        rightImage= (ImageView) mTitleView.findViewById(R.id.iv_title_right);

    }

    protected void setMidTitle(String title) {
        mMiddleTv.setVisibility(View.VISIBLE);
        mMiddleTv.setText(title);
    }

    protected  void setTitle(String title,boolean isRightText,String rightText,boolean isRightIamge,int rightResource){
        if(isRightText){
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(rightText);
        }
        if(isRightIamge){
            rightImage.setVisibility(View.VISIBLE);
            rightImage.setImageResource(rightResource);
        }
        mMiddleTv.setVisibility(View.VISIBLE);
        mMiddleTv.setText(title);
    }

    protected void setRightView(View view) {
        mRightLayout.setVisibility(View.VISIBLE);
        mRightLayout.removeAllViews();
        mRightLayout.addView(view);
    }

    protected void titleBackAction() {
        finish();
    }


    protected void setDefaultBackBtn() {
        mLeftIv.setVisibility(View.VISIBLE);

        mLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleBackAction();
            }
        });
    }
}
