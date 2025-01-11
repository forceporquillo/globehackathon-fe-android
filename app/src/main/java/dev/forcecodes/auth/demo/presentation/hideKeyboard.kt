package dev.forcecodes.auth.demo.presentation

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = view?.rootView ?: View(requireContext())
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
