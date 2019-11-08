package br.com.trabalhoomdb.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.models.account.Account
import br.com.trabalhoomdb.services.account.RetrofitInitializerAccount
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tv_signIn_wrong_credentials.visibility = TextView.INVISIBLE

        signIn_btn_signIn.setOnClickListener {
            auth()
        }

        signIn_btn_signUp.setOnClickListener {
            doSignUp()
        }
    }

    fun doSignUp(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun auth() {
        val s = RetrofitInitializerAccount().serviceAccount()

        val account = Account()
        account.email = et_signIn_email.text.toString()
        account.password = et_signIn_password.text.toString()

        val call = s.auth(account)

        call.enqueue(object : Callback<Account> {

            override fun onResponse(call: Call<Account>?, response: Response<Account>?) {
                response?.let {
                    if (it.code() == 200) {
                        tv_signIn_wrong_credentials.visibility = TextView.INVISIBLE
                        Toast.makeText(this@SignInActivity, "Autenticado!", Toast.LENGTH_LONG).show()
                        gotoHome()
                    } else {
                        tv_signIn_wrong_credentials.visibility = TextView.VISIBLE
//                        Toast.makeText(this@SignInActivity, "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Account>?, t: Throwable?) {
                Toast.makeText(this@SignInActivity, "Ops, deu erro!", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun gotoHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
