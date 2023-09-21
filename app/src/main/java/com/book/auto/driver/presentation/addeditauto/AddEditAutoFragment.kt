package com.book.auto.driver.presentation.addeditauto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.book.auto.driver.databinding.FragmnetAddEditAutoDetailsBinding
import com.book.auto.driver.presentation.home.HomeViewModel


class AddEditAutoFragment : Fragment() {

    private var _binding: FragmnetAddEditAutoDetailsBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmnetAddEditAutoDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnSubmit.setOnClickListener(View.OnClickListener {

        })

        if (arguments != null) {
            // The getPrivacyPolicyLink() method will be created automatically.
            val url: Boolean = requireArguments().getBoolean("is_add")
            Log.v("NAGRAJ",url.toString())
        }

//        val mapFragment = binding.mvCl as SupportMapFragment
//        mapFragment.getMapAsync(this)

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}