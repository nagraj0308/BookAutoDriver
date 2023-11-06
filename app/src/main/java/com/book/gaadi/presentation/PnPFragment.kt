package com.book.gaadi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.book.admin.databinding.FragmentWebViewBinding
import com.book.auto.driver.utils.Constants


class PnPFragment : Fragment() {

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
        binding.wvContent.loadUrl(Constants.PNP_URL)
        binding.wvContent.settings.javaScriptEnabled = true
        binding.wvContent.settings.setSupportZoom(true)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}