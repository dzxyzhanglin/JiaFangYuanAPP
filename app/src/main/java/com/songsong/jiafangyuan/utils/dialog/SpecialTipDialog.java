package com.songsong.jiafangyuan.utils.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by zhanglin on 2017/11/5.
 */

/**
 * 买房、租房时的提示信息
 */
public class SpecialTipDialog {
    private static Activity mActivity;
    private static SpecialTipDialog dialog = null;

    public static SpecialTipDialog create(Activity activity) {
        mActivity = activity;
        if (dialog != null) {
            return dialog;
        }

        return new SpecialTipDialog();
    }

    public void showSpecialTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("特别提示");
        builder.setMessage("1，请在约定的看房时间内及时联系看房；\n" +
                "2，交易时请核实对方相关证件。\n" );
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
