package com.srit.market.helpers

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("android:srcBitmap")
fun loadImage(iv: ImageView, bitmap: Bitmap?) {
    iv.setImageBitmap(bitmap)
}

@BindingAdapter("android:srcUrl")
fun loadImageUrl(iv: ImageView, url: String) {
    Glide
        .with(iv.context)
        .load(url)
        .into(iv)
}