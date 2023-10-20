package com.book.admin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.book.admin.R
import com.book.admin.databinding.FragmentAutoBinding
import com.book.admin.utils.BaseFragment


class AutoFragment : BaseFragment() {

    private var _binding: FragmentAutoBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private var isNew: Boolean = true


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutoBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val navController = findNavController()

//        binding.btnEdit.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putBoolean("is_new", isNew)
//            navController.navigate(R.id.nav_auto, bundle)
//        }

//        viewModel.readVehicle.observe(viewLifecycleOwner, Observer {
//            //setContentState()
//        })

        //Maps View
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}