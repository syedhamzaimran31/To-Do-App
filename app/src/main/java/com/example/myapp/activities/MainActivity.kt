package com.example.myapp.activities

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val mySplash = binding.splash
        val mySplash = findViewById<LottieAnimationView>(R.id.splash)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            mySplash?.let {
                it.visibility = View.VISIBLE
                it.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        Log.d("LottieDebug", "Animation started")
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        Log.d("LottieDebug", "Animation ended")
                        val intent =
                            Intent(applicationContext, OnboardingExample1Activity::class.java)
                        startActivity(intent)
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        Log.d("LottieDebug", "Animation canceled")
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        Log.d("LottieDebug", "Animation repeated")
                    }
                })
                it.playAnimation()
            }
        }, 1000)
    }
}