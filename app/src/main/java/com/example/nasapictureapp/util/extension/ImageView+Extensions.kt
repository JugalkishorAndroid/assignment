package com.example.nasapictureapp.util.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageFromUrl(imageUrl: String?) {
    this.context.let {
        Glide.with(it)
            .load(imageUrl)
            .into(this)
    }
}