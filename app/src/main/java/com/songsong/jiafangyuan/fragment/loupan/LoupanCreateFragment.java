package com.songsong.jiafangyuan.fragment.loupan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.app.Contstants;
import com.songsong.jiafangyuan.base.BaseBackFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglin on 2017/10/1.
 */

public class LoupanCreateFragment extends BaseBackFragment {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.toolbar_title)
    AppCompatTextView mTitle;
    @BindView(R2.id.et_loupan_weizhi)
    AppCompatTextView mWeizhi;

    @BindView(R2.id.loupan_step_1)
    LinearLayoutCompat mSetp1 = null;
    @BindView(R2.id.loupan_step_2)
    LinearLayoutCompat mSetp2 = null;
    @BindView(R2.id.loupan_step_3)
    LinearLayoutCompat mSetp3 = null;
    @BindView(R2.id.loupan_step_4)
    LinearLayoutCompat mSetp4 = null;

    private String province = "";
    private String city = "";
    private String district  = "";

    @OnClick(R2.id.btn_loupan_setp_next_2)
    void onShowNext2() {
        mSetp1.setVisibility(View.GONE);
        mSetp2.setVisibility(View.VISIBLE);
    }

    @OnClick(R2.id.btn_loupan_setp_prev_1)
    void onShowPrev1() {
        mSetp1.setVisibility(View.VISIBLE);
        mSetp2.setVisibility(View.GONE);
    }
    @OnClick(R2.id.btn_loupan_setp_next_3)
    void onShowNext3() {
        mSetp2.setVisibility(View.GONE);
        mSetp3.setVisibility(View.VISIBLE);
    }

    @OnClick(R2.id.btn_loupan_setp_prev_2)
    void onShowPrev2() {
        mSetp2.setVisibility(View.VISIBLE);
        mSetp3.setVisibility(View.GONE);
    }
    @OnClick(R2.id.btn_loupan_setp_next_4)
    void onShowNext4() {
        mSetp3.setVisibility(View.GONE);
        mSetp4.setVisibility(View.VISIBLE);
    }

    @OnClick(R2.id.btn_loupan_setp_prev_3)
    void onShowPrev3() {
        mSetp3.setVisibility(View.VISIBLE);
        mSetp4.setVisibility(View.GONE);
    }

    @OnClick(R2.id.et_loupan_weizhi)
    void onShowWeiZhi() {
        String weizhi = mWeizhi.getText().toString();
        showCitys(weizhi);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_loupan_create;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTitle.setText(R.string.to_loupan);
        initToolbarNav(mToolbar);
    }

    private void initWeizhi(String weizhi) {
        if (weizhi == null || "".equals(weizhi)) {
            province = Contstants.province;
            city = Contstants.city;
            district  = Contstants.district;
        } else {
            String[] arr = weizhi.split("-");
            province = arr[0];
            if (arr.length >= 2)
                city = arr[1];
            if (arr.length >= 3)
                district = arr[2];
        }
    }

    private void showCitys(String weizhi) {
        initWeizhi(weizhi);

        CityPickerView cityPicker = new CityPickerView.Builder(this.getActivity())
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#008dff")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#ffffff")
                .cancelTextColor("#ffffff")
                .province(province)
                .city(city)
                .district(district)
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //返回结果
                //ProvinceBean 省份信息
                //CityBean     城市信息
                //DistrictBean 区县信息
                mWeizhi.setText(province.getName() + "-"
                        + city.getName() + "-" + district.getName());
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
