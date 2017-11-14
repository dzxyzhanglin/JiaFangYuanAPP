package com.songsong.jiafangyuan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.songsong.jiafangyuan.app.Contstants;
import com.songsong.jiafangyuan.fragment.MainFragment;
import com.songsong.jiafangyuan.fragment.user.UserFangTabFragment;
import com.songsong.jiafangyuan.listener.ISignListener;
import com.songsong.jiafangyuan.utils.autoupdate.UpdateUtils;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends SupportActivity implements ISignListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }

        // 检查更新
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (MainActivity.this.checkSelfPermission(permission)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions( new String[]{permission},
                        Contstants.UPDATE_VERSION);
            } else {
                UpdateUtils.create(this).checkVersion();
            }
        } else {
            UpdateUtils.create(this).checkVersion();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Contstants.UPDATE_VERSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 通过
                UpdateUtils.create(this).checkVersion();
            } else { // 不通过
                Toast.makeText(MainActivity.this, "拒绝权限", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onLoginSuccess() {
        updateUserUI();
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginFailure(String msg) {
        Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogout() {
        updateUserUI();
        Toast.makeText(this, "退出成功", Toast.LENGTH_LONG).show();
    }

    private void updateUserUI() {
        MainFragment mainFragment = findFragment(MainFragment.class);
        if (mainFragment == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        } else {
            UserFangTabFragment userFragment =
                    mainFragment.findChildFragment(UserFangTabFragment.class);
            userFragment.initUserInfo();

            mainFragment.showHideFragment(userFragment);
        }
    }
}
