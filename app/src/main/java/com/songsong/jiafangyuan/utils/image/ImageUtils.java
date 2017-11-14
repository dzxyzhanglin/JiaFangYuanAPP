package com.songsong.jiafangyuan.utils.image;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.songsong.jiafangyuan.R;

/**
 * Created by zhanglin on 2017/9/28.
 */

public class ImageUtils {

    //设置图片加载策略
    public static final RequestOptions OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public static void loadImage(Context mContext, String image, AppCompatImageView imageView) {
        if (image != null && !"".equals(image)) {
            Glide.with(mContext)
                    .load(image)
                    .apply(OPTIONS)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.default_image);
        }
    }
}
