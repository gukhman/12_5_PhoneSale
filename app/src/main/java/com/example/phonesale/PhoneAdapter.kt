package com.example.phonesale

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PhoneAdapter(private val context: Context, private var dataSource: ArrayList<Phone>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_phone, parent, false)

        val nameTextView = rowView.findViewById<TextView>(R.id.phone_name)
        val priceTextView = rowView.findViewById<TextView>(R.id.phone_price)
        //val cityTextView = rowView.findViewById<TextView>(R.id.phone_city)

        val phone = getItem(position) as Phone

        nameTextView.text = phone.name
        priceTextView.text = phone.price
        //cityTextView.text = phone.city.toString()

        return rowView
    }

    fun updateList(newList: ArrayList<Phone>) {
        dataSource = newList
        notifyDataSetChanged()
    }
}