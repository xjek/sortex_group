package com.eugene.softexgroup.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.eugene.softexgroup.R
import com.squareup.picasso.Picasso

/**
 * Параметр для layout загрузки картинки
 */
@BindingAdapter(value = ["app:image"])
fun loadImage(imageView: ImageView, image: String?) {
    if (image != null)
        Picasso.get().load(image).fit().error(R.drawable.ic_empty_image).centerCrop().into(imageView)
}