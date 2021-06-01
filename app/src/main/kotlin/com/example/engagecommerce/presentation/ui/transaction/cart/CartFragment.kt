package com.example.engagecommerce.presentation.ui.transaction.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engagecommerce.R
import com.example.engagecommerce.databinding.FragmentCartBinding
import com.example.engagecommerce.domain.model.Product
import com.example.engagecommerce.infrastructure.RootFragment
import com.example.engagecommerce.presentation.adapters.CartAdapter
import com.example.engagecommerce.presentation.adapters.OnProductClick
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = "Cart")
class CartFragment : RootFragment(), OnProductClick {

    private val cartViewModel: CartFragmentViewModel by viewModels()
    private lateinit var binding: FragmentCartBinding
    private val cartAdapter = CartAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cart,
            container,
            false
        )
        setAnimation()
        setBidings()
        setRecycler()
        subscribeObservers()
        trackScreen(this)

        return binding.root
    }

    private fun setRecycler() {
        binding.recyclerCart.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun subscribeObservers() {
        cartViewModel.userCart.observe(viewLifecycleOwner, { list ->
            cartAdapter.setCartProducts(list)
        })
    }

    override fun onProductClick(product: Product, position: Int) {
        cartViewModel.handleRemoveFromCart(product)
        cartAdapter.removeFromCart(product, position)

       // updateCart()
    }
/*
    private fun updateCart() {
        val productsInCartList = cartAdapter.cartList
        enableButtonIfCartIsNotEmpty(productsInCartList)
    }

    private fun enableButtonIfCartIsNotEmpty(list: List<Product>) {
        val button = binding.buttonToCheckout
        button.isEnabled = !list.isNullOrEmpty()
    }*/

    private fun setBidings() {
        binding.cartViewModel = cartViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

}