package com.songsong.jiafangyuan.fragment.user.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseBackFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglin on 2017/9/29.
 */

public class UpdatePasswordFragment extends BaseBackFragment {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @OnClick(R2.id.btn_update_password_confirm)
    void onUpdatePasswordClick() {
        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_update_password;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mToolbar.setTitle(R.string.update_password);
        initToolbarNav(mToolbar);
    }
}
