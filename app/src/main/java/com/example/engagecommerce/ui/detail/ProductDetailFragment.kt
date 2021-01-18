package com.example.engagecommerce.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.FragmentDetailProductBinding
import com.example.engagecommerce.repo.FirebaseCloud
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProductDetailFragment : RootFragment(), View.OnClickListener {


    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var viewModelFactory: ProductDetailViewModelFactory
    private lateinit var binding: FragmentDetailProductBinding
    private val repository = FirebaseCloud()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail_product,
            container,
            false
        )
        auth = Firebase.auth

        binding.buttonAddToCart.setOnClickListener(this)

        viewModelFactory = ProductDetailViewModelFactory(
            ProductDetailFragmentArgs
                .fromBundle(requireArguments())
                .productUid
        )

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductDetailViewModel::class.java)

        viewModel.product.observe(viewLifecycleOwner, {
            binding.textProductNameDetail.text = it?.name
            binding.textProductDescriptionDetail.text = it?.description
            binding.textProductPriceDetail.text = it?.price.toString()
            val image = binding.imageProductImageDetaills
            Glide.with(requireView())
                .load(it.imageUrl)
                .into(image)
        })

        viewModel.user?.observe(viewLifecycleOwner, {
            Log.d("viewModel", "${checkForProductInCart(it)}")
            val state = checkForProductInCart(it)
            setButtonState(state)
        })

        return binding.root
    }

    // Check if viewed product is already in cart
    private fun checkForProductInCart(currentUser: User): Boolean {
        val cart = currentUser.cart
        val productUid = ProductDetailFragmentArgs.fromBundle(requireArguments()).productUid

        return if (cart != null) productUid !in cart
        else true
    }

    // Enable or Disable add to cart button
    private fun setButtonState(state: Boolean) {

        val button = binding.buttonAddToCart
        button.isEnabled = state
        if (state) button.text = getString(R.string.add_to_cart_button)
        else button.text = getString(R.string.button_in_cart_text)
    }

    // Handle clicks in the fragment
    override fun onClick(view: View?) {
        when (view) {
            binding.buttonAddToCart ->
                if (auth.currentUser == null) {
                    navigateToLogin()
                } else {
                    repository.addToCart(
                        ProductDetailFragmentArgs
                            .fromBundle(requireArguments())
                            .productUid
                    )
                    setButtonState(false)
                    Log.d("viewModel2", "${viewModel.user?.value?.cart}")

                }
        }

    }
}