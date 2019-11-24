package br.com.trabalhoomdb.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.ui.fragments.HistoricFragment
import br.com.trabalhoomdb.ui.fragments.HomeFragment
import br.com.trabalhoomdb.ui.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setFragment(HomeFragment())

        btn_home.setOnClickListener {
            setFragment(HomeFragment())
        }

        btn_profile.setOnClickListener {
            setFragment(ProfileFragment())
        }
    }

    fun setFragment(f: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, f)
        ft.addToBackStack(null)
        ft.commit()
    }
}
