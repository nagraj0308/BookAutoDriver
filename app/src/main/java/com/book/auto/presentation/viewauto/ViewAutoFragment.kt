package com.book.auto.presentation.viewauto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.book.auto.data.remote.reqres.Auto
import com.book.auto.databinding.FragmentViewDetailsBinding
import com.book.auto.presentation.base.BaseFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAutoFragment : BaseFragment() {

    private var _binding: FragmentViewDetailsBinding? = null
    private val binding get() = _binding!!
    private var data: Auto? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (arguments != null) {
            data = requireArguments().getSerializable("item") as Auto?
        }
        if (data != null) {
            binding.btnCall.setOnClickListener {
                val u = Uri.parse("tel:" + data!!.mobileNo)
                val i = Intent(Intent.ACTION_DIAL, u)
                try {
                    context?.startActivity(i)
                } catch (_: Exception) {

                }
            }
            binding.tvNumber.text = ": " + data!!.number
            binding.tvDriverName.text = ": " + data!!.driver
            binding.tvType.text = ": " + data!!.type

            context?.let { Glide.with(it).asBitmap().load(data?.imageUrl).into(binding.ivPhoto) }
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}