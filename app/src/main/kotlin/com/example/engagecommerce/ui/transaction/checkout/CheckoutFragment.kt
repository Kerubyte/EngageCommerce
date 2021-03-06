package com.example.engagecommerce.ui.transaction.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.FragmentCheckoutBinding
import com.example.engagecommerce.utils.Utils
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName

@ScreenName(name = "Checkout")
class CheckoutFragment : RootFragment(), View.OnClickListener {

    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var viewModel: CheckoutViewModel
    private lateinit var viewModelFactory: CheckoutViewModelFactory
    private lateinit var user: LiveData<User>
    private lateinit var cartValueBundle: String

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
        
        cartValueBundle = CheckoutFragmentArgs
            .fromBundle(requireArguments())
            .cartValue

        viewModelFactory = CheckoutViewModelFactory(cartValueBundle)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CheckoutViewModel::class.java)

        viewModel.user?.observe(viewLifecycleOwner, {
            bindUserData(it)
        })

        binding.textTotalAmountToPay.text = cartValueBundle
        user = viewModel.user!!

        binding.buttonOrder.setOnClickListener(this)
        UserCom.getInstance().trackScreen(this)

        return binding.root
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.buttonOrder -> {
                val userCart = user.value?.cart
                viewModel.createOrderFromCart(userCart, cartValueBundle)
                viewModel.clearUserCart()
                restartMainActivity()
            }
        }
    }

    private fun bindUserData(user: User) {
        val locale = Utils.locale

        binding.textFirstNameValue.text = user.firstName?.capitalize(locale)
        binding.textLastNameValue.text = user.lastName?.capitalize(locale)
    }
}