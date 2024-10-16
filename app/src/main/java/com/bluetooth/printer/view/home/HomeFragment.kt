package com.bluetooth.printer.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bluetooth.printer.R
import com.bluetooth.printer.data.PrintType
import com.bluetooth.printer.databinding.FragmentHomeBinding
import com.bluetooth.printer.view.base.BaseFragment
import com.bluetooth.printer.view.printimage.PrintImageActivity
import com.bluetooth.printer.view.printpdf.PrintPDFActivity
import com.google.android.gms.ads.AdRequest


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvPrintType.layoutManager=GridLayoutManager(this.context,2);
        val printTypes = ArrayList<PrintType>()
        printTypes.add(PrintType("Print PDF", 1, R.drawable.ic_pdf))
        printTypes.add(PrintType("Print Image", 2, R.drawable.ic_image))
        binding.rvPrintType.adapter = PrintTypeAdapter(printTypes,onClickListener = { _, item -> openPrintTypeActivity(item)})
        return root
    }

    private fun openPrintTypeActivity(printType: PrintType){
       if (printType.id==1){
           activity?.let { PrintPDFActivity.start(it,printType) }
       }else{
           activity?.let { PrintImageActivity.start(it,printType) }
       }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.bannerAd.loadAd(adRequest)
    }


}