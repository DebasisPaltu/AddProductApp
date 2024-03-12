package com.example.productapp.Model

import java.io.File

data class ProductRequest( val productName: String,
                           val productType: String,
                           val price: Double,
                           val tax: Double,
                           val files: List<File>)
