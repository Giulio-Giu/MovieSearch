package br.com.trabalhoomdb.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

    lateinit var sharedPrefferencesGlobal: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //verificando se tem usuário logado
        sharedPrefferencesGlobal = getSharedPreferences(getString(R.string.PREF_APP_NAME), Context.MODE_PRIVATE)

        if (sharedPrefferencesGlobal.getString(getString(R.string.PREF_EMAIL), "")!!.isNotEmpty()) {

            val user = Account()
            user.email = sharedPrefferencesGlobal.getString(getString(R.string.PREF_EMAIL), "")!!
            user.password = sharedPrefferencesGlobal.getString(getString(R.string.PREF_PASSWORD), "")!!
            user.name = sharedPrefferencesGlobal.getString(getString(R.string.PREF_NAME), "")!!

            gotoHome(true, user)
        }

        tv_signIn_wrong_credentials.visibility = TextView.INVISIBLE

        tv_forgot_password.setOnClickListener {
            forgotPassword()
        }

        signIn_btn_signIn.setOnClickListener {
            auth()
        }

        signIn_btn_signUp.setOnClickListener {
            doSignUp()
        }
    }

    fun forgotPassword() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    fun doSignUp() {
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
                        gotoHome(false, it.body())
                    } else {
                        tv_signIn_wrong_credentials.visibility = TextView.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<Account>?, t: Throwable?) {
                Toast.makeText(this@SignInActivity, "Ops, deu erro!", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun gotoHome(isLogged: Boolean, user: Account) {
        //salvando a sessão do usuário, para permanecer logado ao iniciar o app
        if (!isLogged) {
            sharedPrefferencesGlobal.edit()
                .putString(getString(R.string.PREF_EMAIL), et_signIn_email.text.toString().trim())
                .putString(getString(R.string.PREF_PASSWORD), et_signIn_password.text.toString().trim())
                .putString(getString(R.string.PREF_NAME), user.name)
                .apply()
        }

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(getString(R.string.USER), user)
        startActivity(intent)
        finish()
    }
}
