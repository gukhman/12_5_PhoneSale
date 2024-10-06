package com.example.phonesale

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

        phoneCatalog = findViewById(R.id.phoneCatalog)
        phoneName = findViewById(R.id.phoneName)
        phonePrice = findViewById(R.id.phonePrice)
        addButton = findViewById(R.id.edit)

        // Получаем список телефонов из MainActivity
        val receivedList = intent.getSerializableExtra("phoneList") as? ArrayList<Phone>
        if (receivedList != null) {
            phoneList.addAll(receivedList)
        }
        phoneAdapter = PhoneAdapter(this, phoneList)
        phoneCatalog.adapter = phoneAdapter
        koenig.isChecked = true
        filterPhoneList(Cities.КАЛИНИНГРАД)

        koenig.setOnClickListener {
            filterPhoneList(Cities.КАЛИНИНГРАД)
        }

        zelik.setOnClickListener {
            filterPhoneList(Cities.ЗЕЛЕНОГРАДСК)
        }

        phoneCatalog.setOnItemClickListener { _, _, position, _ ->
            val selectedPhone = phoneAdapter.getItem(position) as Phone
            showDeleteDialog(selectedPhone)
        }

        addButton.setOnClickListener {
            val name = phoneName.text.toString()
            val price = phonePrice.text.toString()
            val city = if (koenig.isChecked) Cities.КАЛИНИНГРАД else Cities.ЗЕЛЕНОГРАДСК

            if (name.isNotEmpty() && price.isNotEmpty()) {
                val newPhone = Phone(name, price, city)
                phoneList.add(newPhone)
                phoneAdapter.notifyDataSetChanged()

                phoneName.text.clear()
                phonePrice.text.clear()

                val resultIntent = Intent()
                resultIntent.putExtra("updatedPhoneList", phoneList)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    //override fun on

    private fun showDeleteDialog(phone: Phone) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Подтверждение удаления")
        builder.setMessage("Удалить ${phone.name} из каталога города ${phone.city}?")
        builder.setNegativeButton("Да") { dialog, _ ->
            phoneList.remove(phone)
            phoneAdapter.notifyDataSetChanged()
            dialog.dismiss()
            val resultIntent = Intent()
            resultIntent.putExtra("updatedPhoneList", phoneList)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        builder.setPositiveButton("Нет") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun filterPhoneList(city: Cities) {
        val filteredList = phoneList.filter { it.city == city } as ArrayList<Phone>
        phoneAdapter.updateList(filteredList)
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