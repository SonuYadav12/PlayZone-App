package com.vision.tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed

class GameSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_splash)

       Handler(Looper.getMainLooper()).postDelayed(2000){
           val intent=Intent(this,Login::class.java)
           startActivity(intent)
       }


    }
}