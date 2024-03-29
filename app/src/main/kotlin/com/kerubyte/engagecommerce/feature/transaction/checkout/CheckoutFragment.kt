package com.kerubyte.engagecommerce.feature.transaction.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.common.util.Constants.SCREEN_CHECKOUT
import com.kerubyte.engagecommerce.common.util.Result
import com.kerubyte.engagecommerce.databinding.FragmentCheckoutBinding
import com.kerubyte.engagecommerce.common.util.restartMainActivity
import com.kerubyte.engagecommerce.common.util.setAnimation
import com.kerubyte.engagecommerce.common.util.showErrorSnackbar
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = SCREEN_CHECKOUT)
class CheckoutFragment : Fragment() {

    private val checkoutViewModel: CheckoutFragmentViewModel by viewModels()
    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var checkoutAdapter: CheckoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_checkout,
            container,
            false
        )

        setAnimation()
        setBindings()
        subscribeObserver()
        setupRecycler()
        UserCom.getInstance().trackScreen(this)

        return binding.root
    }

    private fun setupRecycler() {

        checkoutAdapter = CheckoutAdapter()
        binding.recyclerCheckout.apply {
            adapter = checkoutAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun subscribeObserver() {

        observeProductsInCart()
        observeNavigation()
    }

    private fun observeProductsInCart() {

        checkoutViewModel.productsInCart.observe(viewLifecycleOwner) {

            when (it) {

                is Result.Success -> {
                    hideProgressBar()
                    checkoutAdapter.differ.submitList(it.data)

                }
                is Result.Error.AuthenticationError -> {
                    hideProgressBar()
                    showErrorSnackbar(requireView(), R.string.authentication_error)
                }

                is Result.Error.NetworkError -> {
                    showErrorSnackbar(requireView(), R.string.network_error)
                }
            }
        }
    }

    private fun observeNavigation() {

        checkoutViewModel.navigate.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let {
                restartMainActivity()
            }
        }
    }

    private fun setBindings() {

        binding.checkoutFragment = this
        binding.checkoutViewModel = checkoutViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    fun verifyAndUpdateUserAddress() {

        val editStreet = binding.editAddressStreetName.text.toString().trim()
        val editPostCode = binding.editAddressPostCode.text.toString().trim()
        val editCity = binding.editAddressCity.text.toString().trim()
        val editCountry = binding.editAddressCountry.text.toString().trim()

        checkoutViewModel.updateUserAddress(editStreet, editPostCode, editCity, editCountry)
    }

    fun showEditAddress() {

        binding.groupTextAddress.isVisible = false
        binding.groupEditAddress.isVisible = true
    }

    private fun hideProgressBar() {

        binding.progressRecyclerCheckout.isVisible = false
    }
}