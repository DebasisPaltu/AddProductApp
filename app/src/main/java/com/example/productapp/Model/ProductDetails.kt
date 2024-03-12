package com.example.productapp.Model

data class ProductDetails( val productId: Int,
                           val productName: String,
                           val productType: String,
                           val price: Double,
                           val tax: Double,
                           val imageURLs: List<String>)
