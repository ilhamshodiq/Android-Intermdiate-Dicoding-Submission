package com.the12smb.submissionstoryapp.view.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.the12smb.submissionstoryapp.databinding.ActivityLoginBinding
import com.the12smb.submissionstoryapp.view.main.MainViewModel
import com.the12smb.submissionstoryapp.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var LoginViewModel: MainViewModel
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupAction()
    }

    private fun setupView() {
        TODO("Not yet implemented")
    }

    private fun setupViewModel() {
        TODO("Not yet implemented")
    }

    private fun setupAction() {
        binding.registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (email.isEmpty()) {
                binding.emailEditText.error = "Email tidak boleh kosong"
            } else if (password.isEmpty()) {
                binding.passwordEditText.error = "Password tidak boleh kosong"
            } else {
                LoginViewModel.login(email, password)
            }
        }
    }
}