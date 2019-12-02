package com.edu.huatec.mobileshop.activity;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edu.huatec.mobileshop.R;
import com.edu.huatec.mobileshop.common.BaseActivity;
import com.edu.huatec.mobileshop.fragment.NavigationFragment;

public class MainActivity extends BaseActivity {

    @Override
    public @LayoutRes
    int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        super.initView();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_main,new NavigationFragment());
        transaction.commit();

    }
}
