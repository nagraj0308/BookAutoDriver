package com.bluetooth.printer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.bluetooth.printer.databinding.FragmentWebViewBinding
import com.bluetooth.printer.view.utils.Constants
import com.google.android.gms.ads.AdRequest


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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.bannerAd.loadAd(adRequest)
    }
}