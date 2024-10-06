package com.example.phonesale

import java.io.Serializable

data class Phone(val name: String, val price: String, val city: Cities) : Serializable