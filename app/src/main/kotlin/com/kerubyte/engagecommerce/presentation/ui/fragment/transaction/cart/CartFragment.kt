package com.kerubyte.engagecommerce.presentation.ui.fragment.transaction.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentCartBinding
import com.kerubyte.engagecommerce.infrastructure.util.Status
import com.kerubyte.engagecommerce.infrastructure.util.navigateWithArgs
import com.kerubyte.engagecommerce.presentation.adapter.CartAdapter
import com.kerubyte.engagecommerce.presentation.ui.RootFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : RootFragment() {

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
        viewModel.productsInCart.observe(viewLifecycleOwner, {

            when (it.status) {

                Status.LOADING -> {
                    //showProgressBar()
                }
                Status.SUCCESS -> {
                    //hideProgressBar()
                    cartAdapter.differ.submitList(it.data)

                }
                Status.ERROR -> {
                    //hideProgressBar()
                    //displayErrorLayout()
                }
            }
        })

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
            viewModel.removeFromCart(product.uid)
        }
    }
}