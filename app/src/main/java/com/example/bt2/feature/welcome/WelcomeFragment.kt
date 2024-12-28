package com.example.bt2.feature.welcome

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        binding.viewModel = viewModel
        binding.fm = parentFragmentManager

        makeLinkClickable(binding.tvSignIn, "Sign In", Color.BLUE)
        binding.tvTitleTheFashionApp.text = Html.fromHtml("<font color=${Color.BLACK}>The </font> <font color=${Color.parseColor("#795548")}>Fashion App</font><font color=${Color.BLACK}> That\n Makes You Look Your Best </font>")


//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.navigateTo.collect { destinationId ->
//                findNavController().navigate(destinationId)
//            }
//        }

        return binding.root
    }

    private fun makeLinkClickable(textView: TextView, clickableText: String, color: Int) {
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


}