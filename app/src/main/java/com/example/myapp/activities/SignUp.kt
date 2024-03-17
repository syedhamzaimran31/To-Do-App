package com.example.myapp.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    lateinit var userId: String
    private lateinit var db: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore


        val signUpBtn = binding.signUpBtn



        auth = Firebase.auth

        binding.signUpBtn.setOnClickListener {
            signUpUser()
        }
        binding.SignInTv.setOnClickListener {
            val intent = Intent(applicationContext, SignIn::class.java)
            startActivity(intent)
        }
        val auth = FirebaseAuth.getInstance()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun signUpUser() {
        val email = binding.email.text.toString()
        val pass = binding.password.text.toString()

        // check pass
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }


        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                userId = Firebase.auth.currentUser?.uid.toString()
                saveDataInFireStore()

            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveDataInFireStore() {
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val createdTime = System.currentTimeMillis()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        val documentId = UUID.randomUUID().toString()
        val user = hashMapOf(
            "id" to documentId,
            "userId" to userId,
            "createdAt" to "$createdTime, $current",
            "name" to name,
            "email" to email
        )

        db.collection("users").document(documentId)
            .set(user)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Document added with ID: $documentId")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document")
               }
    }
}