package br.com.trabalhoomdb.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.ui.activities.HomeActivity
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contextActivity = context as HomeActivity

        val shared = contextActivity.getSharedPreferences(getString(R.string.PREF_APP_NAME), Context.MODE_PRIVATE)

        tv_fragmentProfile_userName.text = shared.getString(getString(R.string.PREF_NAME), "")
        tv_fragmentProfile_userEmail.text = shared.getString(getString(R.string.PREF_EMAIL), "")
        tv_fragmentProfile_userCreateAt.text = shared.getString(getString(R.string.PREF_CREATEAT), "")

        btn_fragmentProfile_logout.setOnClickListener {
            doLogout()
        }

        tv_fragmentProfile_historic.setOnClickListener {
            gotoHistoric()
        }
    }

    fun doLogout() {
        val contextActivity = context as HomeActivity

        val shared = contextActivity.getSharedPreferences(getString(R.string.PREF_APP_NAME), Context.MODE_PRIVATE)

        MaterialDialog.Builder(contextActivity)
            .title(R.string.fragmentProfile_logout_title)
            .content(R.string.fragmentProfile_logout_message)
            .negativeColorRes(R.color.dark_red)
            .negativeText(R.string.fragmentProfile_logout_positiveButtonText)
            .onNegative { dialog, which ->

                shared.edit().clear()
                    .apply()

                (activity as HomeActivity).doLogout()

                dialog.dismiss()

                onDestroy()
            }
            .positiveText(R.string.fragmentProfile_logout_negativeButtonText)
            .show()
    }


    fun gotoHistoric() {
        val contextActivity = context as HomeActivity
        (activity as HomeActivity).setFragment(HistoricFragment())
    }

}
