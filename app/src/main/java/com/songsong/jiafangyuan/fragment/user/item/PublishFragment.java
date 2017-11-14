package com.songsong.jiafangyuan.fragment.user.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.app.AccountManager;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.fragment.fuwu.FuWuCreateFragment;
import com.songsong.jiafangyuan.fragment.loupan.LoupanCreateFragment;
import com.songsong.jiafangyuan.fragment.maifang.MaiFangCreateFragment;
import com.songsong.jiafangyuan.fragment.sign.LoginFragment;
import com.songsong.jiafangyuan.fragment.zufang.ZuFangCreateFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglin on 2017/9/28.
 */

public class PublishFragment extends BaseBackFragment {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.toolbar_title)
    AppCompatTextView mTitle;

    @OnClick(R2.id.iv_publish_fuwu)
    void onFuWuAddClick() {
        if (AccountManager.isSignIn()) {
            getSupportDelegate().start(new FuWuCreateFragment());
        } else {
            getSupportDelegate().start(new LoginFragment());
        }
    }

    @OnClick(R2.id.iv_publish_maifang)
    void onMaiFangAddClick() {
        if (AccountManager.isSignIn()) {
            getSupportDelegate().start(new MaiFangCreateFragment());
        } else {
            getSupportDelegate().start(new LoginFragment());
        }
    }

    @OnClick(R2.id.iv_publish_loupan)
    void onLoupanAddClick() {
        if (AccountManager.isSignIn()) {
            getSupportDelegate().start(new LoupanCreateFragment());
        } else {
            getSupportDelegate().start(new LoginFragment());
        }
    }

    @OnClick(R2.id.iv_publish_chuzu)
    void onZuFangAddClick() {
        if (AccountManager.isSignIn()) {
            getSupportDelegate().start(new ZuFangCreateFragment());
        } else {
            getSupportDelegate().start(new LoginFragment());
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_publish;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTitle.setText(R.string.to_publish);
        initToolbarNav(mToolbar);
    }
}
