package com.edf.androidtestedf.app.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

object PictureUtils {
    fun loadPicture(context: Context, url: String, view: ImageView) {
        val glideUrl = GlideUrl(url, LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build()
        )
        Glide.with(context)
            .load(glideUrl)
            .into(view)
    }
}