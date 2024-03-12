package com.example.productapp.Server

import com.example.productapp.Model.ProductRequest
import com.example.productapp.Model.ProductResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/public/add")
    suspend fun addProduct(@Body product: ProductRequest): ProductResponse
}