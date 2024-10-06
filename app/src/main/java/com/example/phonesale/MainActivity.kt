package com.example.phonesale

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var cities: RadioGroup
    private lateinit var koenig: RadioButton
    private lateinit var zelik: RadioButton

    private lateinit var priceList: Button
    private lateinit var saleStatistics: Button
    private lateinit var service: Button

    private lateinit var phoneCatalog: ListView
    private lateinit var phoneAdapter: PhoneAdapter

    private val phoneList = arrayListOf(
        Phone("Iphone 16", "150000", Cities.КАЛИНИНГРАД),
        Phone("Iphone 16", "145000", Cities.ЗЕЛЕНОГРАДСК),
        Phone("Samsung S24", "130000", Cities.КАЛИНИНГРАД),
        Phone("Samsung S24", "126000", Cities.ЗЕЛЕНОГРАДСК),
        Phone("Xiaomi 14", "90000", Cities.КАЛИНИНГРАД),
        Phone("Xiaomi 14", "87000", Cities.ЗЕЛЕНОГРАДСК)
    )
    private val phoneSales = arrayListOf<Phone>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        phoneCatalog = findViewById(R.id.phoneСatalog)
        phoneAdapter = PhoneAdapter(this, phoneList)
        phoneCatalog.adapter = phoneAdapter

        cities = findViewById(R.id.cities)
        koenig = findViewById(R.id.koenig)
        koenig.isChecked = true
        filterPhoneList(Cities.КАЛИНИНГРАД)
        zelik = findViewById(R.id.zelik)

        priceList = findViewById(R.id.add)
        saleStatistics = findViewById(R.id.saleStatistics)
        service = findViewById(R.id.service)

        koenig.setOnClickListener {
            filterPhoneList(Cities.КАЛИНИНГРАД)
        }

        zelik.setOnClickListener {
            filterPhoneList(Cities.ЗЕЛЕНОГРАДСК)
        }

        phoneCatalog.setOnItemClickListener { _, _, position, _ ->
            val selectedPhone = phoneAdapter.getItem(position) as Phone
            showPurchaseDialog(selectedPhone)
        }

        priceList.setOnClickListener {
            val intent = Intent(this, PriceList::class.java)
            intent.putExtra("phoneList", phoneList)
            startActivityForResult(intent, REQUEST_CODE_EDIT)
        }

        saleStatistics.setOnClickListener {
            val intent = Intent(this, SaleStatistics::class.java)
            startActivity(intent)
        }

        service.setOnClickListener {
            val intent = Intent(this, Service::class.java)
            startActivity(intent)
        }

    }

    private fun showPurchaseDialog(phone: Phone) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Подтверждение покупки")
        builder.setMessage("Покупаем ${phone.name} за ${phone.price}?")
        builder.setNegativeButton("Да") { dialog, _ ->
            phoneSales.add(phone)
            dialog.dismiss()
            val rootView = findViewById<View>(R.id.main)
            Snackbar.make(rootView, "Телефон куплен!", Snackbar.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            val updatedList = data?.getSerializableExtra("updatedPhoneList") as? ArrayList<Phone>
            if (updatedList != null) {
                phoneList.clear()
                phoneList.addAll(updatedList)
                phoneAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_EDIT = 1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_exit) finishAffinity()
        return true
    }
}