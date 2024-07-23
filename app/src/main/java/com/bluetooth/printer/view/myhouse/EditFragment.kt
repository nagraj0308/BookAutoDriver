package com.bluetooth.printer.view.myhouse

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bluetooth.printer.PM
import com.bluetooth.printer.R
import com.bluetooth.printer.databinding.FragmentEditDetailsBinding
import com.bluetooth.printer.view.base.BaseFragment
import com.bluetooth.printer.view.home.HomeViewModel
import com.bluetooth.printer.view.utils.PermissionUtils
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : BaseFragment() {
    @Inject
    lateinit var pm: PM

    private var _binding: FragmentEditDetailsBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var isNew: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        hideProgress()
        binding.btnSubmit.setOnClickListener {
            if (validate()) {
                val name = binding.tilName.editText!!.text.trim().toString()
                val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
                val rate = binding.tilRate.editText!!.text.trim().toString()
                val address = binding.tilAddress.editText!!.text.trim().toString()
                showProgress()

            }
        }
        binding.btnSave.setOnClickListener {
            if (validate()) {
                val name = binding.tilName.editText!!.text.trim().toString()
                val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
                val rate = binding.tilRate.editText!!.text.trim().toString()
                val address = binding.tilAddress.editText!!.text.trim().toString()
                showProgress()

            }

        }
        binding.btnDelete.setOnClickListener {
            showProgress()

        }

        if (arguments != null) {
            isNew = requireArguments().getBoolean("is_new")
        }


        binding.ivAutoPhoto1.setOnClickListener {
            if (PermissionUtils.checkReadStoragePermission(requireContext())) {
                startForResult1.launch(
                    Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                PermissionUtils.requestReadStoragePermission(requireActivity())
            }
        }


        binding.ivAutoPhoto2.setOnClickListener {
            if (PermissionUtils.checkReadStoragePermission(requireContext())) {
                startForResult2.launch(
                    Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                PermissionUtils.requestReadStoragePermission(requireActivity())
            }
        }

        binding.ivAutoPhoto3.setOnClickListener {
            if (PermissionUtils.checkReadStoragePermission(requireContext())) {
                startForResult3.launch(
                    Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                PermissionUtils.requestReadStoragePermission(requireActivity())
            }
        }


        binding.ivAutoPhoto4.setOnClickListener {
            if (PermissionUtils.checkReadStoragePermission(requireContext())) {
                startForResult4.launch(
                    Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                PermissionUtils.requestReadStoragePermission(requireActivity())
            }
        }

        if (isNew) {
            binding.llEdit.visibility = View.GONE
            binding.btnSubmit.visibility = View.VISIBLE
        } else {

            binding.llEdit.visibility = View.VISIBLE
            binding.btnSubmit.visibility = View.GONE
        }
        return root
    }

    private val startForResult1 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            val selectedImage: Uri = data!!.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver.query(
                selectedImage, filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            Glide.with(this).asBitmap().load(picturePath).into(binding.ivAutoPhoto1)
            performCrop(selectedImage, 3, 2, 1)
        }
    }

    private val startForResult2 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            val selectedImage: Uri = data!!.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver.query(
                selectedImage, filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            Glide.with(this).asBitmap().load(picturePath).into(binding.ivAutoPhoto2)
            performCrop(selectedImage, 3, 2, 2)
        }
    }

    private val startForResult3 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            val selectedImage: Uri = data!!.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver.query(
                selectedImage, filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            Glide.with(this).asBitmap().load(picturePath).into(binding.ivAutoPhoto3)
            performCrop(selectedImage, 3, 2, 3)
        }
    }

    private val startForResult4 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            val selectedImage: Uri = data!!.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver.query(
                selectedImage, filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            Glide.with(this).asBitmap().load(picturePath).into(binding.ivAutoPhoto4)
            performCrop(selectedImage, 3, 2, 4)
        }
    }


    private val startForCropResult1 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            try {
                val extras: Bundle = data!!.extras!!
                val croppedPic = extras.getParcelable<Bitmap>("data")
                Glide.with(this).asBitmap().load(croppedPic).into(binding.ivAutoPhoto1)
                if (croppedPic != null) {
                }
            } catch (_: Exception) {
            }
        }
    }

    private val startForCropResult2 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            try {
                val extras: Bundle = data!!.extras!!
                val croppedPic = extras.getParcelable<Bitmap>("data")
                Glide.with(this).asBitmap().load(croppedPic).into(binding.ivAutoPhoto2)
                if (croppedPic != null) {
                }
            } catch (_: Exception) {
            }
        }
    }

    private val startForCropResult3 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            try {
                val extras: Bundle = data!!.extras!!
                val croppedPic = extras.getParcelable<Bitmap>("data")
                Glide.with(this).asBitmap().load(croppedPic).into(binding.ivAutoPhoto3)
                if (croppedPic != null) {
                }
            } catch (_: Exception) {
            }
        }
    }

    private val startForCropResult4 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && null != result.data) {
            val data = result.data
            try {
                val extras: Bundle = data!!.extras!!
                val croppedPic = extras.getParcelable<Bitmap>("data")
                Glide.with(this).asBitmap().load(croppedPic).into(binding.ivAutoPhoto4)
                if (croppedPic != null) {
                }

            } catch (_: Exception) {
            }
        }
    }


    private fun performCrop(picUri: Uri, arx: Int, ary: Int, imgNo: Int) {
        val cropIntent = Intent("com.android.camera.action.CROP")
        cropIntent.setDataAndType(picUri, "image/*")
        cropIntent.putExtra("crop", "true")
        cropIntent.putExtra("aspectX", arx)
        cropIntent.putExtra("aspectY", ary)
        cropIntent.putExtra("outputX", 256)
        cropIntent.putExtra("outputY", 256)
        cropIntent.putExtra("return-data", true)
        if (imgNo == 1) {
            startForCropResult1.launch(
                Intent(
                    cropIntent
                )
            )
        } else if (imgNo == 2) {
            startForCropResult2.launch(
                Intent(
                    cropIntent
                )
            )
        } else if (imgNo == 3) {
            startForCropResult3.launch(
                Intent(
                    cropIntent
                )
            )
        } else if (imgNo == 4) {
            startForCropResult4.launch(
                Intent(
                    cropIntent
                )
            )
        }
    }


    private fun validate(): Boolean {


        val driverName = binding.tilName.editText!!.text.trim().toString()
        if (driverName.isEmpty()) {
            binding.tilName.error = getString(R.string.this_field_required)
            return false
        } else {
            binding.tilName.isErrorEnabled = false
        }

        val mobileNumber = binding.tilMobileNumber.editText!!.text.trim().toString()
        if (mobileNumber.length < 5) {
            binding.tilMobileNumber.error = getString(R.string.this_should_be_n_digits)
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

    override fun showProgress() {
        binding.content.visibility = View.GONE
        binding.pbCpb.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.content.visibility = View.VISIBLE
        binding.pbCpb.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}