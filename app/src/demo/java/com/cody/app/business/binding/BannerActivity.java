package com.cody.app.business.binding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cody.app.R;
import com.cody.xf.widget.banner.BannerRecyclerAdapter;
import com.cody.xf.widget.banner.BannerViewModel;
import com.cody.xf.widget.banner.OnBannerClickListener;
import com.cody.xf.widget.banner.XFBanner;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity implements OnBannerClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        XFBanner xfBanner = (XFBanner) findViewById(R.id.xf_banner);

        XFBanner xfBanner1 = (XFBanner) findViewById(R.id.rv_banner_1);
        XFBanner xfBanner2 = (XFBanner) findViewById(R.id.rv_banner_2);
        XFBanner xfBanner3 = (XFBanner) findViewById(R.id.rv_banner_3);
        XFBanner xfBanner4 = (XFBanner) findViewById(R.id.rv_banner_4);
        XFBanner xfBanner5 = (XFBanner) findViewById(R.id.rv_banner_5);

        final List<BannerViewModel> bannerViewModels = new ArrayList<>();
        bannerViewModels.add(new BannerViewModel("http://img1.uat1.rs.com/g1/M00/00/07/wKh8y1ihhMaAPTU_AAKdo5CtTjY289.jpg"));
        bannerViewModels.add(new BannerViewModel("http://img1.uat1.rs.com/g1/M00/00/07/wKh8y1ihhJSAPwiAAAGsFC660jQ989.jpg"));
        bannerViewModels.add(new BannerViewModel("http://img1.uat1.rs.com/g1/M00/00/07/wKh8y1ihhTGAMxrgAAGre0JTakk658.jpg"));
        bannerViewModels.add(new BannerViewModel("http://img1.uat1.rs.com/g1/M00/00/21/wKh8y1i5DRWAQGR1AAFvHmbSKZ4625.jpg"));
        bannerViewModels.add(new BannerViewModel("http://img1.uat1.rs.com/g1/M00/00/12/wKh8y1izzjaAZocbAAEzQ7376oE170.jpg"));
        BannerRecyclerAdapter bannerRecyclerAdapter = new BannerRecyclerAdapter(bannerViewModels);
        xfBanner.setBannerRecyclerAdapter(bannerRecyclerAdapter);
        xfBanner1.setBannerRecyclerAdapter(bannerRecyclerAdapter);
        xfBanner2.setBannerRecyclerAdapter(bannerRecyclerAdapter);
        xfBanner3.setBannerRecyclerAdapter(bannerRecyclerAdapter);
        xfBanner4.setBannerRecyclerAdapter(bannerRecyclerAdapter);
        xfBanner5.setBannerRecyclerAdapter(bannerRecyclerAdapter);
        xfBanner.setOnBannerClickListener(this);
        xfBanner1.setOnBannerClickListener(this);
        xfBanner2.setOnBannerClickListener(this);
        xfBanner3.setOnBannerClickListener(this);
        xfBanner4.setOnBannerClickListener(this);
        xfBanner5.setOnBannerClickListener(this);
    }

    @Override
    public void onBannerItemClick(View view, int position, BannerViewModel viewModel) {
        Toast.makeText(BannerActivity.this, "position=" + position, Toast.LENGTH_LONG).show();
    }
}
