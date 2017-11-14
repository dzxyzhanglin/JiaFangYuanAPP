package com.songsong.jiafangyuan.fragment.maifang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.base.BaseMainFragment;
import com.songsong.jiafangyuan.utils.dialog.SpecialTipDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglin on 2017/9/23.
 */

public class MaiFangTabFragment extends BaseMainFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //@BindView(R2.id.rv_fang_search)
    // RecyclerView mRecyclerView = null;


    public static MaiFangTabFragment newInstance() {
        Bundle args = new Bundle();
        MaiFangTabFragment fragment = new MaiFangTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R2.id.btn_maifang_search_filter)
    void onConfirmFilterClick() {
        getParentMainFragment().getSupportDelegate().start(new MaifangListFragment());
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_maifang_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.maifang_search);
        mToolbar.inflateMenu(R.menu.search_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search_confirm:
                        getParentMainFragment().getSupportDelegate().start(new MaifangListFragment());
                        break;
                    default:
                }
                return true;
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        // 版本1，选择按钮， 因为客户不喜欢这种，屏蔽掉
        //initData();

        // 显示特别提示
        SpecialTipDialog.create(this.getActivity()).showSpecialTipDialog();
    }

    /* 版本1数据
    private void initData() {
        RestClient.builder()
                .url("api/maifang_search2.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
                        mRecyclerView.setLayoutManager(manager);

                        final List<MultipleItemEntity> data = new FangSearchDataConverter()
                                .setJsonData(response)
                                .convert();
                        final FangSearchAdapter adapter = new FangSearchAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }*/
}
