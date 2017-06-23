package com.zzti.fengyongge.androiddevtool.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.zzti.fengyongge.androiddevtool.R;
import com.zzti.fengyongge.androiddevtool.bean.LoginBean;
import com.zzti.fengyongge.androiddevtool.myinterface.ApiCallback;
import com.zzti.fengyongge.androiddevtool.net.api.Api;
import com.zzti.fengyongge.androiddevtool.ui.QueryActivity;
import com.zzti.fengyongge.androiddevtool.utils.LogUtils;
import com.zzti.fengyongge.androiddevtool.utils.PreferencesUtils;
import com.zzti.fengyongge.androiddevtool.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopMainFragment extends Fragment {

    View view;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvQuery)
    TextView tvQuery;
    @BindView(R.id.tvAddtag)
    TextView tvAddtag;
    @BindView(R.id.tvLogo)
    TextView tvLogo;


    public ShopMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shop_main, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.ivPhoto, R.id.tvName, R.id.tvLogin, R.id.tvQuery, R.id.tvAddtag, R.id.tvLogo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPhoto:


                break;
            case R.id.tvName:
                break;
            case R.id.tvLogin:

                Api.Inst(getActivity()).Login("13661390463", "123456", new ApiCallback() {
                    @Override
                    public void onDataSuccess(JSONObject data) {

                        LogUtils.i("data："+data);

                        LoginBean loginBean = JSON.parseObject(data.getString("data"), LoginBean.class);


                        PreferencesUtils.putString(getActivity(), "staff_id", loginBean.getId());
                        PreferencesUtils.putString(getActivity(), "supply_id", loginBean.getSupplier_id());
                        StringUtils.filtNull(tvName,loginBean.getUsername());
                        ImageLoader.getInstance().displayImage(loginBean.getStaff_image(),ivPhoto);
                    }

                    @Override
                    public void onDataError(JSONObject data) {

                    }

                    @Override
                    public void onNetError(String data) {

                    }
                });

                break;
            case R.id.tvQuery:
                startActivity(new Intent(getActivity(), QueryActivity.class));
                break;
            case R.id.tvAddtag:
                break;
            case R.id.tvLogo:
                break;
        }
    }
}
