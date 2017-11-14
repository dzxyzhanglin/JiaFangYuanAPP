package com.songsong.jiafangyuan.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.app.AccountManager;
import com.songsong.jiafangyuan.base.BaseMainFragment;
import com.songsong.jiafangyuan.data.adapter.UserOperateAdapter;
import com.songsong.jiafangyuan.data.bean.ListUserBean;
import com.songsong.jiafangyuan.data.entity.UserEntity;
import com.songsong.jiafangyuan.fragment.sign.LoginFragment;
import com.songsong.jiafangyuan.fragment.user.item.DemandFragment;
import com.songsong.jiafangyuan.fragment.user.item.PublishFragment;
import com.songsong.jiafangyuan.fragment.user.item.SettingFragment;
import com.songsong.jiafangyuan.ui.recycler.ItemType;
import com.songsong.jiafangyuan.utils.storage.LattePreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhanglin on 2017/9/23.
 */

public class UserFangTabFragment extends BaseMainFragment {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    @BindView(R2.id.rv_user_operate)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.tv_user_login)
    LinearLayoutCompat mUserLogin = null;
    @BindView(R2.id.iv_user_avatar)
    CircleImageView mAvatar = null;
    @BindView(R2.id.tv_user_nickname)
    AppCompatTextView mNickname = null;
    @BindView(R2.id.tv_user_phone)
    AppCompatTextView mPhone = null;

    public static UserFangTabFragment newInstance() {
        Bundle args = new Bundle();
        UserFangTabFragment fragment = new UserFangTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R2.id.tv_user_login)
    void onLoginClick() {
        getParentMainFragment().start(new LoginFragment());
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 初始可操作列
        initOperateList();
        // 初始化用户信息
        initUserInfo();
    }

    private void initOperateList() {
        // 设置个人中心显示操作项
        final ListUserBean d1 = new ListUserBean.Builder()
                .setItemType(ItemType.NORMAL)
                .setId(0)
                .setDelegate(new PublishFragment())
                .setIcon(R.mipmap.img_user_fabuxuqiu)
                .setTitle("发布需求")
                .build();
        final ListUserBean d2 = new ListUserBean.Builder()
                .setItemType(ItemType.NORMAL)
                .setId(1)
                .setDelegate(new DemandFragment())
                .setIcon(R.mipmap.img_user_edit)
                .setTitle("修改编辑")
                .build();
        final ListUserBean d3 = new ListUserBean.Builder()
                .setItemType(ItemType.NORMAL)
                .setId(2)
                .setDelegate(new SettingFragment())
                .setIcon(R.mipmap.img_user_setting)
                .setTitle("设置")
                .build();
        List<ListUserBean> data = new ArrayList<>();
        data.add(d1);
        data.add(d2);
        data.add(d3);

        // 设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final UserOperateAdapter adapter = new UserOperateAdapter(data, this);
        mRecyclerView.setAdapter(adapter);
    }

    public void initUserInfo() {
        if (AccountManager.isSignIn()) {
            mUserLogin.setVisibility(View.GONE);
            mAvatar.setVisibility(View.VISIBLE);
            mNickname.setVisibility(View.VISIBLE);
            mPhone.setVisibility(View.VISIBLE);
            initLoginData();
        } else {
            mUserLogin.setVisibility(View.VISIBLE);
            mAvatar.setVisibility(View.GONE);
            mNickname.setVisibility(View.GONE);
            mPhone.setVisibility(View.GONE);
        }
    }

    private void initLoginData() {
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
