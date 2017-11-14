package com.songsong.jiafangyuan.utils.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songsong.jiafangyuan.R;
import com.songsong.jiafangyuan.app.ConfigKeys;
import com.songsong.jiafangyuan.app.Latte;
import com.songsong.jiafangyuan.base.BaseBackFragment;
import com.songsong.jiafangyuan.ui.loader.LatteLoader;
import com.songsong.jiafangyuan.utils.log.LatteLogger;
import com.songsong.jiafangyuan.utils.net.RestClient;
import com.songsong.jiafangyuan.utils.net.callback.IError;
import com.songsong.jiafangyuan.utils.net.callback.IFailure;
import com.songsong.jiafangyuan.utils.net.callback.ISuccess;
import com.songsong.jiafangyuan.wxapi.LatteWeChat;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by zhanglin on 2017/10/22.
 */

public class AppPay implements View.OnClickListener {
    private Activity mActivity = null;
    private AlertDialog mDialog = null;

    private AppPay(BaseBackFragment fragment) {
        this.mActivity = fragment.getActivity();
        this.mDialog = new AlertDialog.Builder(fragment.getContext()).create();
    }

    public static AppPay create(BaseBackFragment fragment) {
        return new AppPay(fragment);
    }

    public void beginPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.iv_pay_alipay).setOnClickListener(this);
            window.findViewById(R.id.iv_pay_weichact).setOnClickListener(this);
            window.findViewById(R.id.iv_pay_cancel).setOnClickListener(this);
        }
    }

    /**
     * 微信支付
     */
    private void weChatPay() {
        LatteLoader.stopLoading();
        final String weChatPrePayUrl = "api/wxpay.php";

        final String appId = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
        final IWXAPI iwxapi = WXAPIFactory.createWXAPI(mActivity, appId, true);
        iwxapi.registerApp(appId);
        LatteLogger.d("iwxapi = " + iwxapi);
        RestClient.builder()
                .url(weChatPrePayUrl)
                .loader(mActivity)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LatteLogger.d("response = " + response);
                        final JSONObject result =
                                JSON.parseObject(response).getJSONObject("result");
                        final String prepayId = result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        LatteLogger.d("sign = " + paySign);

                        final PayReq payReq = new PayReq();
                        payReq.appId = appId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        boolean req = iwxapi.sendReq(payReq);
                        if (!req)
                            Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(mActivity, "请求出错:" + msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(mActivity, "请求出错", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_pay_alipay) {
            Toast.makeText(mActivity, "支付宝签约中", Toast.LENGTH_LONG).show();
            mDialog.cancel();
        } else if (id == R.id.iv_pay_weichact) {
            weChatPay();
            mDialog.cancel();
        } else if (id == R.id.iv_pay_cancel) {
            mDialog.cancel();
        }
    }
}
