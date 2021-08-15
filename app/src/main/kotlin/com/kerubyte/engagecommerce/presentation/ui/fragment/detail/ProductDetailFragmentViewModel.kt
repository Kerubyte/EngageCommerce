package com.kerubyte.engagecommerce.presentation.ui.fragment.detail

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.application.repo.ProductRepository
import com.kerubyte.engagecommerce.application.utils.Resource
import com.kerubyte.engagecommerce.application.utils.Status
import com.kerubyte.engagecommerce.domain.model.local.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailFragmentViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val productUid = savedStateHandle.get<String>("productUid")

    private val _currentProduct = MutableLiveData<Resource<Product>>()
    val currentProduct: LiveData<Resource<Product>>
        get() = _currentProduct


    private fun getSingleProduct() {

        _currentProduct.value = Resource(Status.LOADING, null, null)

        viewModelScope.launch {
            productUid?.let {
                val result = productRepository.getSingleProduct(it)
                _currentProduct.postValue(result)
            }
        }
    }

    init {
        getSingleProduct()
    }
}