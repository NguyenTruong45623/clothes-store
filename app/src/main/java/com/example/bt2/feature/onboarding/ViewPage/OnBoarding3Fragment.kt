package com.example.bt2.feature.onboarding.ViewPage

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bt2.databinding.FragmentOnBoardingView3Binding

class OnBoarding3Fragment : Fragment(){

    private var _binding : FragmentOnBoardingView3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingView3Binding.inflate(inflater, container, false)

        binding.tvTitle3.text = Html.fromHtml("<font color=${Color.parseColor("#795548")}>Swift</font> <font color=${Color.BLACK}> and </font> <font color=${Color.parseColor("#795548")}>Reliable</font>" +
                "<font color=${Color.BLACK}> Delivery</font>")

        return  binding.root
    }

}