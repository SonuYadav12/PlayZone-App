package com.vision.tic_tac_toe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vision.tic_tac_toe.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
   private lateinit var auth:FirebaseAuth
   private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=Firebase.auth
        binding.button.setOnClickListener {

            auth.createUserWithEmailAndPassword(binding.Email.text.toString(),binding.Password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "Authentication SuccessFul.",
                            Toast.LENGTH_LONG,
                        ).show()

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_LONG).show()
                    }
                }
        }
        
        
    }
}