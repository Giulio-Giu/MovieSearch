package br.com.trabalhoomdb.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.trabalhoomdb.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_btn_doSignIn.setOnClickListener {
            doSignIn()
        }

        main_btn_doSignUp.setOnClickListener {
            doSignUp()
        }
    }

    fun doSignIn(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    fun doSignUp(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
