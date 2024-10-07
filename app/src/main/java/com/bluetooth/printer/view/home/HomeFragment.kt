package com.bluetooth.printer.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bluetooth.printer.R
import com.bluetooth.printer.data.PrintType
import com.bluetooth.printer.databinding.FragmentHomeBinding
import com.bluetooth.printer.view.base.BaseFragment


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.rvPrintType.layoutManager=GridLayoutManager(this.context,2);
//        val printTypes = ArrayList<PrintType>()
//        printTypes.add(PrintType("Receipt", 1, R.drawable.ic_home))
//        printTypes.add(PrintType("Receipt", 1, R.drawable.ic_home))
//        printTypes.add(PrintType("Receipt", 1, R.drawable.ic_home))
//        printTypes.add(PrintType("Receipt", 1, R.drawable.ic_home))
//        binding.rvPrintType.adapter = PrintTypeAdapter(printTypes)

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }


}