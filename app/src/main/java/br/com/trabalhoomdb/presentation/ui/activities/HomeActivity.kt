package br.com.trabalhoomdb.presentation.ui.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.common.di.DIFilmManager
import br.com.trabalhoomdb.common.keys.HomeActivityKeys
import br.com.trabalhoomdb.common.utils.ListenerEvents
import br.com.trabalhoomdb.common.utils.Screen
import br.com.trabalhoomdb.databinding.ActivityHomeBinding
import br.com.trabalhoomdb.presentation.ui.fragments.HistoricFragment
import br.com.trabalhoomdb.presentation.ui.fragments.HomeFragment

class HomeActivity : AppCompatActivity(), View.OnClickListener, ListenerEvents {

    private lateinit var sharedPreferencesGlobal: SharedPreferences
    private lateinit var viewBinding: ActivityHomeBinding

    //store the value of the last accessed fragment
    private var lastFragment = HomeActivityKeys.HomeFragment.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        API_KEY = getString(R.string.api_key)

        sharedPreferencesGlobal = getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        /** TODO()
         * Inicializar as depeendências
         */
        initDependencies(savedInstanceState)

        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        /** TODO()
         * Inicializar o navigation component
         */

        initListeners()
        setFragment(HomeFragment())
    }

    private fun initDependencies(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            DIFilmManager().initFilmDependenceInjection(application)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_home -> {
                val checked =
                    checkCurrentFragment(HomeActivityKeys.HomeFragment.toString(), lastFragment)

                if (!checked) {
                    val search = sharedPreferencesGlobal.getString(
                        getString(R.string.PREF_HISTORIC_SEARCH),
                        ""
                    )
                    if (search != null && search.trim().isNotEmpty()) {
                        sharedPreferencesGlobal.edit()
                            .putString(getString(R.string.PREF_HISTORIC_SEARCH), "")
                            .apply()
                    }
                    lastFragment = HomeActivityKeys.HomeFragment.toString()
                    setFragment(HomeFragment())
                }
            }

            R.id.btn_historic -> {
                val checked =
                    checkCurrentFragment(HomeActivityKeys.HistoricFragment.toString(), lastFragment)

                if (!checked) {
                    lastFragment = HomeActivityKeys.HistoricFragment.toString()
                    setFragment(HistoricFragment())
                }
            }
        }
    }

    /** TODO fazer o nav host*/

//    override fun onBackPressed() {
//        super.onBackPressed()
//        if (supportFragmentManager.backStackEntryCount == 0)
//            finish()
//    }

    override fun initListeners() {
        viewBinding.toolbar.btnHome.setOnClickListener(this)
        viewBinding.toolbar.btnHistoric.setOnClickListener(this)
    }

    private fun checkCurrentFragment(currentFragment: String, lastFragment: String): Boolean {
        return currentFragment == lastFragment
    }

    fun setFragment(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(viewBinding.frameLayout.id, f)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun showHideProgressBar(visibility: Boolean) {
        if (visibility) {
            viewBinding.progressBar.visibility = View.VISIBLE
            viewBinding.activityContent.alpha = 0.2f
            Screen.enableDisableView(this, false)
        } else {
            viewBinding.progressBar.visibility = View.GONE
            viewBinding.activityContent.alpha = 1f
            Screen.enableDisableView(this, true)
        }
    }

    companion object {
        private var API_KEY = ""

        fun getApiKey() = API_KEY
    }
}
