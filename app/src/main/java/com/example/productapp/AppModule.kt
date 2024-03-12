package com.example.productapp

import com.example.productapp.RepositoryPack.ProductRepository
import com.example.productapp.ViewModelPack.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ProductViewModel(get()) }
}