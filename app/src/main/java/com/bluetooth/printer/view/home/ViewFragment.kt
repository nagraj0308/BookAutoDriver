package com.bluetooth.printer.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bluetooth.printer.databinding.FragmentViewDetailsBinding
import com.bluetooth.printer.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewFragment : BaseFragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentViewDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (arguments != null) {
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