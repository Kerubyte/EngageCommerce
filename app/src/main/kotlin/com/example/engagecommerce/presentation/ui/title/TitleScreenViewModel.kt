package com.example.engagecommerce.presentation.ui.title

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.database.ProductRepository
import com.example.engagecommerce.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TitleScreenViewModel
@Inject
constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val products = repository.getProducts()

    fun getSingleProduct(product: Product): String {
        val productUid = repository.getSingleProduct(product.uid.toString())
        return productUid.value.toString()
    }
}