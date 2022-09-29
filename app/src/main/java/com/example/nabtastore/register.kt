package com.example.nabtastore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nabtastore.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var mAuth : FirebaseAuth
    lateinit var mDbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mAuth = FirebaseAuth.getInstance()



        binding.btnRegis.setOnClickListener {
            if (binding.edtNameRegis.text.isEmpty() && binding.edtEmailRegis.text.isEmpty()
                && binding.edtPasswordRegis.text.isEmpty() && binding.edtPhoneRegis.text.isEmpty())
            {
                Toast.makeText(this,"Please Enter your Data...",Toast.LENGTH_LONG).show()
            }else if (binding.edtNameRegis.text.isEmpty())
            {
                Toast.makeText(this,"Enter your Name...",Toast.LENGTH_SHORT).show()
            }else if (binding.edtEmailRegis.text.isEmpty())
            {
                Toast.makeText(this,"Enter your Email...",Toast.LENGTH_SHORT).show()
            }else if (binding.edtPasswordRegis.text.isEmpty())
            {
                Toast.makeText(this,"Enter your Password...",Toast.LENGTH_SHORT).show()
            }else if (binding.edtPhoneRegis.text.isEmpty())
            {
                Toast.makeText(this,"Enter your Phone...",Toast.LENGTH_SHORT).show()
            }else{
                var name = binding.edtNameRegis.text.toString()
                var email = binding.edtEmailRegis.text.toString()
                var password = binding.edtPasswordRegis.text.toString()
                var phone = binding.edtPhoneRegis.text.toString()
                signup(name, email, password, phone)
            }


        }

        binding.tvBackLogin.setOnClickListener {
            onBackPressed()
        }

    }
    private fun signup(name :String ,email :String , password :String , phone :String)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    AddUserToDatabase(name,email,mAuth.currentUser?.uid!! , password , phone)

                    val i = Intent(this@register,home::class.java)
                    startActivity(i)

                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@register,"some error", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun AddUserToDatabase(name:String , email :String , uid:String , password: String , phone: String)
    {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(name).setValue(User(name,email,uid , password , phone))

    }
}