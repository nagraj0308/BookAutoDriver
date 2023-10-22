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
import com.book.admin.R
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

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bn_accepted -> {
                    viewModel.getAutos(1)
                    true
                }

                R.id.bn_pending -> {
                    viewModel.getAutos(0)
                    true
                }

                R.id.bn_rejected -> {
                    viewModel.getAutos(2)
                    true
                }

                else -> {
                    viewModel.getAutos(1)
                    true
                }
            }
        }
        binding.bottomNav.selectedItemId = R.id.bn_pending

        binding.rcv.layoutManager = LinearLayoutManager(context)
        viewModel.autos.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = AutoAdapter(it, navController)
                binding.rcv.adapter = adapter
            }
        }
        binding.rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    viewModel.getAutos(viewModel.statePos.value!!)
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