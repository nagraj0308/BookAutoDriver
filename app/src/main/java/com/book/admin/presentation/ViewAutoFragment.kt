package com.book.admin.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.book.admin.R
import com.book.admin.data.remote.reqres.Auto
import com.book.admin.databinding.FragmentViewBinding
import com.book.admin.presentation.home.HomeViewModel
import com.book.admin.utils.BaseFragment
import com.book.admin.utils.Constants
import com.book.admin.utils.Utils
import com.bumptech.glide.Glide


class ViewAutoFragment : BaseFragment() {

    private var _binding: FragmentViewBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var isAuto: Boolean = true
    private var data: Auto? = null


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val navController = findNavController()

        if (arguments != null) {
            isAuto = requireArguments().getBoolean("is_auto")
            data = requireArguments().getSerializable("item") as Auto?
        }

        if (data != null) {
            binding.btnCall.setOnClickListener {
                val u = Uri.parse("tel:" + data!!.mobileNo)
                val i = Intent(Intent.ACTION_DIAL, u)
                try {
                    context?.startActivity(i)
                } catch (_: Exception) {

                }
            }
            binding.tvNumber.text = ": " + data!!.number
            binding.tvId.text = ": " + data!!._id
            binding.tvModTime.text = ": " + Utils.convertLongToTime(data!!.modifyTime)
            binding.tvDriverName.text = ": " + data!!.driver
            binding.tvType.text = ": " + data!!.type
            binding.tilAdminRemark.editText!!.setText(data!!.adminRemark)

            val arrayAdapter = ArrayAdapter(
                requireContext(), R.layout.item_spinner, Constants.vss
            )
            binding.actvStatus.setAdapter(arrayAdapter)
            binding.actvStatus.setOnItemClickListener { _, _, position, _ ->
                setStatus(Constants.vss[position].code, navController, true)
            }
            setStatus(data!!.verificationState, navController, false)
            context?.let { Glide.with(it).asBitmap().load(data?.imageUrl).into(binding.ivPhoto) }

            binding.btnSave.setOnClickListener {
                viewModel.changeAutoAdminRemark(
                    binding.tilAdminRemark.editText!!.text.toString(),
                    data!!._id
                ) {
                    navController.popBackStack()
                }
            }

        }
        return root
    }

    private fun setStatus(code: String, navController: NavController, update: Boolean) {
        binding.actvStatus.setText(Constants.status(code).toString(), false)
        if (code != Constants.vss[0].code) {
            binding.llStatus.visibility = View.GONE
            if (update) {
                viewModel.changeAutoStatus(code, data!!._id) {
                    navController.popBackStack()
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}