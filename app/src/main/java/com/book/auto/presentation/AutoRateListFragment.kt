package com.book.auto.presentation;


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.auto.databinding.FragmentAutoRateListBinding;
import com.book.auto.presentation.base.BaseFragment
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
class AutoRateListFragment : BaseFragment() {
    private var _binding: FragmentAutoRateListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutoRateListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        context?.let { Glide.with(it).asBitmap().load("https://firebasestorage.googleapis.com/v0/b/bookvehicle-24571.appspot.com/o/BookAutoRateList%2Fnittrichy.jpeg?alt=media&token=223ea2b1-bfb0-44b7-80e5-3511340187e1").into(binding.ivPhoto) }
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