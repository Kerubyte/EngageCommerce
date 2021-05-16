package com.example.engagecommerce.presentation.ui.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engagecommerce.R
import com.example.engagecommerce.databinding.FragmentTitleScreenBinding
import com.example.engagecommerce.domain.model.Product
import com.example.engagecommerce.infrastructure.RootFragment
import com.example.engagecommerce.presentation.adapters.OnProductClick
import com.example.engagecommerce.presentation.adapters.ProductAdapter
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = "Home")
class TitleScreenFragment : RootFragment(), OnProductClick {

    private val titleScreenViewModel: TitleScreenViewModel by viewModels()
    private val productAdapter = ProductAdapter(this)
    private lateinit var binding: FragmentTitleScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_title_screen,
            container,
            false
        )
        setAnimation()
        trackScreen(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeObservers()
    }

    override fun onProductClick(product: Product, position: Int) {
        openProductDetails(product.uid)
    }

    private fun openProductDetails(productUid: String) {
        findNavController().navigate(
            TitleScreenFragmentDirections
                .actionTitleScreenFragmentToProductDetailFragment(productUid)
        )
    }

    private fun setupRecyclerView() {
        binding.recyclerTitleScreen.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun subscribeObservers() {
        titleScreenViewModel.products.observe(viewLifecycleOwner, { list ->
            productAdapter.setProducts(list)
        })
    }
}