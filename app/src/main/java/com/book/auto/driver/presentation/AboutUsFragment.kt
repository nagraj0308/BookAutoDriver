package com.book.auto.driver.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.book.auto.driver.databinding.FragmentWebViewBinding
import com.book.auto.driver.utils.Constants


class AboutUsFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.wvContent.webViewClient = WebViewClient()
        binding.wvContent.loadUrl(Constants.ABOUT_US_URL)
        binding.wvContent.settings.javaScriptEnabled = true
        binding.wvContent.settings.setSupportZoom(true)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}