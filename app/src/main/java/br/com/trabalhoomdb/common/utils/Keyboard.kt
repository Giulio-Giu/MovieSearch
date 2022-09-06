package br.com.trabalhoomdb.common.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

class Keyboard {
    companion object {

        fun hideKeyboard(view: View) {
            val inputMethodManager = view.context.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun showKeyboard(view: View) {
            val inputMethodManager = view.context.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}