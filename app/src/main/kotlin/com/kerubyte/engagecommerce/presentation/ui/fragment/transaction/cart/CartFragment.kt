package com.kerubyte.engagecommerce.presentation.ui.fragment.transaction.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentCartBinding
import com.kerubyte.engagecommerce.infrastructure.util.navigateWithArgs
import com.kerubyte.engagecommerce.infrastructure.util.setAnimation
import com.kerubyte.engagecommerce.infrastructure.util.showErrorSnackbar
import com.kerubyte.engagecommerce.presentation.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val viewModel: CartFragmentViewModel by viewModels()
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cart,
            container,
            false
        )

        setAnimation()
        setBindings()
        subscribeObserver()
        setupRecycler()
        setOnItemClickListener()

        return binding.root
    }

    private fun setupRecycler() {

        cartAdapter = CartAdapter()
        binding.recyclerCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun subscribeObserver() {

        observeUserCart()
        observeNavigation()
    }

    private fun observeUserCart() {

        viewModel.productsInCart.observe(viewLifecycleOwner, {

            when (it) {

                is com.kerubyte.engagecommerce.infrastructure.util.Result.Success -> {
                    hideProgressBar()
                    cartAdapter.differ.submitList(it.data)

                }
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.AuthenticationError -> {
                    hideProgressBar()
                    showErrorSnackbar(requireView(), R.string.authentication_error)
                }

                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.NetworkError -> {
                    hideProgressBar()
                    showErrorSnackbar(requireView(), R.string.network_error)                }
            }
        })
    }

    private fun observeNavigation() {

        viewModel.navigate.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                val args = binding.textCartTotalValue.text.toString()
                navigateWithArgs(
                    CartFragmentDirections.actionCartFragmentToCheckoutFragment(args)
                )
            }
        })
    }

    private fun setBindings() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setOnItemClickListener() {
        cartAdapter.setOnItemClickListener { product ->
            viewModel.handleRemoveFromCart(product.uid)
        }
    }

    private fun hideProgressBar() {
        binding.progressRecyclerCart.visibility = View.INVISIBLE
    }
}