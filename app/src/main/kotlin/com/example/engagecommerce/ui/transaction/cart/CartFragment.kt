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
import com.example.engagecommerce.utils.Utils
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType
import com.user.sdk.events.ScreenName

@ScreenName(name = "Cart")
class CartFragment : RootFragment(), OnProductClick, View.OnClickListener {

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
        setAnimation()

        cartViewModel = CartFragmentViewModel()
        binding.buttonToCheckout.setOnClickListener(this)
        UserCom.getInstance().trackScreen(this)

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
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.buttonToCheckout -> {
                val cartValue = binding.textCartTotalValue.text
                navigateToCheckout(cartValue.toString())
                cartViewModel.sendProductEvent(
                    adapter.cartList,
                    ProductEventType.CHECKOUT
                )
            }
        }
    }

    override fun onProductClick(product: Product, position: Int) {
        cartViewModel.removeFromCart(product)
        adapter.removeFromCart(product, position)

        updateCart()
    }

    private fun updateCart() {
        val productsInCartList = adapter.cartList
        val cartValue = cartViewModel.calculateCartValue(productsInCartList)
        binding.textCartTotalValue.text = Utils.formatPrice.format(cartValue)
        binding.textCartQuantityValue.text = productsInCartList.size.toString()
        checkIfCartIsEmpty(productsInCartList)
    }

    private fun setButtonState(state: Boolean) {
        val button = binding.buttonToCheckout
        button.isEnabled = state
    }

    private fun checkIfCartIsEmpty(list: ArrayList<Product>) {
        if (!list.isNullOrEmpty()) setButtonState(true) else setButtonState(false)
    }


}