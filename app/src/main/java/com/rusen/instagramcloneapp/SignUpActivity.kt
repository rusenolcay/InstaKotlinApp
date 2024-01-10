package com.rusen.instagramcloneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.rusen.instagramcloneapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnSignUp.setOnClickListener {
            if (binding.tvName.editText?.text.toString().equals("") or
                binding.tvEmail.editText?.text.toString().equals("") or
                binding.tvPassword.editText?.text.toString().equals("")
            ) {

                Toast.makeText(
                    this@SignUpActivity,
                    "Please fill the all Information",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.tvEmail.editText?.text.toString(),
                    binding.tvPassword.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Login Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            result.exception?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }
    }
}