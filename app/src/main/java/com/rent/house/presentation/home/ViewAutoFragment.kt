package com.rent.house.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.rent.house.data.remote.reqres.Vehicle
import com.rent.house.databinding.FragmentViewDetailsBinding
import com.rent.house.presentation.base.BaseFragment
import com.rent.house.utils.Constants
import com.rent.house.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAutoFragment : BaseFragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentViewDetailsBinding? = null
    private val binding get() = _binding!!
    private var data: Vehicle? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (arguments != null) {
            data = requireArguments().getSerializable("item") as Vehicle?
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
                append(Constants.vehicleTypes[data!!.typeId])
            }
            binding.tvDistance.text = buildString {
                append(": ")
                append(
                    Utils.distance(
                        data!!.lat,
                        data!!.lon,
                        viewModel.lat.value!!,
                        viewModel.lon.value!!
                    ).toString()
                )
                append(" KM")
            }

            binding.tvRate.text = buildString {
                append(": ")
                append(data!!.rate)
            }

            context?.let { Glide.with(it).asBitmap().load(data?.imageUrl).into(binding.ivPhoto) }
        }

        return root
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}