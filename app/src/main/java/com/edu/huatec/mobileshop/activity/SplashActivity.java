package com.edu.huatec.mobileshop.activity;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.edu.huatec.mobileshop.R;
import com.edu.huatec.mobileshop.common.BaseActivity;




public class SplashActivity extends BaseActivity {

    ImageView splash_loading_item;
    @Override
    public int getContentViewId() {
       return R.layout.activity_splash;
    }
    @Override
    protected void initView() {
        super.initView();

        splash_loading_item= (ImageView) findViewById(R.id.splash_loading_item);
        Animation translate = AnimationUtils.loadAnimation(this,R.anim.splash_loading);


        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(new Intent(SplashActivity.this, AdActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
//        splash_loading_item.setAnimation(translate);
        splash_loading_item.startAnimation(translate);
    }
}
