package com.tecsup.newsapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class ContactsActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var lvContacts: ListView

    private data class Contact(val name: String, val phone: String) {
        override fun toString(): String = "$name: $phone"
    }

    private val contactsList = listOf(
        Contact("Prensa Editorial", "+51 1 987987981"),
        Contact("Prensa Editorial", "+51 1 987987982"),
        Contact("Prensa Editorial", "+51 1 987987983"),
        Contact("Prensa Editorial", "+51 1 987987984"),
        Contact("Prensa Editorial", "+51 1 987987985"),
        Contact("Prensa Editorial", "+51 1 987987986"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacts)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupToolbar()
        setupListView()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        lvContacts = findViewById(R.id.lv_contacts)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.contacts_title)
    }

    private fun setupListView() {
        val adapter = ArrayAdapter(this, R.layout.item_contact, R.id.tv_contact_info, contactsList)
        lvContacts.adapter = adapter
        registerForContextMenu(lvContacts)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_profile_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_privacy -> {
                startActivity(Intent(this, PrivacyActivity::class.java))
                finish()
                true
            }
            R.id.action_contacts -> {
                true
            }
            R.id.action_central -> {
                Toast.makeText(this, R.string.toast_central, Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contact_context, menu)

        val info = menuInfo as? AdapterView.AdapterContextMenuInfo
        if ((info != null) && (info.position in contactsList.indices)) {
            val contact = contactsList[info.position]
            menu.setHeaderTitle(contact.toString())
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = (item.menuInfo as? AdapterView.AdapterContextMenuInfo) ?: return super.onContextItemSelected(item)
        if (info.position !in contactsList.indices) return super.onContextItemSelected(item)

        val contact = contactsList[info.position]
        return when (item.itemId) {
            R.id.action_call -> {
                makeCall(contact.phone)
                true
            }
            R.id.action_send_message -> {
                sendMessage(contact.phone)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun makeCall(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, "tel:$phone".toUri())
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, R.string.toast_no_app, Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendMessage(phone: String) {
        val intent = Intent(Intent.ACTION_SENDTO, "smsto:$phone".toUri()).apply {
            putExtra("sms_body", "Hola")
        }
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, R.string.toast_no_app, Toast.LENGTH_SHORT).show()
        }
    }
}