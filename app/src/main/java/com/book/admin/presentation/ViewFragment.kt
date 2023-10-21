package com.book.admin.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.book.admin.R
import com.book.admin.data.remote.reqres.Auto
import com.book.admin.databinding.FragmentViewBinding
import com.book.admin.presentation.home.HomeViewModel
import com.book.admin.utils.BaseFragment
import com.book.admin.utils.Constants
import com.bumptech.glide.Glide


class ViewFragment : BaseFragment() {

    private var _binding: FragmentViewBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var isAuto: Boolean = true
    private var data: Auto? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentViewBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        binding.btnSubmit.setOnClickListener({
//            val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
//            val rate = binding.tilNormalRate.editText!!.text.trim().toString()
//            val autoType = binding.actvAutoType.text.trim().toString()
////                viewModel.insertFSImage(
////                    name, autoType,
////                    rate,
////                    autoNumber,
////                    driverName,
////                    mobileNumber,
////                    binding.sbDeactivated.isChecked,
////                    Utils.screenShot(binding.ivAutoPhoto)!!
////                ) {
////                    if (it) {
////                        requireActivity().onBackPressed()
////                    } else {
////                        showToast("Try again")
////                    }
////                }
//        })


        if (arguments != null) {
            isAuto = requireArguments().getBoolean("is_auto")
            data = requireArguments().getSerializable("item") as Auto?
        }


        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            Constants.vss
        )
        binding.actvAutoType.setAdapter(arrayAdapter)
        binding.actvAutoType.setText(Constants.vss[0].toString(), false)

        context?.let { Glide.with(it).asBitmap().load(data?.imageUrl).into(binding.ivPhoto) }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}