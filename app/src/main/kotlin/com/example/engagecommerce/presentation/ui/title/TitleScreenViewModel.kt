package com.example.engagecommerce.presentation.ui.title

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.database.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TitleScreenViewModel
@Inject
constructor(
    repository: ProductRepository
) : ViewModel() {

    val products = repository.getProducts()
}