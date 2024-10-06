package com.example.phonesale

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SaleStatistics : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var statisticsET: TextView

    private val phoneSales = arrayListOf<Phone>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sale_statistics)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Настройка Action Bar с кнопкой "Назад"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        statisticsET = findViewById(R.id.statisticsET)

        val receivedList = intent.getSerializableExtra("phoneSales") as? ArrayList<Phone>
        if (receivedList != null) {
            phoneSales.addAll(receivedList)
        }
        // Группируем по Наименованию
        val groupedSales = phoneSales.groupBy { it.name }

        val result = mutableListOf<PhoneSales>()

        for ((name, sales) in groupedSales) {
            val totalAmount = sales.sumOf { it.price.toLong() } // Суммируем суммы
            val count = sales.size // Получаем количество
            result.add(PhoneSales(name, count, totalAmount))
        }

        var out = ""
        for (sale in result) {
            /*Log.d(
                "phoneSales",
                "Наименование: ${sale.name}, Количество: ${sale.count}, Сумма: ${sale.sum}"
            )*/
            out += sale
        }
        statisticsET.text = out
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