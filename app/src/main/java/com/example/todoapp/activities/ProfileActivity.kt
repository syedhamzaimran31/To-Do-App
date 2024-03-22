package com.example.todoapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var name: String
    private lateinit var email: String
    private var phoneNumber: Int? = null
    private lateinit var DOB: String
    private lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = binding.name.text.toString()
        email = binding.email.text.toString()
        DOB = binding.dob.text.toString()
        gender = binding.gender.text.toString()
    }
}