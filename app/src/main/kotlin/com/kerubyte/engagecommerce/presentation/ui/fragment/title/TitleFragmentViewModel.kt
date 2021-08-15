package com.kerubyte.engagecommerce.presentation.ui.fragment.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.application.repo.ProductRepository
import com.kerubyte.engagecommerce.application.utils.Resource
import com.kerubyte.engagecommerce.application.utils.Status
import com.kerubyte.engagecommerce.domain.model.local.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleFragmentViewModel
@Inject
constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>>
        get() = _products

    private fun getAllProducts() {
        _products.postValue(Resource(Status.LOADING, null, null))

        viewModelScope.launch {
            val result = productRepository.getAllProducts()
            _products.postValue(result)
        }
    }

    init {
        getAllProducts()
    }
}