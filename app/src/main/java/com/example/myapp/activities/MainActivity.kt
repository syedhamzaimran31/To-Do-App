package com.example.myapp.activities

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }

    private lateinit var sharedpreferences: SharedPreferences
    private var email_sh: String? = null
    private var password_sh: String? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

//        email_sh = sharedpreferences.getString(EMAIL_KEY, null)
//        password_sh =sharedpreferences.getString(PASSWORD_KEY, null)

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

                        if (email_sh != null && password_sh != null) {
                            val i = Intent(this@MainActivity, ProfileActivity::class.java)
                            startActivity(i)
                        } else {
                            val intent =
                                Intent(applicationContext, OnboardingExample1Activity::class.java)
                            startActivity(intent)
                        }


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