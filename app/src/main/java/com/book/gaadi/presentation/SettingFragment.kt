package com.book.gaadi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.book.gaadi.PM
import com.book.gaadi.R
import com.book.gaadi.databinding.FragmentSettingBinding
import com.book.gaadi.presentation.base.BaseFragment
import com.book.gaadi.utils.Language
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
        binding.actvLanguage.setSelection(selectedLangPos)


        return root
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