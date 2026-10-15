package com.tecsup.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var btnLogout: MaterialButton
    private lateinit var optionInfo: View
    private lateinit var optionNotifications: View
    private lateinit var optionFavorites: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupToolbar()
        setupListeners()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        btnLogout = findViewById(R.id.btn_logout)
        optionInfo = findViewById(R.id.layout_option_info)
        optionNotifications = findViewById(R.id.layout_option_notifications)
        optionFavorites = findViewById(R.id.layout_option_favorites)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.profile_title)
    }

    private fun setupListeners() {
        optionInfo.setOnClickListener {
            Toast.makeText(this, R.string.profile_option_info, Toast.LENGTH_SHORT).show()
        }

        optionNotifications.setOnClickListener {
            Toast.makeText(this, R.string.profile_option_notifications, Toast.LENGTH_SHORT).show()
        }

        optionFavorites.setOnClickListener {
            Toast.makeText(this, R.string.profile_option_favorites, Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_profile_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_privacy -> {
                startActivity(Intent(this, PrivacyActivity::class.java))
                true
            }
            R.id.action_contacts -> {
                startActivity(Intent(this, ContactsActivity::class.java))
                true
            }
            R.id.action_central -> {
                Toast.makeText(this, R.string.toast_central, Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}