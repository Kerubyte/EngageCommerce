package com.example.engagecommerce.ui.transaction.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.adapter.CartAdapter
import com.example.engagecommerce.adapter.OnProductClick
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.databinding.FragmentCartBinding


class CartFragment : RootFragment(), OnProductClick {

    private lateinit var cartViewModel: CartFragmentViewModel
    private lateinit var binding: FragmentCartBinding
    private val adapter = CartAdapter(this)

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

        cartViewModel = CartFragmentViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCart.adapter = adapter

        cartViewModel.userCart?.observe(viewLifecycleOwner, { list ->
            adapter.setCartProducts(list)

            updateCart()
        })

        // Navigate to checkout passing cart value along
        binding.buttonToCheckout.setOnClickListener {
            val cartValue = binding.textCartQuantityValue.text
            navvvv(cartValue.toString())
        }
    }

    // TODO
    override fun onProductClick(product: Product, position: Int) {
        cartViewModel.removeFromCart(product)
        adapter.removeFromCart(product, position)

        updateCart()
    }

    // Bind cart quantity and value to view
    private fun updateCart() {
        val productsInCartList = adapter.cartList
        val cartValue = cartViewModel.calculateCartValue(productsInCartList)
        binding.textCartTotalValue.text = cartValue.toString()
        binding.textCartQuantityValue.text = productsInCartList.size.toString()
    }
}