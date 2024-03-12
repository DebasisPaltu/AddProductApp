package com.example.productapp.Server

import android.telecom.Call
import com.example.productapp.Model.ProductDataItem
import retrofit2.http.GET

interface ApiServer {


    @GET("get")
    fun getProduct(): retrofit2.Call<List<ProductDataItem>>
}