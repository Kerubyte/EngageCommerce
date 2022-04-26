package com.kerubyte.engagecommerce.presentation.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentDetailProductBinding
import com.kerubyte.engagecommerce.infrastructure.util.navigate
import com.kerubyte.engagecommerce.infrastructure.util.setAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

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
        subscribeObserver()

        return binding.root
    }

    private fun setBindings() {

        binding.viewModel = detailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun subscribeObserver() {

        detailViewModel.navigate.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                navigate(R.id.loginFragment)
            }
        }
    }
}