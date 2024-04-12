package com.example.todoapp.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.permissionx.guolindev.PermissionX


class SignIn : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedpreferences: SharedPreferences
    private var email_sh: String? = null
    private var password_sh: String? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        email_sh = sharedpreferences.getString("EMAIL_KEY", null)
        password_sh = sharedpreferences.getString("PASSWORD_KEY", null)

        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        binding.signUpTv.setOnClickListener {
            val intent = Intent(applicationContext, SignUp::class.java)
            startActivity(intent)
        }
        auth = FirebaseAuth.getInstance()

        binding.SignInBtn.setOnClickListener {
            login()
        }
        binding.forgotPassword.setOnClickListener{
            val intent=Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }


    }

    private fun login() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                val editor = sharedpreferences.edit()

                editor.putString(EMAIL_KEY, email)
                editor.putString(PASSWORD_KEY, password)

                // to save our data with key and value.
                editor.apply()
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val intent=Intent(this,UpdateTasks::class.java)
                startActivity(intent)
            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }
}