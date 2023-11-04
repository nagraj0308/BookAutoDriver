package com.book.auto.presentation.viewauto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.book.auto.databinding.FragmnetViewDetailsBinding
import com.book.auto.driver.presentation.base.BaseFragment
import com.book.auto.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAutoFragment : BaseFragment() {

    private var _binding: FragmnetViewDetailsBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var isNew: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmnetViewDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnSubmit.setOnClickListener {

        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}