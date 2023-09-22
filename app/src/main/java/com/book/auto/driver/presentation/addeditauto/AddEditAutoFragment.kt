package com.book.auto.driver.presentation.addeditauto

import android.R
import android.R.attr.width
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.book.auto.driver.databinding.FragmnetAddEditAutoDetailsBinding
import com.book.auto.driver.presentation.home.HomeViewModel
import com.book.auto.driver.utils.Constants
import kotlin.math.roundToInt


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
            val url: Boolean = requireArguments().getBoolean("is_add")
            Log.v("NAGRAJ", url.toString())
        }


        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.simple_spinner_dropdown_item,
            Constants.gaadiTypes
        )
        binding.actvAutoType.setAdapter(arrayAdapter)
        binding.actvAutoType.setText(Constants.gaadiTypes[0], false)


        //Auto Photo
        binding.ivAutoPhoto.post {
            val h = (binding.ivAutoPhoto.width * Constants.IMG_HBW).roundToInt()
            binding.ivAutoPhoto.layoutParams =
                LinearLayout.LayoutParams(width, h);
        }


//        val mapFragment = binding.mvCl as SupportMapFragment
//        mapFragment.getMapAsync(this)

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.btnSubmit.setOnClickListener{
            if (validate()){

            }
        }
        return root
    }


    fun validate(): Boolean {
        val name =binding.tilAutoName.editText!!.text.trim().toString()
        if(name.isEmpty()){
            binding.tilAutoName.error= getString(R.string.paste_as_plain_text)
            return false
        }else{
            binding.tilAutoName.error=""
        }

        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}