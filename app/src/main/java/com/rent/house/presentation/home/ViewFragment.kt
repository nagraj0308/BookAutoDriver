package com.rent.house.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rent.house.data.remote.reqres.House
import com.rent.house.databinding.FragmentViewDetailsBinding
import com.rent.house.presentation.base.BaseFragment
import com.rent.house.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewFragment : BaseFragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentViewDetailsBinding? = null
    private val binding get() = _binding!!
    private var data: House? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (arguments != null) {
            data = requireArguments().getSerializable("item") as House?
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

            binding.tvName.text = buildString {
                append(": ")
                append(data!!.name)
            }
            binding.tvAddress.text = buildString {
                append(": ")
                append(data!!.address)
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

            binding.rcvImage.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            val images = ArrayList<String>()
            if (data!!.imageUrl1.isNotEmpty()) images.add(data!!.imageUrl1)
            if (data!!.imageUrl2.isNotEmpty()) images.add(data!!.imageUrl2)
            if (data!!.imageUrl3.isNotEmpty()) images.add(data!!.imageUrl3)
            if (data!!.imageUrl4.isNotEmpty()) images.add(data!!.imageUrl4)
            val adapter = ImageAdapter(images)
            binding.rcvImage.adapter = adapter
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