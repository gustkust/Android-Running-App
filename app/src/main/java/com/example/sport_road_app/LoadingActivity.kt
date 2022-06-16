package com.example.sport_road_app

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.content.Intent

class LoadingActivity : AppCompatActivity() {
    private lateinit var  img: ImageView
    private lateinit var  isAnimation: AnimationDrawable



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        img = findViewById(R.id.imageView)
        img.setImageResource(R.drawable.animation_item)
        getSupportActionBar()?.hide()

        isAnimation = img.drawable as AnimationDrawable
        isAnimation.start()

        val thread = Thread(){
            run{
                Thread.sleep(5000)
            }
            runOnUiThread(){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        thread.start()
    }
}