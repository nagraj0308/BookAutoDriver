package com.book.auto.presentation.viewauto

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.book.auto.databinding.FragmnetAddEditAutoDetailsBinding
import com.book.auto.driver.presentation.base.BaseFragment
import com.book.auto.presentation.home.HomeViewModel
import com.book.auto.utils.Constants
import com.book.auto.utils.PermissionUtils
import com.book.auto.utils.Utils
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewAutoFragment : BaseFragment() {

    private var _binding: FragmnetAddEditAutoDetailsBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var isNew: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmnetAddEditAutoDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnSubmit.setOnClickListener {

        }
        binding.btnSave.setOnClickListener {
            if (validate()) {
                val autoNumber = binding.tilAutoNumber.editText!!.text.trim().toString()
                val driverName = binding.tilDriverName.editText!!.text.trim().toString()
                val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
                val autoType = binding.actvAutoType.text.trim().toString()
                viewModel.updateFSImage(
                    autoType,
                    autoNumber,
                    driverName,
                    mobileNumber,
                    Utils.screenShot(binding.ivAutoPhoto)!!,
                    {
                        if (it) {
                            requireActivity().onBackPressed()
                        } else {
                            showToast("Try again")
                        }
                    },
                    {})
            }

        }
        binding.btnDelete.setOnClickListener {
            viewModel.deleteAuto({
                if (it) {
                    activity?.finish()
                }
            }, {
                showToast(it)
            })
        }

        if (arguments != null) {
            isNew = requireArguments().getBoolean("is_new")
        }


        val arrayAdapter = ArrayAdapter(
            requireContext(), R.layout.item_spinner, Constants.autoTypes
        )
        binding.actvAutoType.setAdapter(arrayAdapter)
        binding.actvAutoType.setText(Constants.autoTypes[0], false)




        binding.btnEditPhoto.setOnClickListener {
            if (PermissionUtils.checkReadStoragePermission(requireContext())) {
                startForResult.launch(
                    Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                PermissionUtils.requestReadStoragePermission(requireActivity())
            }
        }

        if (isNew) {
            binding.tilDriverName.editText!!.setText(pm.name)
            binding.llEdit.visibility = View.GONE
            binding.btnSubmit.visibility = View.VISIBLE
        } else {
            binding.tilAutoNumber.editText!!.setText(viewModel.vehicle.value!!.number)
            binding.tilDriverName.editText!!.setText(viewModel.vehicle.value!!.driver)
            binding.tilMobileNumber.editText!!.setText(viewModel.vehicle.value!!.mobileNo)
            binding.actvAutoType.setText(viewModel.vehicle.value!!.type)


            Glide.with(this).asBitmap().load(viewModel.vehicle.value!!.imageUrl)
                .into(binding.ivAutoPhoto)

            binding.llEdit.visibility = View.VISIBLE
            binding.btnSubmit.visibility = View.GONE
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}