package com.example.engagecommerce.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.databinding.FragmentDetailProductBinding
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName

@ScreenName(name = "Detail")
class ProductDetailFragment : RootFragment() {

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var viewModelFactory: ProductDetailViewModelFactory
    private lateinit var binding: FragmentDetailProductBinding

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
        setAnimation()

        viewModelFactory = ProductDetailViewModelFactory(
            ProductDetailFragmentArgs
                .fromBundle(requireArguments())
                .productUid
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductDetailViewModel::class.java)

        viewModel.navigate.observe(viewLifecycleOwner, {
            if (it) navigateToLogin()
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }
}