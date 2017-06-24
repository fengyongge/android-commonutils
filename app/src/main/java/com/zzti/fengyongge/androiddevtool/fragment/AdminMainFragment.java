package com.zzti.fengyongge.androiddevtool.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzti.fengyongge.androiddevtool.R;
import com.zzti.fengyongge.androiddevtool.app.MyApp;
import com.zzti.fengyongge.androiddevtool.myinterface.ApiCallback;
import com.zzti.fengyongge.androiddevtool.net.NetUtils;
import com.zzti.fengyongge.androiddevtool.net.api.Api;
import com.zzti.fengyongge.androiddevtool.utils.AppUpdateUtils;
import com.zzti.fengyongge.androiddevtool.utils.AppUtils;
import com.zzti.fengyongge.androiddevtool.utils.PreferencesUtils;
import com.zzti.fengyongge.androiddevtool.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminMainFragment extends Fragment {

    View view;
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.rlLeft)
    RelativeLayout rlLeft;
    @BindView(R.id.rlVersion)
    RelativeLayout rlVersion;
    @BindView(R.id.tvTopText)
    TextView tvTopText;
    String code="2";
    Unbinder unbinder;

    public AdminMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvTopText.setText("我的");
        rlLeft.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llExit,R.id.rlVersion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llExit:
                PreferencesUtils.putBoolean(getActivity(),"login",false);
                MyApp.killActivity();
                getActivity().finish();
                break;
            case R.id.rlVersion:

//                update();
                break;

        }
    }






    public void update() {

        Api.Inst(getActivity()).update(new ApiCallback() {
            @Override
            public void onDataSuccess(JSONObject data) {

                String status = JSON.parseObject(data.getString("data")).getString("status");
                String code = JSON.parseObject(data.getString("data")).getString("version");
                String download_url = JSON.parseObject(data.getString("data")).getString("url");

                String message = JSON.parseObject(data.getString("data")).getString("message");

                try {
                    if (AppUtils.getVersionCode(getActivity()) < Integer.parseInt(code)) {

                        if(status.equals("0")){
                            AppUpdateUtils appUpdateUtils = new AppUpdateUtils();
                            appUpdateUtils.showUpdateDialog(getActivity(), message, download_url, status);
                        }

                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onDataError(JSONObject data) {

                ToastUtils.showToast(getActivity(), data.getString("msg"));
            }

            @Override
            public void onNetError(String data) {

                ToastUtils.showToast(getActivity(), data);
            }
        });
    }
}
