package com.example.phonesale

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PriceList : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var cities: RadioGroup
    private lateinit var koenig: RadioButton
    private lateinit var zelik: RadioButton
    private lateinit var phoneCatalog: ListView
    private lateinit var phoneAdapter: PhoneAdapter
    private lateinit var phoneName: EditText
    private lateinit var phonePrice: EditText
    private lateinit var addButton: Button
    private val phoneList = arrayListOf<Phone>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_price_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Настройка Action Bar с кнопкой "Назад"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cities = findViewById(R.id.cities)
        koenig = findViewById(R.id.koenig)
        zelik = findViewById(R.id.zelik)
        phoneCatalog = findViewById(R.id.phoneСatalog)
        phoneName = findViewById(R.id.phoneName)
        phonePrice = findViewById(R.id.phonePrice)
        addButton = findViewById(R.id.add)

        // Получаем список телефонов из MainActivity
        val receivedList = intent.getSerializableExtra("phoneList") as? ArrayList<Phone>
        if (receivedList != null) {
            phoneList.addAll(receivedList)
        }
        phoneAdapter = PhoneAdapter(this, phoneList)
        phoneCatalog.adapter = phoneAdapter

        addButton.setOnClickListener{

        }
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