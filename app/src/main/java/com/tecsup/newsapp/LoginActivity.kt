package com.tecsup.newsapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnEnter: MaterialButton
    private lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnEnter = findViewById(R.id.btn_enter)
        btnRegister = findViewById(R.id.btn_register)
    }

    private fun setupListeners() {
        btnEnter.setOnClickListener {
            performLogin()
        }

        btnRegister.setOnClickListener {
            Toast.makeText(this, R.string.login_toast_register_simulated, Toast.LENGTH_SHORT).show()
        }
    }

    private fun performLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty()) {
            etEmail.error = getString(R.string.login_error_empty_email)
            etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = getString(R.string.login_error_invalid_email)
            etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPassword.error = getString(R.string.login_error_empty_password)
            etPassword.requestFocus()
            return
        }

        if (password.length < 6) {
            etPassword.error = getString(R.string.login_error_short_password)
            etPassword.requestFocus()
            return
        }

        // Explicit Intent: Login -> Noticias
        val intent = Intent(this, NewsActivity::class.java).apply {
            putExtra(EXTRA_EMAIL, email)
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
    }
}