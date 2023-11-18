package com.rent.house.presentation.base

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    fun showToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
    }

    abstract fun showProgress()
    abstract fun hideProgress()

}