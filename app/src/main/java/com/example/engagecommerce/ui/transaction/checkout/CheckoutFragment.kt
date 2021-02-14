package com.example.engagecommerce.ui.transaction.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.FragmentCheckoutBinding
import com.example.engagecommerce.databinding.FragmentCheckoutBindingImpl
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import java.util.*

@ScreenName(name = "Checkout")
class CheckoutFragment : RootFragment() {

    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var viewModel: CheckoutViewModel
    private lateinit var viewModelFactory: CheckoutViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_checkout,
            container,
            false
        )

        val cartValueBundle = CheckoutFragmentArgs
            .fromBundle(requireArguments())
            .cartValue

        viewModelFactory = CheckoutViewModelFactory(cartValueBundle)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CheckoutViewModel::class.java)

        viewModel.user?.observe(viewLifecycleOwner, {
            bindUserData(it)
        })

        binding.textTotalAmountToPay.text = cartValueBundle

        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    private fun bindUserData(user: User) {

        val locale = Locale.getDefault()
        binding.textFirstNameValueOrder.text = user.firstName?.capitalize(locale)
        binding.textLastNameValueOrder.text = user.lastName?.capitalize(locale)
        binding.textEmailValueOrder.text = user.email?.capitalize(locale)

    }
}