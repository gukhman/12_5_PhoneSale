package com.example.phonesale

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class Service : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var phoneCatalog: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_service)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Настройка Action Bar с кнопкой "Назад"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        phoneCatalog = findViewById(R.id.phoneCatalog)
        val phoneList = intent.getStringArrayListExtra("phoneNames")
        val phoneNamesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, phoneList!!)
        phoneCatalog.adapter = phoneNamesAdapter

        phoneCatalog.setOnItemClickListener { _, _, position, _ ->
            val selectedPhone = phoneNamesAdapter.getItem(position)
            showServiceDialog(selectedPhone)
        }
    }

    private fun showServiceDialog(phone: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Обращение в сервис")
        builder.setMessage("Сдать в ремонт $phone?")
        builder.setNegativeButton("Да") { dialog, _ ->
            val rootView = findViewById<android.view.View>(android.R.id.content)
            Snackbar.make(rootView, "Ваш телефон передан в ремонт", Snackbar.LENGTH_LONG).show()
            dialog.dismiss()
        }
        builder.setPositiveButton("Нет") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Завершаем активность и возвращаемся к предыдущей
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}