package com.zzti.fengyongge.androiddevtool;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TabHost;
import com.orhanobut.logger.Logger;
import com.zzti.fengyongge.androiddevtool.fragment.AdminMainFragment;
import com.zzti.fengyongge.androiddevtool.fragment.MemberMainFragment;
import com.zzti.fengyongge.androiddevtool.fragment.ShopMainFragment;
import com.zzti.fengyongge.androiddevtool.fragment.TaskMainFragment;
import com.zzti.fengyongge.androiddevtool.view.TabIndicatorView;


public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost,tabhost;
    private String TAB_SHOP="门店",TAB_TASK="工作",TAB_CUSTOM="会员",TAB_ME="我的";
    TabIndicatorView shopIndicator,taskIndicator,cusstomIndicator,mecoverIndicator;
    int newNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initView() {
        tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(),
                R.id.realtabcontent);


        TabHost.TabSpec spec = tabhost.newTabSpec(TAB_SHOP);
        shopIndicator = new TabIndicatorView(this);
        shopIndicator.setTabIcon(R.drawable.main_shop_out,
                R.drawable.main_shop_on);
        shopIndicator.setTabTitle("门店");
        spec.setIndicator(shopIndicator);
        tabhost.addTab(spec, ShopMainFragment.class, null);


        spec = tabhost.newTabSpec(TAB_TASK);
        taskIndicator = new TabIndicatorView(this);
        taskIndicator.setTabIcon( R.drawable.main_task_out,
                R.drawable.main_task_on);
        taskIndicator.setTabTitle("工作");
        spec.setIndicator(taskIndicator);
        tabhost.addTab(spec, TaskMainFragment.class, null);

        spec = tabhost.newTabSpec(TAB_CUSTOM);
        cusstomIndicator = new TabIndicatorView(this);
        cusstomIndicator.setTabIcon(R.drawable.main_member_out,
                R.drawable.main_member_on);
        cusstomIndicator.setTabTitle("会员");
        spec.setIndicator(cusstomIndicator);
        tabhost.addTab(spec, MemberMainFragment.class, null);


        spec = tabhost.newTabSpec(TAB_ME);
        mecoverIndicator = new TabIndicatorView(this);
        mecoverIndicator.setTabIcon(R.drawable.main_admin_out,
                R.drawable.main_admin_on);
        mecoverIndicator.setTabTitle("我的");
        spec.setIndicator(mecoverIndicator);
        tabhost.addTab(spec, AdminMainFragment.class, null);

//      tabhost.getTabWidget().setDividerDrawable(android.R.color.white);// 去掉分割线
        tabhost.getTabWidget().setDividerDrawable(R.color.white);

        // 初始化 tab选中
        tabhost.setCurrentTabByTag(TAB_SHOP);
        shopIndicator.setTabSelected(true);

        // 设置tab切换的监听

        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tag) {
                shopIndicator.setTabSelected(false);
                taskIndicator.setTabSelected(false);
                cusstomIndicator.setTabSelected(false);
                mecoverIndicator.setTabSelected(false);

                if (TAB_SHOP.equals(tag)) {
                    shopIndicator.setTabSelected(true);
                }
                else if (TAB_TASK.equals(tag)) {
                    taskIndicator.setTabSelected(true);
                }
                else if (TAB_CUSTOM.equals(tag)) {
                    cusstomIndicator.setTabSelected(true);
                }
                else if (TAB_ME.equals(tag)) {
                    mecoverIndicator.setTabSelected(true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // TODO Auto-generated method stub
                Logger.i("app开始置于后台");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


}
