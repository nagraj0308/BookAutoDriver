package com.book.admin.presentation

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.book.admin.R
import com.book.admin.databinding.FragmentViewBinding
import com.book.admin.presentation.home.HomeViewModel
import com.book.admin.utils.BaseFragment
import com.book.admin.utils.Constants
import com.bumptech.glide.Glide


class ViewFragment : BaseFragment() {

    private var _binding: FragmentViewBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var isNew: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentViewBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnSubmit.setOnClickListener({
            if (validate()) {
                val name = binding.tilAutoName.editText!!.text.trim().toString()
                val autoNumber = binding.tilAutoNumber.editText!!.text.trim().toString()
                val driverName = binding.tilDriverName.editText!!.text.trim().toString()
                val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
                val rate = binding.tilNormalRate.editText!!.text.trim().toString()
                val autoType = binding.actvAutoType.text.trim().toString()
//                viewModel.insertFSImage(
//                    name, autoType,
//                    rate,
//                    autoNumber,
//                    driverName,
//                    mobileNumber,
//                    binding.sbDeactivated.isChecked,
//                    Utils.screenShot(binding.ivAutoPhoto)!!
//                ) {
//                    if (it) {
//                        requireActivity().onBackPressed()
//                    } else {
//                        showToast("Try again")
//                    }
//                }
            }
        })
        binding.btnSave.setOnClickListener(View.OnClickListener {
            if (validate()) {
                val name = binding.tilAutoName.editText!!.text.trim().toString()
                val autoNumber = binding.tilAutoNumber.editText!!.text.trim().toString()
                val driverName = binding.tilDriverName.editText!!.text.trim().toString()
                val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
                val rate = binding.tilNormalRate.editText!!.text.trim().toString()
                val autoType = binding.actvAutoType.text.trim().toString()
//                viewModel.updateFSImage(
//                    name, autoType,
//                    rate,
//                    autoNumber,
//                    driverName,
//                    mobileNumber,
//                    binding.sbDeactivated.isChecked,
//                    Utils.screenShot(binding.ivAutoPhoto)!!
//                ) {
//                    if (it) {
//                        requireActivity().onBackPressed()
//                    } else {
//                        showToast("Try again")
//                    }
//                }
            }

        })
        binding.btnDelete.setOnClickListener {
//            viewModel.deleteAuto {
//                if (it) {
//                    activity?.onBackPressed()
//                }
//            }
        }

        if (arguments != null) {
            isNew = requireArguments().getBoolean("is_new")
        }


        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            Constants.gaadiTypes
        )
        binding.actvAutoType.setAdapter(arrayAdapter)
        binding.actvAutoType.setText(Constants.gaadiTypes[0], false)


//        if (isNew) {
//            binding.tilDriverName.editText!!.setText(viewModel.readState.value!!.name)
//            binding.llEdit.visibility = View.GONE
//            binding.btnSubmit.visibility = View.VISIBLE
//        } else {
//            binding.tilAutoName.editText!!.setText(viewModel.readVehicle.value!!.name)
//            binding.tilAutoNumber.editText!!.setText(viewModel.readVehicle.value!!.number)
//            binding.tilDriverName.editText!!.setText(viewModel.readVehicle.value!!.driver)
//            binding.tilMobileNumber.editText!!.setText(viewModel.readVehicle.value!!.mobileNo)
//            binding.actvAutoType.setText(viewModel.readVehicle.value!!.type)
//            binding.sbDeactivated.setChecked(viewModel.readVehicle.value!!.deactivated)
//
//            Glide.with(this).asBitmap().load(viewModel.readVehicle.value!!.imageUrl)
//                .into(binding.ivAutoPhoto)
//
//            binding.llEdit.visibility = View.VISIBLE
//            binding.btnSubmit.visibility = View.GONE
//        }
        return root
    }


    private val startForCropResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            try {
                val extras: Bundle = data!!.extras!!
                val croppedPic = extras.getParcelable<Bitmap>("data")
                Glide.with(this).asBitmap().load(croppedPic).into(binding.ivAutoPhoto)
            } catch (_: Exception) {
            }
        }
    }


    private fun validate(): Boolean {
        val name = binding.tilAutoName.editText!!.text.trim().toString()
        if (name.isEmpty()) {
            binding.tilAutoName.error = getString(R.string.this_field_required)
            return false
        } else {
            binding.tilAutoName.isErrorEnabled = false
        }

        val autoNumber = binding.tilAutoNumber.editText!!.text.trim().toString()
        if (autoNumber.length < 10) {
            binding.tilAutoNumber.error = getString(R.string.this_should_be_10_digits)
            return false
        } else {
            binding.tilAutoNumber.isErrorEnabled = false
        }

        val driverName = binding.tilDriverName.editText!!.text.trim().toString()
        if (driverName.isEmpty()) {
            binding.tilDriverName.error = getString(R.string.this_field_required)
            return false
        } else {
            binding.tilDriverName.isErrorEnabled = false
        }

        val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()



        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}