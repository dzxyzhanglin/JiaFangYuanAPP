package com.songsong.jiafangyuan.data.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.app.AccountManager;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.base.BaseMainFragment;
import com.songsong.jiafangyuan.data.bean.ListUserBean;
import com.songsong.jiafangyuan.fragment.sign.LoginFragment;
import com.songsong.jiafangyuan.fragment.user.UserFangTabFragment;
import com.songsong.jiafangyuan.ui.recycler.ItemType;

import java.util.List;

/**
 * Created by zhanglin on 2017/8/25.
 */

public class UserOperateAdapter extends BaseMultiItemQuickAdapter<ListUserBean, BaseViewHolder> {
    private final UserFangTabFragment DELEGATE;

    public UserOperateAdapter(List<ListUserBean> data, UserFangTabFragment delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ItemType.NORMAL, R.layout.item_user_operate);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListUserBean item) {
        // 数据
        final BaseBackFragment delegate = item.getDelegate();
        final String title = item.getTitle();
        final int icon = item.getIcon();
        // 控件
        AppCompatTextView tvTitle = helper.getView(R.id.tv_user_title);
        AppCompatImageView ivIcon = helper.getView(R.id.iv_user_icon);
        LinearLayoutCompat listCompat = helper.getView(R.id.list_user_operate);

        tvTitle.setText(title);
        ivIcon.setImageResource(icon);
        listCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccountManager.isSignIn()) {
                    DELEGATE.getParentMainFragment().start(delegate);
                } else { // 跳转到登录界面
                    DELEGATE.getParentMainFragment().start(new LoginFragment());
                }
            }
        });
    }
}
