package com.example.engagecommerce.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.engagecommerce.R
import com.example.engagecommerce.databinding.FragmentDetailProductBinding
import com.example.engagecommerce.infrastructure.RootFragment
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = "Detail")
class ProductDetailFragment : RootFragment() {

    private lateinit var binding: FragmentDetailProductBinding
    private val viewModel: ProductDetailViewModel by viewModels()

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

        viewModel.navigate.observe(viewLifecycleOwner, {
            if (it) navigateToLogin()
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }
}