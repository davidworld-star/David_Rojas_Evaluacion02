package com.tecsup.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewsActivity : AppCompatActivity() {

    private lateinit var btnProfile: ImageButton
    private lateinit var fabShare: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        btnProfile = findViewById(R.id.btn_profile)
        fabShare = findViewById(R.id.fab_share)
    }

    private fun setupListeners() {
        // Explicit Intent: Noticias -> Perfil
        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Implicit Intent: Compartir noticia
        fabShare.setOnClickListener {
            shareNews()
        }
    }

    private fun shareNews() {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.news_share_subject))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.news_share_content))
        }
        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.news_share_title))
        startActivity(shareIntent)
    }
}