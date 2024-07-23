package com.bluetooth.printer.view.myhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bluetooth.printer.R
import com.bluetooth.printer.databinding.FragmentMyHouseBinding
import com.bluetooth.printer.view.base.BaseFragment
import com.bluetooth.printer.view.home.HomeViewModel


class MyHouseFragment : BaseFragment() {

    private var _binding: FragmentMyHouseBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private var isNew: Boolean = true


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyHouseBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val navController = findNavController()
        binding.btnAddAuto.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_new", isNew)
            navController.navigate(R.id.nav_edit_house, bundle)
        }



        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_new", isNew)
            navController.navigate(R.id.nav_edit_house, bundle)
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