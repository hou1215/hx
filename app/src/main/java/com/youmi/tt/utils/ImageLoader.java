package com.youmi.tt.utils;

import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

/**
 * @Author dzl on 2016/10/31.
 */
public class ImageLoader {

    public static void loadImage(ImageView imageView, String url, int... res){
        /*Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .crossFade()
                .placeholder(res)
                .into(imageView);*/
        DrawableRequestBuilder<String>  builder = Glide.with(imageView.getContext())

                .load(url)
                .centerCrop()
                .crossFade();
                if (res.length>0) {
                    builder.placeholder(res[0]);
                }
                builder.into(imageView);
    }

}
