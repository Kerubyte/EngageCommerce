package com.kerubyte.engagecommerce.presentation.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentDetailProductBinding
import com.kerubyte.engagecommerce.presentation.ui.RootFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : RootFragment() {

    private val detailViewModel: ProductDetailFragmentViewModel by viewModels()
    lateinit var binding: FragmentDetailProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_detail_product,
            container,
            false
        )

        setAnimation()
        setBindings()
        return binding.root
    }

    private fun setBindings() {

        binding.viewModel = detailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}