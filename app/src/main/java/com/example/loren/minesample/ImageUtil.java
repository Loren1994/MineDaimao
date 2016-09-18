package com.example.loren.minesample;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

public class ImageUtil {

    static void bind(@NotNull ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .crossFade()
                .placeholder(ContextCompat.getDrawable(imageView.getContext(), R.mipmap.ic_launcher))
                .into(imageView);
    }
}
