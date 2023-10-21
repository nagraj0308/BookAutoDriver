package com.book.admin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        binding.rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    viewModel.getPendingAutos()
                }
            }
        })
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}