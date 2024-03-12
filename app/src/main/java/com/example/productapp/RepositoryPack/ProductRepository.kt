package com.example.productapp.RepositoryPack

import com.example.productapp.Model.ProductRequest
import com.example.productapp.Server.ApiService

class ProductRepository(private val apiService: ApiService) {

    suspend fun addProduct(product: ProductRequest) = apiService.addProduct(product)
}