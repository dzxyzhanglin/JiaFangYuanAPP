package com.songsong.jiafangyuan.fragment.fuwu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.R2;
import com.songsong.jiafangyuan.app.ConfigKeys;
import com.songsong.jiafangyuan.app.Contstants;
import com.songsong.jiafangyuan.app.Latte;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.data.entity.UserEntity;
import com.songsong.jiafangyuan.utils.image.GlideLoader;
import com.songsong.jiafangyuan.utils.image.ImageUtils;
import com.songsong.jiafangyuan.utils.log.LatteLogger;
import com.songsong.jiafangyuan.utils.net.RestClient;
import com.songsong.jiafangyuan.utils.net.callback.IError;
import com.songsong.jiafangyuan.utils.net.callback.IFailure;
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;
import com.songsong.jiafangyuan.utils.storage.LattePreference;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhanglin on 2017/10/1.
 */
public class FuWuCreateFragment extends BaseBackFragment {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.toolbar_title)
    AppCompatTextView mTitle;

    @BindView(R2.id.cv_service_add_avatar)
    CircleImageView mAvatar;
    @BindView(R2.id.tv_service_add_nickname)
    AppCompatTextView mNickname;
    @BindView(R2.id.tv_service_add_phone)
    AppCompatTextView mPhone;

    @BindView(R2.id.et_service_add_name)
    AppCompatEditText mEtName;
    @BindView(R2.id.et_service_add_phone)
    AppCompatEditText mEtPhone;
    /*@BindView(R2.id.et_service_add_project)
    AppCompatEditText mEtProject;*/
    @BindView(R2.id.et_service_add_address)
    AppCompatEditText mEtAddress;
    @BindView(R2.id.iv_service_add_card)
    AppCompatImageView mIvCard;
    @BindView(R2.id.tv_card_url)
    AppCompatTextView mCardUrl;

    private ImageConfig imageConfig;

    /**
     * 上传个人证照
     * 只上传一张图片
     */
    @OnClick(R2.id.iv_service_add_card_btn)
    void uploadCard() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (_mActivity.checkSelfPermission(permission)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions( new String[]{permission},
                        Contstants.APPLY_PERMISSION_RESULT_CODE);
            } else {
                showCamera();
            }
        } else {
            showCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Contstants.APPLY_PERMISSION_RESULT_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 通过
                showCamera();
            } else { // 不通过
                Toast.makeText(getContext(), "拒绝权限", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void showCamera() {
        imageConfig = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimaryDark))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                //.showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                //.filePath("/fang")
                //.setContainer(ivContainer)
                .requestCode(Contstants.IMAGE_RESULT_CODE)
                .build();
        ImageSelector.open(FuWuCreateFragment.this, imageConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Contstants.IMAGE_RESULT_CODE && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            handleImageSelect(pathList);
        }
    }

    /**
     * 图片选择后回调处理
     */
    void handleImageSelect(List<String> pathList) {
        for (String path : pathList) {
            RestClient.builder()
                    .url("api/upload.php")
                    .loader(getContext())
                    .file(new File(path))
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            final JSONObject obj = JSON.parseObject(response);
                            final int code = obj.getInteger("code");
                            if (code == 1) {
                                final String imageurl = obj.getString("imageurl");
                                mCardUrl.setText(imageurl);

                                final String httpImageUrl = Latte.getConfiguration(ConfigKeys.API_HOST)
                                        + imageurl.replace("../", "");
                                LatteLogger.d("httpImageUrl = " + httpImageUrl);
                                ImageUtils.loadImage(getContext(), httpImageUrl, mIvCard);
                            } else {
                                Toast.makeText(getContext(), "上传失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(), "上传失败", Toast.LENGTH_LONG).show();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(getContext(), "服务器出错", Toast.LENGTH_LONG).show();
                        }
                    })
                    .build()
                    .upload();
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_fuwu_create;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTitle.setText(R.string.fuwu_create);
        initToolbarNav(mToolbar);
        // 初始化用户信息
        initUserInfo();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 初始化用户已保存信息
        initData();
    }

    private void initUserInfo() {
        final UserEntity profile = LattePreference.getUserInfo();
        mNickname.setText(profile.getNickName());
        mPhone.setText(profile.getPhone());
        if (profile.getAvatar() != null) {
            //ImageUtils.loadImage(getContext(), profile.getAvatar(), mAvatar);
            Glide.with(getContext())
                    .load(profile.getAvatar())
                    .apply(OPTIONS)
                    .into(mAvatar);
        } else {
            mAvatar.setImageResource(R.mipmap.img_user_default);
        }
    }

    private void initData() {
        RestClient.builder()
                .loader(getContext())
                .url("api/service_info.php")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject obj = JSON.parseObject(response);
                        final String name = obj.getString("name");
                        final String phone = obj.getString("phone");
                        final String project = obj.getString("project");
                        final String address = obj.getString("address");
                        final String photo = obj.getString("photo");
                        final String photoUrl = obj.getString("photo_url");
                        if (name != null && !"".equals(name)) {
                            mEtName.setText(name);
                        }
                        if (phone != null && !"".equals(phone)) {
                            mEtPhone.setText(phone);
                        }
                       /* if (project != null && !"".equals(project)) {
                            mEtProject.setText(project);
                        }*/
                        if (address != null && !"".equals(address)) {
                            mEtAddress.setText(address);
                        }
                        if (photo != null && !"".equals(photo)) {
                            mIvCard.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(getContext(), photo, mIvCard);
                        } else {
                            mIvCard.setVisibility(View.GONE);
                        }
                        if (photoUrl != null && !"".equals(photoUrl)) {
                            mCardUrl.setText(photoUrl);
                        }
                    }
                })
                .build()
                .get();
    }
}
