package com.edu.huatec.mobileshop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.huatec.mobileshop.R;
import com.edu.huatec.mobileshop.common.BaseActivity;
import com.edu.huatec.mobileshop.common.MobileShopApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;



public class AdActivity extends BaseActivity {


    private TextView tv_skip;

    @Override
    public int getContentViewId() {
        return R.layout.activity_ad;
    }

    @Override
    protected void initView() {
        super.initView();
        //显示图片
        tv_skip = (TextView) findViewById(R.id.tv_skip);
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.iv_image);
        //String url = "http://10.37.59.210/MobileShop/img/ad/new_ad_img.jpg";
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575125386663&di=340e7ab00ea0333d73ca1d2a1e15fa93&imgtype=0&src=http%3A%2F%2Fimage2.sina.com.cn%2Fent%2Fd%2F2005-06-21%2FU105P28T3D758537F326DT20050621155831.jpg";

        ImageLoader.getInstance().displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                MobileShopApp.handler.post(jumpTread);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                MobileShopApp.handler.post(jumpTread);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

                MobileShopApp.handler.post(jumpTread);
            }
        });

    }

    private void skip() {
        Intent intent = new Intent(AdActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        c_count = COUNT;
        MobileShopApp.handler.removeCallbacks(jumpTread);
    }

    private static final String SKIP_TEXT = "点击跳过 %d";
    private final static int COUNT = 5;
    private final static int DELAYED = 1000;
    private int c_count = COUNT;

    private Runnable jumpTread = new Runnable() {
        @Override
        public void run() {
            if (c_count <= 0) {
                skip();
            } else {
                tv_skip.setText(String.format(SKIP_TEXT, c_count));
                c_count--;
                MobileShopApp.handler.postDelayed(jumpTread, DELAYED);
            }
        }
    };
}
