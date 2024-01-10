package com.rusen.instagramcloneapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rusen.instagramcloneapp.Models.User
import com.rusen.instagramcloneapp.databinding.ActivitySignUpBinding
import com.rusen.instagramcloneapp.utils.USER_NODE
import com.rusen.instagramcloneapp.utils.USER_PROFILE_FOLDER
import com.rusen.instagramcloneapp.utils.uploadImage

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User

    private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER){
                if (it==null){

                }else{
                    user.image=it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = User()
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
                        with(binding) {
                            user.name = tvName.editText?.text.toString()
                            user.password = tvPassword.editText?.text.toString()
                            user.email = tvEmail.editText?.text.toString()
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(this@SignUpActivity, "Login", Toast.LENGTH_SHORT)
                                        .show()
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
    }
}