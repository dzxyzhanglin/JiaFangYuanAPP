package com.songsong.jiafangyuan.fragment.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.utils.net.RestClient;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglin on 2017/9/29.
 */

public class RegisterFragment extends BaseBackFragment {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @OnClick(R2.id.tv_register_agreement)
    void onAgreementClick() {
        getSupportDelegate().start(new AgreementFragment());
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mToolbar.setTitle(R.string.register);
        initToolbarNav(mToolbar);

    }
}
