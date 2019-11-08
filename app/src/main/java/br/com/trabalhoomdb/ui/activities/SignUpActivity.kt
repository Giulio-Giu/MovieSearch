package br.com.trabalhoomdb.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.models.account.Account
import br.com.trabalhoomdb.services.account.RetrofitInitializerAccount
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        tv_signUp_message_error.visibility = TextView.INVISIBLE

        signUp_btn_signUp.setOnClickListener {
            if (et_signUp_password.text.toString() == et_signUp_confirmPassword.text.toString()) {
                doSignUp()
            } else {
                tv_signUp_message_error.visibility = TextView.VISIBLE
                tv_signUp_message_error.text = getString(R.string.error_passwords)
            }
        }

        signUp_btn_cancel.setOnClickListener {
            finish()
        }
    }

    fun doSignUp() {
        val s = RetrofitInitializerAccount().serviceAccount()

        val account = Account()
        account.name = et_signUp_name.text.toString()
        account.email = et_signUp_email.text.toString()
        account.password = et_signUp_password.text.toString()

        val call = s.auth(account)

        call.enqueue(object : Callback<Account> {

            override fun onResponse(call: Call<Account>?, response: Response<Account>?) {
                response?.let {
                    if(it.code() == 200) {
                        Toast.makeText(this@SignUpActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        tv_signUp_message_error.visibility = TextView.VISIBLE
                        tv_signUp_message_error.text = getString(R.string.error_data)
                    }
                }
            }

            override fun onFailure(call: Call<Account>?, t: Throwable?) {
                Toast.makeText(this@SignUpActivity, "Ops, deu erro!", Toast.LENGTH_LONG).show()
            }
        })
    }
}
