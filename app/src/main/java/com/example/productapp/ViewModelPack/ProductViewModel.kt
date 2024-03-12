package com.example.productapp.ViewModelPack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.productapp.Model.ProductRequest
import com.example.productapp.Model.ProductResponse
import com.example.productapp.RepositoryPack.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _productResponse = MutableLiveData<ProductResponse>()
    val productResponse: LiveData<ProductResponse> get() = _productResponse

    fun addProduct(product: ProductRequest) {
        viewModelScope.launch {
            try {
                val response = productRepository.addProduct(product)
                _productResponse.value = response
            } catch (e: Exception) {
                // Handle failure
            }
        }
    }
}