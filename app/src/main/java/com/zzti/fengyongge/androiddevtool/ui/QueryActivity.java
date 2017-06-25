package com.zzti.fengyongge.androiddevtool.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.zzti.fengyongge.androiddevtool.R;
import com.zzti.fengyongge.androiddevtool.adapter.BaseCommAdapter;
import com.zzti.fengyongge.androiddevtool.adapter.ViewHolder;
import com.zzti.fengyongge.androiddevtool.bean.TagsBean;
import com.zzti.fengyongge.androiddevtool.dialog.AlertHelper;
import com.zzti.fengyongge.androiddevtool.dialog.ProgressBarHelper;
import com.zzti.fengyongge.androiddevtool.myinterface.AlertCallback;
import com.zzti.fengyongge.androiddevtool.myinterface.ApiCallback;
import com.zzti.fengyongge.androiddevtool.myinterface.SweetAlertCallBack;
import com.zzti.fengyongge.androiddevtool.net.api.Api;
import com.zzti.fengyongge.androiddevtool.utils.ToastUtils;
import com.zzti.fengyongge.androiddevtool.view.RefreshLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author fengyonggge
 * @date 2017/5/23
 */
public class QueryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    @BindView(R.id.lv)
    ListView lv;
    Dialog windowsBar;
    @BindView(R.id.llNoResult)
    LinearLayout llNoResult;
    @BindView(R.id.rlSwipe)
    RefreshLayout rlSwipe;
    private int pageIndex = 1;
    private int pageNumber = 20;
    private boolean isLoading = false;
    private boolean noMoreData = false;
    private boolean isRefresh = false;
    private boolean isClear = false;
    private int totalResult;

    private List<TagsBean> labels_listTemp = new ArrayList();
    private List<TagsBean> labels_list = new ArrayList();
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);

        loadMore();
    }


    public void loadMore() {
//        windowsBar = ProgressBarHelper.createWindowsBar(QueryActivity.this);


        Api.Inst(QueryActivity.this).getLabels(new ApiCallback() {
            @Override
            public void onDataSuccess(JSONObject data) {

                if (windowsBar != null && windowsBar.isShowing()) {
                    windowsBar.dismiss();
                }
                if (isClear) {
                    labels_list.clear();
                    labels_listTemp.clear();
                }

                totalResult = Integer.parseInt(data.getJSONObject("data")
                        .getString("total"));
                labels_list.addAll(JSON.parseArray(data.getJSONObject("data")
                        .getString("list"), TagsBean.class));

//                for (int i = 0; i <labels_listTemp.size() ; i++) {
//                    if(labels_listTemp.get(i).getMark().equals("1")){
//                        labels_list.add(labels_listTemp.get(i));
//                    }
//                }

                if(labels_list.size()>0){
                     adapter = new Adapter(labels_listTemp);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


                if (labels_list.size() == 0) {
                    llNoResult.setVisibility(View.VISIBLE);
                } else {
                    llNoResult.setVisibility(View.GONE);
                }
                if (isRefresh) {
                    rlSwipe.setRefreshing(false);
                } else {
                    rlSwipe.setLoading(false);
                }
                isClear = false;
                if (labels_list.size() == 0
                        || (pageIndex == 1 && totalResult < pageNumber)
                        || (pageIndex == 1 && totalResult == pageNumber)
                        || labels_list.size() == totalResult) {

                    noMoreData = true;
                }
                pageIndex++;
                isLoading = false;
            }

            @Override
            public void onDataError(JSONObject data) {
                if (windowsBar != null && windowsBar.isShowing()) {
                    windowsBar.dismiss();
                }
                if (isRefresh) {
                    rlSwipe.setRefreshing(false);
                }

                ToastUtils.showToast(QueryActivity.this,data.getString("msg"));
            }

            @Override
            public void onNetError(String data) {
                if (windowsBar != null && windowsBar.isShowing()) {
                    windowsBar.dismiss();
                }
                ToastUtils.showToast(QueryActivity.this,data);

            }
        });

    }

    @Override
    public void onLoad() {

        if (noMoreData == true) {
                ToastUtils.showToast(QueryActivity.this, "没有更多");
            rlSwipe.setLoading(false);
        } else {
            isRefresh = false;
            isLoading = true;
//            progressBar.setVisibility(View.VISIBLE);
            loadMore();
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        refreshgoods();
    }


    private void refreshgoods() {
        windowsBar = ProgressBarHelper.createWindowsBar(this);
        totalResult = 0;
        pageIndex = 1;
        pageNumber = 20;
        isLoading = false;
        noMoreData = false;
        loadMore();
    }


    public class Adapter extends BaseCommAdapter<TagsBean> {


        @BindView(R.id.tvTagName)
        TextView tvTagName;

        public Adapter(List<TagsBean> datas) {
            super(datas);
        }

        @Override
        protected void setUI(ViewHolder holder, final int position, Context context) {
            ButterKnife.bind(this, holder.getConverView());

            tvTagName.setText( labels_list.get(position).getName());

            Logger.i("--"+labels_list.get(position).getName());


            //修改
            tvTagName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertHelper.create2EditAlert(QueryActivity.this, "确定", "取消", "是否修改", new SweetAlertCallBack() {
                        @Override
                        public void onConfirm(final String data) {

                            Api.Inst( QueryActivity.this).updateMemberTag(data, labels_list.get(position).getId(), new ApiCallback() {
                                @Override
                                public void onDataSuccess(JSONObject data) {
                                    if (windowsBar != null && windowsBar.isShowing()) {
                                        windowsBar.dismiss();
                                    }


                                }

                                @Override
                                public void onDataError(JSONObject data) {
                                    if (windowsBar != null && windowsBar.isShowing()) {
                                        windowsBar.dismiss();
                                    }
                                    ToastUtils.showToast(QueryActivity.this,data.getString("msg"));

                                }

                                @Override
                                public void onNetError(String data) {
                                    if (windowsBar != null && windowsBar.isShowing()) {
                                        windowsBar.dismiss();
                                    }
                                    ToastUtils.showToast(QueryActivity.this,data);

                                }
                            });


                        }

                        @Override
                        public void onCancle() {

                        }
                    });


                }
            });


            tvTagName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertHelper.createDialog(QueryActivity.this, "确定", "取消", "是否删除", new AlertCallback() {
                        @Override
                        public void onConfirm() {

                            windowsBar = ProgressBarHelper.createWindowsBar(QueryActivity.this);

                            Api.Inst( QueryActivity.this).deleteTag(labels_list.get(position).getId(), new ApiCallback() {
                                @Override
                                public void onDataSuccess(JSONObject data) {
                                    if (windowsBar != null && windowsBar.isShowing()) {
                                        windowsBar.dismiss();
                                    }
                                }

                                @Override
                                public void onDataError(JSONObject data) {
                                    if (windowsBar != null && windowsBar.isShowing()) {
                                        windowsBar.dismiss();
                                    }
                                    ToastUtils.showToast(QueryActivity.this,data.getString("msg"));

                                }

                                @Override
                                public void onNetError(String data) {
                                    if (windowsBar != null && windowsBar.isShowing()) {
                                        windowsBar.dismiss();
                                    }
                                    ToastUtils.showToast(QueryActivity.this, data);
                                }


                        });


                        }

                        @Override
                        public void onCancel() {

                        }
                    });

                    return false;
                }
            });

        }

        @Override
        protected int getLayoutId() {
            return R.layout.activity_query_item;
        }

    }

}