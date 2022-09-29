package com.example.nabtastore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nabtastore.databinding.ActivitySignBinding
import com.google.firebase.auth.FirebaseAuth

class sign : AppCompatActivity() {

    lateinit var binding: ActivitySignBinding
    lateinit var mAuth : FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    private var isRemember  = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("share_pref", Context.MODE_PRIVATE)

        isRemember = sharedPreferences.getBoolean("check",false)

        if (isRemember )
        {
            var i = Intent(this,home::class.java)
            startActivity(i)
            finish()
        }

        mAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            if (binding.email.text.isEmpty()&&binding.pass.text.isEmpty())
            {
                Toast.makeText(this,"Please enater your data...",Toast.LENGTH_LONG).show()
            }else if (binding.email.text.isEmpty())
            {
                Toast.makeText(this,"Enter your email...",Toast.LENGTH_LONG).show()
            }
            else if (binding.pass.text.isEmpty())
            {
                Toast.makeText(this ,"Enter your password...",Toast.LENGTH_LONG).show()
            }
            else {

                var email = binding.email.text.toString()
                var password = binding.pass.text.toString()
                login(email, password)
            }
        }
        binding.register.setOnClickListener {
            var  i = Intent(this,register::class.java)
            startActivity(i)
        }


    }
    private fun login(email :String , password :String)
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.apply(){


                        putBoolean("check",true)
                    }.apply()

                        Toast.makeText(this,"Account Saved ...",Toast.LENGTH_LONG).show()


                    var i = Intent(this@sign,home::class.java)
                    startActivity(i)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@sign,"User dosn`t exist", Toast.LENGTH_SHORT).show()
                }
            }

    }
}