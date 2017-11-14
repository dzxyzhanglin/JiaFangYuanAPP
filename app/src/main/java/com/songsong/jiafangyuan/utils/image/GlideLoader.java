package com.songsong.jiafangyuan.utils.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jaiky.imagespickers.ImageLoader;

/**
 * Created by zhanglin on 2017/10/5.
 */

public class GlideLoader implements ImageLoader {
    //设置图片加载策略
    public static final RequestOptions OPTIONS =
            new RequestOptions()
                    .placeholder(com.jaiky.imagespickers.R.drawable.global_img_default)
                    .centerCrop();

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(OPTIONS)
                .into(imageView);
    }
}
