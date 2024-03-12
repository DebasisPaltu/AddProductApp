package com.example.productapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.Adpter.ProductAdapter
import com.example.productapp.Fragment.AddProductBottomSheet
import com.example.productapp.Model.ProductDataItem
import com.example.productapp.Server.ApiServer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var productsAdapter: ProductAdapter
    private lateinit var apiService: ApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

      val  buttonAddProduct  = findViewById<Button>(R.id.buttonAddProduct)

        buttonAddProduct.setOnClickListener {
            val addProductDialog = AddProductBottomSheet()
            addProductDialog.show(supportFragmentManager, "addProductDialog")
        }



        recyclerView = findViewById(R.id.recyclerViewProducts)
        progressBar = findViewById(R.id.progressBar)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://app.getswipe.in/api/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiServer::class.java)
        productsAdapter = ProductAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProductActivity)
            adapter = productsAdapter
        }

        fetchData()
    }

    private fun fetchData() {
        progressBar.visibility = View.VISIBLE
        apiService.getProduct().enqueue(object : Callback<List<ProductDataItem>>{
            override fun onResponse(call: Call<List<ProductDataItem>>, response: Response<List<ProductDataItem>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val products = response.body()
                    products?.let {
                        productsAdapter.submitList(it)
                    }
                } else {
                    Toast.makeText(this@ProductActivity, "Failed to fetch products", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProductDataItem>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@ProductActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}