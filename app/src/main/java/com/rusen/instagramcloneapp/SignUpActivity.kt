package com.rusen.instagramcloneapp

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.rusen.instagramcloneapp.Models.User
import com.rusen.instagramcloneapp.databinding.ActivitySignUpBinding
import com.rusen.instagramcloneapp.utils.USER_NODE
import com.rusen.instagramcloneapp.utils.USER_PROFILE_FOLDER
import com.rusen.instagramcloneapp.utils.uploadImage
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it == null) {

                } else {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val text = "<font color=#FF000000>Already have an Account</font> <font color=#1E88E5>Login ?</font>"
        binding.tvLogin.setText(Html.fromHtml(text))
        user = User()
        if (intent.hasExtra("MODE")){
            if (intent.getIntExtra("MODE", -1)==1){
                binding.btnSignUp.text ="Update Profile"

                com.google.firebase.Firebase.firestore.collection(USER_NODE).document(com.google.firebase.Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user= it.toObject<User>()!!
                        if (!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                    }
            }
        }
        binding.btnSignUp.setOnClickListener {
            if (intent.hasExtra("MODE")){
                if (intent.getIntExtra("MODE", -1)==1){
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                        startActivity(
                            Intent(
                                this@SignUpActivity,
                                HomeActivity::class.java
                            )
                        )
                        finish()
                    }
                }
            }else{

            }
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
                        with(binding) {
                            user.name = tvName.editText?.text.toString()
                            user.password = tvPassword.editText?.text.toString()
                            user.email = tvEmail.editText?.text.toString()
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    startActivity(
                                        Intent(
                                            this@SignUpActivity,
                                            HomeActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                        }
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
        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.tvLogin.setOnClickListener {
            startActivity(
                Intent(
                    this@SignUpActivity,
                    LoginActivity::class.java
                )
            )
            finish()
        }
    }
}