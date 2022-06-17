package com.kerubyte.engagecommerce.feature.product.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.common.util.Constants.SCREEN_TITLE
import com.kerubyte.engagecommerce.common.util.Result
import com.kerubyte.engagecommerce.databinding.FragmentTitleBinding
import com.kerubyte.engagecommerce.common.util.navigateWithArgs
import com.kerubyte.engagecommerce.common.util.setAnimation
import com.kerubyte.engagecommerce.common.util.showErrorSnackbar
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = SCREEN_TITLE)
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
        UserCom.getInstance().trackScreen(this)

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

        titleViewModel.products.observe(viewLifecycleOwner) {

            when (it) {

                is Result.Success -> {
                    hideProgressBar()
                    titleAdapter.differ.submitList(it.data)
                }
                is Result.Error.AuthenticationError -> {
                    hideProgressBar()
                    showErrorSnackbar(requireView(), R.string.authentication_error)
                }

                is Result.Error.NetworkError -> {
                    hideProgressBar()
                    showErrorSnackbar(requireView(), R.string.network_error)
                }
            }
        }

    }

    private fun setOnItemClickListener() {
        titleAdapter.setOnItemClickListener { product ->
            openProductDetails(product.uid)
        }
    }

    private fun openProductDetails(productUid: String) {
        navigateWithArgs(
            //TitleFragmentDirections.actionTitleFragmentToProductDetailFragment(productUid)
        TitleFragmentDirections.actionTitleFragmentToProductDetailFragment(productUid)
        )
    }

    private fun hideProgressBar() {
        binding.progressBarTitleFragment.visibility = View.INVISIBLE
    }
}