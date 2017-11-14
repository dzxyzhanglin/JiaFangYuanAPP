package com.songsong.jiafangyuan.fragment.sign;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.listener.ISignListener;
import com.songsong.jiafangyuan.utils.net.RestClient;
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglin on 2017/8/23.
 */

public class LoginFragment extends BaseBackFragment {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.toolbar_title)
    AppCompatTextView mTitle;

    private ISignListener mISignListener = null;

    @OnClick(R2.id.btn_login_login)
    void onLoginClick() {
        RestClient.builder()
                .url("api/login.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        pop();
                        SignHandler.onLoginSuccess(response, mISignListener);
                    }
                })
                .build()
                .post();
    }

    @OnClick(R2.id.btn_login_register)
    void onRegisterClick() {
        getSupportDelegate().start(new RegisterFragment());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISignListener) {
            mISignListener = (ISignListener) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 初始化返回按钮
        mTitle.setText(R.string.login);
        initToolbarNav(mToolbar, true);
    }
}
