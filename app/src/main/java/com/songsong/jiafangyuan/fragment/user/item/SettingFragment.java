package com.songsong.jiafangyuan.fragment.user.item;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.app.AccountManager;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.data.entity.UserEntity;
import com.songsong.jiafangyuan.fragment.sign.LoginFragment;
import com.songsong.jiafangyuan.fragment.sign.SignHandler;
import com.songsong.jiafangyuan.listener.ISignListener;
import com.songsong.jiafangyuan.utils.image.ImageUtils;
import com.songsong.jiafangyuan.utils.storage.LattePreference;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhanglin on 2017/9/29.
 */

public class SettingFragment extends BaseBackFragment {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.toolbar_title)
    AppCompatTextView mTitle;

    @BindView(R2.id.cv_setting_avatar)
    CircleImageView mAvatar;
    @BindView(R2.id.tv_setting_nickname)
    AppCompatTextView mNickname;
    @BindView(R2.id.tv_setting_phone)
    AppCompatTextView mPhone;

    private ISignListener mISignListener = null;

    @OnClick(R2.id.btn_setting_update_password)
    void onUpdatePasswordClick() {
        if (AccountManager.isSignIn()) {
            getSupportDelegate().start(new UpdatePasswordFragment());
        } else {
            getSupportDelegate().start(new LoginFragment());
        }
    }

    @OnClick(R2.id.btn_setting_logout)
    void onLogoutClick() {
        pop();
        SignHandler.onLogout(mISignListener);
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
        return R.layout.fragment_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTitle.setText(R.string.setting);
        initToolbarNav(mToolbar);

        // 初始化用户信息
        initUserData();
    }

    private void initUserData() {
        final UserEntity profile = LattePreference.getUserInfo();
        mNickname.setText(profile.getNickName());
        mPhone.setText(profile.getPhone());
        if (profile.getAvatar() != null) {
            Glide.with(getContext())
                    .load(profile.getAvatar())
                    .apply(OPTIONS)
                    .into(mAvatar);
        } else {
            mAvatar.setImageResource(R.mipmap.img_user_default);
        }
    }
}
