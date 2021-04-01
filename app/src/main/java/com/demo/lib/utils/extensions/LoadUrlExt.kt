package com.demo.lib.utils.extensions

import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.demo.tinder.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun AppCompatImageView.loadUrl(url: String) {

    val oldScaleType = this.scaleType

    this.scaleType = ImageView.ScaleType.CENTER

    Picasso.get()
            .load(url)
            .fit()
            .placeholder(R.drawable.baseline_photo_size_select_actual_black_24dp)
            .into(this, object : Callback {
                override fun onSuccess() {
                    this@loadUrl.scaleType = oldScaleType
                }

                override fun onError(e: Exception?) {
                    Log.e("User", e.toString())
                }
            })
}