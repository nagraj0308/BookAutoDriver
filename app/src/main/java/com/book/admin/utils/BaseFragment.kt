package com.book.admin.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    fun showToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
    }
}