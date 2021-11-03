package com.kerubyte.engagecommerce.presentation.ui.fragment.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentTitleBinding
import com.kerubyte.engagecommerce.infrastructure.util.navigateWithArgs
import com.kerubyte.engagecommerce.infrastructure.util.setAnimation
import com.kerubyte.engagecommerce.infrastructure.util.showErrorSnackbar
import com.kerubyte.engagecommerce.presentation.adapter.TitleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TitleFragment : Fragment() {

    private val titleViewModel: TitleFragmentViewModel by viewModels()
    private lateinit var binding: FragmentTitleBinding
    private lateinit var titleAdapter: TitleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_title,
            container,
            false
        )

        setAnimation()
        subscribeObserver()
        setUpRecycler()
        setOnItemClickListener()

        return binding.root
    }

    private fun setUpRecycler() {

        titleAdapter = TitleAdapter()
        binding.recyclerTitleFragment.apply {
            adapter = titleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun subscribeObserver() {

        observeProducts()
    }

    private fun observeProducts() {

        titleViewModel.products.observe(viewLifecycleOwner, {

            when (it) {

                is com.kerubyte.engagecommerce.infrastructure.util.Result.Success -> {
                    hideProgressBar()
                    titleAdapter.differ.submitList(it.data)
                }
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.AuthenticationError -> {
                    hideProgressBar()
                    showErrorSnackbar(requireView(), R.string.authentication_error)
                }

                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.NetworkError -> {
                    hideProgressBar()
                    showErrorSnackbar(requireView(), R.string.network_error)
                }
            }
        })

    }

    private fun setOnItemClickListener() {
        titleAdapter.setOnItemClickListener { product ->
            openProductDetails(product.uid)
        }
    }

    private fun openProductDetails(productUid: String) {
        navigateWithArgs(
            TitleFragmentDirections.actionTitleFragmentToProductDetailFragment(productUid)
        )
    }

    private fun hideProgressBar() {
        binding.progressBarTitleFragment.visibility = View.INVISIBLE
    }
}