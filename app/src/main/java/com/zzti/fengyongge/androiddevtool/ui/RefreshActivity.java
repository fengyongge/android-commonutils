package com.zzti.fengyongge.androiddevtool.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzti.fengyongge.androiddevtool.R;
import com.zzti.fengyongge.androiddevtool.adapter.BaseCommAdapter;
import com.zzti.fengyongge.androiddevtool.adapter.ViewHolder;
import com.zzti.fengyongge.androiddevtool.bean.TestBean;
import com.zzti.fengyongge.androiddevtool.dialog.ProgressBarHelper;
import com.zzti.fengyongge.androiddevtool.myinterface.ApiCallback;
import com.zzti.fengyongge.androiddevtool.net.api.Api;
import com.zzti.fengyongge.androiddevtool.utils.ToastUtils;
import com.zzti.fengyongge.androiddevtool.view.RefreshLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseObject;

public class RefreshActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    @BindView(R.id.rlLeft)
    RelativeLayout rlLeft;
    @BindView(R.id.tvTopText)
    TextView tvTopText;
    @BindView(R.id.llNoResult)
    LinearLayout llNoResult;
    @BindView(R.id.lvStaff)
    ListView lvStaff;
    @BindView(R.id.rlSwipe)
    RefreshLayout rlSwipe;
    private ProgressBar progressBar;
    List<TestBean> testList = new ArrayList<>();
    Adapter adapter;

    //
    private Dialog windowsBar = null;
    private int totalResult = 0;
    private int pageIndex = 1;
    private int pageNumber = 20;
    private boolean isLoading = false;
    private boolean noMoreData = false;
    private boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        ButterKnife.bind(this);

        View footerView = getLayoutInflater().inflate(R.layout.listview_footer, null);
        progressBar = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);
        lvStaff.addFooterView(footerView);

        rlSwipe.setOnRefreshListener(RefreshActivity.this);
        rlSwipe.setOnLoadListener(this);
        rlSwipe.setChildView(lvStaff);
        rlSwipe.setColorSchemeResources(R.color.colorAccent);

//        refreshgoods();
    }



    public void loadMore() {

        windowsBar = ProgressBarHelper.createWindowsBar(RefreshActivity.this);
        
        Api.Inst(RefreshActivity.this).meetingASssistant(pageIndex, new ApiCallback() {
                    @Override
                    public void onDataSuccess(JSONObject data) {


                        if (windowsBar != null && windowsBar.isShowing()) {
                            windowsBar.dismiss();
                        }

                        if (isRefresh) {
                            rlSwipe.setRefreshing(false);
                        }

                        if (isLoading) {
                            rlSwipe.setLoading(false);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            testList.clear();
                        }

                        totalResult = Integer.parseInt(data.getJSONObject("data")
                                .getString("total"));
                        testList.addAll(JSON.parseArray(parseObject(data.getString("data")).getString("list"), TestBean.class));

                        if (testList.size() == 0
                                || (pageIndex == 1 && totalResult <= pageNumber)
                                || testList.size() == totalResult) {
                            noMoreData = true;

                        } else {
                            noMoreData = false;
                            pageIndex++;
                        }

                        if (testList.size() == 0) {
                            lvStaff.setVisibility(View.GONE);
                            llNoResult.setVisibility(View.VISIBLE);
                        } else {
                            lvStaff.setVisibility(View.VISIBLE);
                            llNoResult.setVisibility(View.GONE);
                        }

                        //
                        adapter = new Adapter(testList);
                        lvStaff.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDataError(JSONObject data) {
                        if (isRefresh) {
                            rlSwipe.setRefreshing(false);
                        }

                        if (windowsBar != null && windowsBar.isShowing()) {
                            windowsBar.dismiss();
                        }
                        ToastUtils.showToast(RefreshActivity.this, data.getString("msg"));
                    }

                    @Override
                    public void onNetError(String data) {
                        if (isRefresh) {
                            rlSwipe.setRefreshing(false);
                        }

                        if (windowsBar != null && windowsBar.isShowing()) {
                            windowsBar.dismiss();
                        }
                        ToastUtils.showToast(RefreshActivity.this, data);
                    }
                });
    
    }




    @OnClick(R.id.rlLeft)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onLoad() {

        if (noMoreData == true) {
            ToastUtils.showToast(RefreshActivity.this, "没有更多");
            rlSwipe.setLoading(false);
        } else {
            isRefresh = false;
            isLoading = true;
            progressBar.setVisibility(View.VISIBLE);
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

    public class Adapter extends BaseCommAdapter<TestBean> {


        @BindView(R.id.tv)
        TextView tv;

        public Adapter(List<TestBean> datas) {
            super(datas);
        }

        @Override
        protected void setUI(ViewHolder holder, int position, Context context) {
            ButterKnife.bind(this,holder.getConverView());

        }

        @Override
        protected int getLayoutId() {
            return R.layout.activity_refresh_item;
        }

    }
}
