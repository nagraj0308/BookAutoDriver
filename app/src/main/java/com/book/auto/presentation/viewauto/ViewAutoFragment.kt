package com.book.auto.presentation.viewauto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.book.auto.data.remote.reqres.Auto
import com.book.auto.databinding.FragmentViewDetailsBinding
import com.book.auto.presentation.base.BaseFragment
import com.book.auto.presentation.home.HomeViewModel
import com.book.auto.utils.Utils
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAutoFragment : BaseFragment() {
    private val viewModel: HomeViewModel by activityViewModels()
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
                    viewModel.incAutoCallCount(data!!._id)
                    context?.startActivity(i)
                } catch (_: Exception) {

                }
            }
            binding.tvNumber.text = buildString {
                append(": ")
                append(data!!.number)
            }
            binding.tvDriverName.text = buildString {
                append(": ")
                append(data!!.driver)
            }
            binding.tvType.text = buildString {
                append(": ")
                append(data!!.type)
            }
            binding.tvDistance.text = buildString {
                append(": ")
                append(
                    Utils.distance(
                        data!!.lat, data!!.lon, viewModel.lat.value!!, viewModel.lon.value!!
                    ).toString()
                )
                append(" KM")
            }

            context?.let { Glide.with(it).asBitmap().load(data?.imageUrl).into(binding.ivPhoto) }
        }

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