package com.kerubyte.engagecommerce.feature.product.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.common.domain.ProductRepository
import com.kerubyte.engagecommerce.common.domain.model.ProductModel
import com.kerubyte.engagecommerce.common.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleFragmentViewModel
@Inject
constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<Result<List<ProductModel>>>()
    val products: LiveData<Result<List<ProductModel>>>
        get() = _products

    private fun getAllProducts() {

        viewModelScope.launch {
            val result = productRepository.getAllProducts()
            _products.postValue(result)
        }
    }

    init {
        getAllProducts()
    }
}