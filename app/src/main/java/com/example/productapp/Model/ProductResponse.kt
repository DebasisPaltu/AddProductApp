package com.example.productapp.Model

data class ProductResponse(val message: String,
                           val product_details: ProductDetails,
                           val product_id: Int,
                           val success: Boolean)
