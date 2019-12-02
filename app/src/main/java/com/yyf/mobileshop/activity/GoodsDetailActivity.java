package com.yyf.mobileshop.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyf.mobileshop.R;
import com.yyf.mobileshop.common.BaseActivity;
import com.yyf.mobileshop.http.ProgressDialogSubscriber;
import com.yyf.mobileshop.http.entity.GoodsDetailEntity;
import com.yyf.mobileshop.http.presenter.GoodsPresenter;
import com.yyf.mobileshop.utils.SystemCofig;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailActivity extends BaseActivity {

    private static final String TAG = "GoodsDetailActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.image_pager)
    ViewPager viewPager;
    @BindView(R.id.image_def)
    ImageView imageDef;
    @BindView(R.id.image_pager_index)
    TextView imagePagerIndex;

    private int goods_id;
    private String goods_name;
    private List<GoodsDetailEntity.GisBean> datas;
    private MyPagerAdapter pagerAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @OnClick(R.id.favorite_layout)
    void favorite() {
        if(SystemCofig.isLogin()){
            toastShort("收藏");
        }else {
            startActivityForResult(new Intent(this,LoginActivity.class),1001);
        }
    }

    @OnClick(R.id.cat_layout)
    void cat() {
        if(SystemCofig.isLogin()){
            toastShort("购物车");
        }else {
            startActivityForResult(new Intent(this,LoginActivity.class),1002);
        }
    }

    @OnClick(R.id.add_to_cart)
    void add_to_cart() {
        if(SystemCofig.isLogin()){
            toastShort("添加到购物车");
        }else {
            startActivityForResult(new Intent(this,LoginActivity.class),1002);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        goods_id = getIntent().getIntExtra("goods_id", 0);
        goods_name = getIntent().getStringExtra("goods_name");
        tvTitle.setMaxEms(9);
        tvTitle.setLines(1);
        if (TextUtils.isEmpty(goods_name)) {
            tvTitle.setText("商品详情");
        } else {
            tvTitle.setText(goods_name);
        }
        //初始化ViewPager
        datas = new ArrayList<>();
        pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                imagePagerIndex.setText(String.format("%d/%d", position + 1, datas.size()));
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        GoodsPresenter.goodsDetail(new ProgressDialogSubscriber<GoodsDetailEntity>(GoodsDetailActivity.this) {
            @Override
            public void onNext(GoodsDetailEntity goodsDetailEntity) {
                datas.clear();

                //设置图片
                List<GoodsDetailEntity.GisBean> gis = goodsDetailEntity.getGis();
                if (gis != null && gis.size() != 0) {
                    imageDef.setVisibility(View.GONE);
                    viewPager.setVisibility(View.VISIBLE);
                    datas.addAll(gis);
                    imagePagerIndex.setText(String.format("%d/%d", 1, datas.size()));
                    pagerAdapter.notifyDataSetChanged();
                } else {
                    imageDef.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.GONE);
                }
            }
        }, goods_id);
    }

    @OnClick(R.id.iv_back)
    void close() {
        finish();
    }

    public class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return datas.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }
        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            GoodsDetailEntity.GisBean gisBean = datas.get(position);
            ImageView imageView = new ImageView(GoodsDetailActivity.this);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toastShort("onClick position = " + position);
                }
            });
            ImageLoader.getInstance().displayImage(gisBean.getThumbnail(), imageView);
            view.addView(imageView);
            return imageView;
        }

    }
}
