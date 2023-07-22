package com.vision.tic_tac_toe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vision.tic_tac_toe.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
   private lateinit var auth:FirebaseAuth
   private lateinit var binding: ActivityLoginBinding
   private lateinit var googleSignInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        googleSignInClient=GoogleSignIn.getClient(this,gso)

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
        
        binding.google.setOnClickListener {

         val signInClient=googleSignInClient.signInIntent
         launcher.launch(signInClient)

        }
    }
private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult())
{
    result->
      if(result.resultCode== Activity.RESULT_OK)
      {
          val task=GoogleSignIn.getSignedInAccountFromIntent((result.data))
          if(task.isSuccessful)
          {
              val account:GoogleSignInAccount?=task.result
              val credential=GoogleAuthProvider.getCredential(account?.idToken,null)
              auth.signInWithCredential(credential).addOnCompleteListener {
                  if(it.isSuccessful){
                      Toast.makeText(this,"Done",Toast.LENGTH_LONG).show()
                      startActivity(Intent(this,MainActivity::class.java))
                  }
                  else
                  {
                      Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
                  }
              }
          }
      }
}
//This will take the existting user to the manin page if the user have already created and account
    override fun onStart() {
        super.onStart()
        if(auth.currentUser!=null)
        {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}