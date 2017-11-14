package com.songsong.jiafangyuan.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.app.AccountManager;
import com.songsong.jiafangyuan.base.BaseMainFragment;
import com.songsong.jiafangyuan.fragment.fuwu.FuWuFragment;
import com.songsong.jiafangyuan.fragment.loupan.LoupanFragment;
import com.songsong.jiafangyuan.fragment.maifang.MaiFangCreateFragment;
import com.songsong.jiafangyuan.fragment.maifang.MaiFangTabFragment;
import com.songsong.jiafangyuan.fragment.maifang.MaifangListFragment;
import com.songsong.jiafangyuan.fragment.sign.LoginFragment;
import com.songsong.jiafangyuan.fragment.zufang.ZuFangCreateFragment;
import com.songsong.jiafangyuan.fragment.zufang.ZuFangListFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglin on 2017/9/23.
 */

public class HomeTabFragment extends BaseMainFragment {

    @BindView(R2.id.fl_container)
    LinearLayoutCompat mNestedScrollView;
/*    @BindView(R2.id.list_index_zufang)
    RecyclerView mZuFangRecyclerView = null;
    @BindView(R2.id.list_index_maifang)
    RecyclerView mMaiFangRecyclerView = null;*/

    public static HomeTabFragment newInstance() {
        Bundle args = new Bundle();
        HomeTabFragment fragment = new HomeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R2.id.img_index_maifang)
    void onMaiFangClick() {
        getParentMainFragment().getmBottomBar().setCurrentItem(1);
    }

    // 卖房
    @OnClick(R2.id.img_index_mf)
    void onMfClick() {
        if (AccountManager.isSignIn()) {
            getParentMainFragment().start(new MaiFangCreateFragment());
        } else {
            getParentMainFragment().start(new LoginFragment());
        }
    }

/*    @OnClick(R2.id.tv_index_mf)
    void onMfClick2() {
        if (AccountManager.isSignIn()) {
            getParentMainFragment().start(new MaiFangCreateFragment());
        } else {
            getParentMainFragment().start(new LoginFragment());
        }
    }*/

    @OnClick(R2.id.img_index_zufang)
    void onZuFangClick() {
        getParentMainFragment().getmBottomBar().setCurrentItem(2);
    }

    // 出租
    @OnClick(R2.id.img_index_cz)
    void onCzClick() {
        if (AccountManager.isSignIn()) {
            getParentMainFragment().start(new ZuFangCreateFragment());
        } else {
            getParentMainFragment().start(new LoginFragment());
        }
    }

/*    @OnClick(R2.id.tv_index_cu)
    void onCzClick2() {
        if (AccountManager.isSignIn()) {
            getParentMainFragment().start(new ZuFangCreateFragment());
        } else {
            getParentMainFragment().start(new LoginFragment());
        }
    }*/

    @OnClick(R2.id.img_index_loupantuijian)
    void onLoupantuijianClick() {
        getParentMainFragment().start(new LoupanFragment());
    }

    @OnClick(R.id.img_index_maimaifuwu)
    void onFuWuClick() {
        getParentMainFragment().start(new FuWuFragment());
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        //initData();
    }

   /* private void initData() {
        RestClient.builder()
                .url("index.php?m=api&c=Index&a=index")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject obj = JSON.parseObject(response);
                        initZufang(obj.getJSONArray("zu_data"));
                        initMaifang(obj.getJSONArray("sell_data"));
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "请求数据失败", Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();
    }*/

   /* private void initZufang(JSONArray zufangData) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mZuFangRecyclerView.setLayoutManager(manager);

        final ArrayList<MultipleItemEntity> data =
                new FangDataConverter()
                        .convert(zufangData);
        final FangAdapter mAdapter1 = new FangAdapter(data);
        mZuFangRecyclerView.setAdapter(mAdapter1);
        mZuFangRecyclerView.addOnItemTouchListener(
                ZuFangItemClickListener.create(getParentMainFragment()));
    }*/

   /* private void initMaifang(JSONArray maifangData) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mMaiFangRecyclerView.setLayoutManager(manager);

        final ArrayList<MultipleItemEntity> data =
                new FangDataConverter()
                        .convert(maifangData);
        final FangAdapter mAdapter2 = new FangAdapter(data);
        mMaiFangRecyclerView.setAdapter(mAdapter2);
        mMaiFangRecyclerView.addOnItemTouchListener(
                ZuFangItemClickListener.create(getParentMainFragment()));
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
