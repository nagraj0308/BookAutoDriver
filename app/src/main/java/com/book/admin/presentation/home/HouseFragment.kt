package com.book.admin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.book.admin.R
import com.book.admin.databinding.FragmentHouseBinding
import com.book.admin.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HouseFragment : BaseFragment() {

    private var _binding: FragmentHouseBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHouseBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val navController = findNavController()

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bn_accepted -> {
                    viewModel.getVehicles(1)
                    true
                }

                R.id.bn_pending -> {
                    viewModel.getVehicles(0)
                    true
                }

                R.id.bn_rejected -> {
                    viewModel.getVehicles(2)
                    true
                }

                else -> {
                    viewModel.getVehicles(1)
                    true
                }
            }
        }
        binding.bottomNav.selectedItemId = R.id.bn_pending

        binding.rcv.layoutManager = LinearLayoutManager(context)
        viewModel.vehicles.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = VehicleAdapter(it, navController)
                binding.rcv.adapter = adapter
                binding.tvTotal.text = buildString {
                    append("Total : ")
                    append(it.size)
                    append(" Items")
                }
            } else {
                binding.tvTotal.text = buildString {
                    append("Total : ")
                    append(0)
                    append(" Items")
                }
            }
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}