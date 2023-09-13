package br.com.trabalhoomdb.presentation.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.com.trabalhoomdb.R

class SplashActivity : AppCompatActivity() {

    private val delayMillis: Long = 1030

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val shared = getSharedPreferences(
            getString(R.string.PREF_APP_NAME),
            Context.MODE_PRIVATE
        )

        shared.edit().putString(getString(R.string.PREF_HISTORIC_SEARCH), null).apply()

        Handler(Looper.getMainLooper()).postDelayed({
            goToHome()
        }, delayMillis)
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}