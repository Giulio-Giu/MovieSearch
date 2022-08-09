package br.com.trabalhoomdb.common.utils

import android.app.Activity
import android.view.WindowManager

class Screen {
    companion object{
        fun enableDisableView(activity: Activity, enabled: Boolean) {
            if (enabled) {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            } else {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }
        }
    }
}