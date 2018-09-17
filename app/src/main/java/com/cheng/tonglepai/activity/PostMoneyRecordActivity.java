package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.PostMoneyRecordAdapter;
import com.cheng.tonglepai.data.PostMoneyRecordData;
import com.cheng.tonglepai.net.PostMoneyRecordRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/9/17.
 */

public class PostMoneyRecordActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private LoadingDialog loadingDialog;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private PostMoneyRecordAdapter mAdapter;
    private TextView tvAll, tvHas, tvUnpost;
    private ListView lvPostMoney;
    private Button btnToPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_psot_money_record);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("上缴记录");
        initRefreshLayout();
        initView();
        initData();

    }

    private void initData() {
        loadingDialog.show();
        PostMoneyRecordRequest mRequest = new PostMoneyRecordRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<PostMoneyRecordData>() {
            @Override
            public void onSuccess(PostMoneyRecordData data) {
                tvAll.setText("￥"+data.getPrice_data().getZ_price());
                tvHas.setText("￥"+data.getPrice_data().getYj_price());
                tvUnpost.setText("￥"+data.getPrice_data().getWj_price());
                if (isLoad) {
                    isLoad = false;
                    if (data.getData().size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mAdapter.setLoadData(data.getData());
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                    return;
                }
                mAdapter.clearData();
                mAdapter.setData(data.getData());
                if (data.getData().size() < 10)
                    needLoad = false;
                else
                    needLoad = true;
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(String msg, int code) {
                loadingDialog.dismiss();
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                Toast.makeText(PostMoneyRecordActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
            }
        });
        mRequest.requestPostMoneyRecord(page + "");
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        tvAll = (TextView) findViewById(R.id.tv_all_record);
        tvHas = (TextView) findViewById(R.id.tv_has_record);
        tvUnpost = (TextView) findViewById(R.id.tv_un_record);
        lvPostMoney = (ListView) findViewById(R.id.lv_post_money);
        mAdapter = new PostMoneyRecordAdapter(this);
        lvPostMoney.setAdapter(mAdapter);

        btnToPost = (Button) findViewById(R.id.btn_to_post_money);
        btnToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostMoneyRecordActivity.this,ToPostMoneyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_post_money);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoad = false;
        isFirst = true;
        page = 1;
        initData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoad = true;
        isFirst = false;
        page++;
        if (needLoad) {
            initData();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoad = false;
        isFirst = true;
        page = 1;
        initData();
    }
}
