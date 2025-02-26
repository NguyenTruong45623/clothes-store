package com.example.bt2.until

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.bt2.R

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}

@BindingAdapter("localImageUri") // Đặt tên khác để phân biệt
fun loadLocalImage(view: ImageView, uri: String?) {
    if (!uri.isNullOrEmpty()) {
        try {
            val localUri = Uri.parse(uri) // Chuyển đổi String thành Uri
            Glide.with(view.context)
                .load(localUri) // Load từ Uri
                .into(view)
        } catch (e: Exception) {
            // Xử lý lỗi nếu URI không hợp lệ
            e.printStackTrace()
        }
    }
}

@BindingAdapter("favoriteStatus")
fun ImageView.setFavoriteColor(isFavorite: Boolean) {
    val color = if (isFavorite) Color.RED else Color.GRAY
    this.setColorFilter(color)
}

@BindingAdapter("android:text")
fun setSpinnerText(spinner: Spinner, text: String?) {
    text?.let {
        val adapter = spinner.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i).toString() == it) {
                spinner.setSelection(i)
                break
            }
        }
    }
}

@InverseBindingAdapter(attribute = "android:text")
fun getSpinnerText(spinner: Spinner): String? {
    return spinner.selectedItem?.toString()
}

@BindingAdapter("android:textAttrChanged")
fun setSpinnerTextChangedListener(spinner: Spinner, listener: InverseBindingListener?) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener?.onChange()
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}
