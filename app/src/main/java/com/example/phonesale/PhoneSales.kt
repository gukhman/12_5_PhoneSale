package com.example.phonesale

data class PhoneSales(val name: String, val count: Int, val sum: Long) {
    override fun toString(): String {
        return "$name: $count шт. на сумму $sum\n"
    }
}