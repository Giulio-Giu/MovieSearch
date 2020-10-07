package br.com.trabalhoomdb.ui.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.ui.fragments.HistoricFragment
import br.com.trabalhoomdb.ui.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var clickHome = false
        var clickHistoric = false

        val sharedPreferences = getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        setFragment(HomeFragment())

        btn_home.setOnClickListener {
            val search = sharedPreferences.getString(getString(R.string.PREF_HISTORIC_SEARCH), "")
            if (!clickHome || (search != null && search.trim().isNotEmpty())) {
                sharedPreferences.edit()
                    .putString(getString(R.string.PREF_HISTORIC_SEARCH), "")
                    .apply()
                setFragment(HomeFragment())

                clickHistoric = false
                clickHome = true
            }
        }

        btn_historic.setOnClickListener {
            if (!clickHistoric) {
                setFragment(HistoricFragment())

                clickHistoric = true
                clickHome = false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0)
            finish()
    }

    fun setFragment(f: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, f)
//        ft.addToBackStack(null)
        ft.commit()
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager

        activity.currentFocus?.let { focus ->
            inputMethodManager.hideSoftInputFromWindow(focus.windowToken, 0)
        }
    }
}
