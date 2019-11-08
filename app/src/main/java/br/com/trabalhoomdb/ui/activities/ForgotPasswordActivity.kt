package br.com.trabalhoomdb.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.services.account.RetrofitInitializerAccount
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        tv_forgotPassword_error_message.visibility = TextView.INVISIBLE

        forgotPassword_btn_reset.setOnClickListener {
            if(et_forgotPassword_email.text.toString().isNotEmpty()) {
                forgotPassword()
                showMessage()

            } else {
                tv_forgotPassword_error_message.visibility = TextView.VISIBLE
            }
        }

        forgotPassword_btn_cancel.setOnClickListener {
            finish()
        }
    }

    fun showMessage() {
        MaterialDialog.Builder(this)
            .cancelable(false)
            .title(R.string.reset_password)
            .content(R.string.forgotPassword_message)
            .positiveText(R.string.ok)
            .onPositive { dialog, which -> finish() }
//            .positiveColorRes(R.color.orange)
            .titleColorRes(R.color.orange)
            .show()
    }

    fun forgotPassword() {
        val s = RetrofitInitializerAccount().serviceAccount()
        val call = s.forgotPassword(et_forgotPassword_email.text.toString())
    }
}
