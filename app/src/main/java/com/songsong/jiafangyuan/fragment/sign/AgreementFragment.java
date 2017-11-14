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
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;

import butterknife.BindView;

/**
 * Created by zhanglin on 2017/9/29.
 */

public class AgreementFragment extends BaseBackFragment {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.tv_agreement_content)
    AppCompatTextView mContent = null;

    @Override
    public Object setLayout() {
        return R.layout.fragment_agreement;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mToolbar.setTitle(R.string.agreement);
        initToolbarNav(mToolbar);


        // 请求服务条款内容
        RestClient.builder()
                .url("api/service_agreement.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject obj = JSON.parseObject(response);
                        final String data = obj.getString("data");
                        mContent.setText(
                                Html.fromHtml(data));
                    }
                })
                .build()
                .get();
    }
}
