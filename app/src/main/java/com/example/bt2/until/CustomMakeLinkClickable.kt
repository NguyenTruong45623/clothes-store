package com.example.bt2.until

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

fun makeLinkClickable(textView: TextView, clickableText: String, color: Int) {
    val fullText = textView.text.toString()
    val ss = SpannableString(fullText)

    val clickableSpan = object : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = color
            ds.isUnderlineText = true
        }

        override fun onClick(widget: View) {

        }
    }

    val startIndex = fullText.indexOf(clickableText)
    val endIndex = startIndex + clickableText.length
    ss.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    textView.text = ss
    textView.movementMethod = LinkMovementMethod.getInstance()
    textView.highlightColor = Color.TRANSPARENT
}