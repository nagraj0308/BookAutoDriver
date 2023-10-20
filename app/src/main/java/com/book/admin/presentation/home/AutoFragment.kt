package com.book.admin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.book.admin.databinding.FragmentAutoBinding
import com.book.admin.utils.BaseFragment


class AutoFragment : BaseFragment() {

    private var _binding: FragmentAutoBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutoBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val navController = findNavController()

        binding.rcv.layoutManager = LinearLayoutManager(context)
        viewModel.autos.observe(viewLifecycleOwner) {
            val adapter = AutoAdapter(it)
            binding.rcv.adapter = adapter
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}