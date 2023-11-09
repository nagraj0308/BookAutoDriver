package com.book.gaadi.presentation.mygaadi

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
import com.book.gaadi.PM
import com.book.gaadi.R
import com.book.gaadi.databinding.FragmentAddEditAutoDetailsBinding
import com.book.gaadi.presentation.base.BaseFragment
import com.book.gaadi.presentation.home.HomeViewModel
import com.book.gaadi.utils.Constants
import com.book.gaadi.utils.PermissionUtils
import com.book.gaadi.utils.Utils
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditAutoFragment : BaseFragment() {
    @Inject
    lateinit var pm: PM

    private var _binding: FragmentAddEditAutoDetailsBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var isNew: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditAutoDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnSubmit.setOnClickListener {
            if (validate()) {
                val autoNumber = binding.tilAutoNumber.editText!!.text.trim().toString()
                val driverName = binding.tilDriverName.editText!!.text.trim().toString()
                val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
                val rate = binding.actvAutoType.text.trim().toString()
                val autoType = binding.actvAutoType.text.trim().toString()
                viewModel.insertFSImage(
                    2,
                    autoNumber,
                    driverName,
                    mobileNumber,
                    rate,
                    Utils.screenShot(binding.ivAutoPhoto)!!,
                    {
                        if (it) {
                            requireActivity().onBackPressed()
                        } else {
                            showToast("Try again")
                        }
                    },
                    { showToast("") })
            }
        }
        binding.btnSave.setOnClickListener {
            if (validate()) {
                val autoNumber = binding.tilAutoNumber.editText!!.text.trim().toString()
                val driverName = binding.tilDriverName.editText!!.text.trim().toString()
                val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
                val rate = binding.actvAutoType.text.trim().toString()
                val autoType = binding.actvAutoType.text.trim().toString()
                viewModel.updateFSImage(
                    1,
                    autoNumber,
                    driverName,
                    mobileNumber,
                    rate,
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
            binding.actvAutoType.setText(
                Constants.autoTypes[viewModel.vehicle.value!!.typeId],
                false
            )


            Glide.with(this).asBitmap().load(viewModel.vehicle.value!!.imageUrl)
                .into(binding.ivAutoPhoto)

            binding.llEdit.visibility = View.VISIBLE
            binding.btnSubmit.visibility = View.GONE
        }
        return root
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            val selectedImage: Uri = data!!.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver.query(
                selectedImage,
                filePathColumn,
                null,
                null,
                null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            Glide.with(this).asBitmap().load(picturePath).into(binding.ivAutoPhoto)
            performCrop(selectedImage, 3, 2)
        }
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

    private fun performCrop(picUri: Uri, arx: Int, ary: Int) {
        val cropIntent = Intent("com.android.camera.action.CROP")
        cropIntent.setDataAndType(picUri, "image/*")
        cropIntent.putExtra("crop", "true")
        cropIntent.putExtra("aspectX", arx)
        cropIntent.putExtra("aspectY", ary)
        cropIntent.putExtra("outputX", 256)
        cropIntent.putExtra("outputY", 256)
        cropIntent.putExtra("return-data", true)
        startForCropResult.launch(
            Intent(
                cropIntent
            )
        )
    }


    private fun validate(): Boolean {

        val autoNumber = binding.tilAutoNumber.editText!!.text.trim().toString()
        if (autoNumber.length < 4) {
            binding.tilAutoNumber.error = getString(R.string.this_should_be_4_digits)
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
        if (mobileNumber.length < 10) {
            binding.tilMobileNumber.error = getString(R.string.this_should_be_10_digits)
            return false
        } else {
            binding.tilMobileNumber.isErrorEnabled = false
        }

        if (!viewModel.isLocationUpdated.value!!) {
            showToast("Please update location")
            return false
        }

        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}