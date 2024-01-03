package com.book.homestay.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.book.homestay.PM
import com.book.homestay.R
import com.book.homestay.databinding.FragmentSettingBinding
import com.book.homestay.presentation.base.BaseFragment
import com.book.homestay.presentation.home.HomeActivity
import com.book.homestay.utils.Language
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment : BaseFragment() {
    @Inject
    lateinit var pm: PM

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val arrayAdapter = ArrayAdapter(
            requireContext(), R.layout.item_spinner, Language.values()
        )
        binding.actvLanguage.setAdapter(arrayAdapter)
        val selectedLangPos: Int = arrayAdapter.getPosition(
            Language.getLanguageFromCode(pm.lang)
        )
        setLanguage(selectedLangPos)
        binding.actvLanguage.setOnItemClickListener { _, _, position, _ ->
            setLanguage(position)
        }
        return root
    }

    private fun setLanguage(pos: Int) {
        binding.actvLanguage.setText(Language.values()[pos].value(), false)

        val prev: String? = pm.lang
        val language = Language.values()[pos]
        pm.lang = Language.getLanguageCode(language)
        (activity as HomeActivity).setLangCode(pm.lang!!)
        if (prev != pm.lang) {
            activity?.finishAndRemoveTask()
        }
    }


    override fun showProgress() {
    }

    override fun hideProgress() {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}