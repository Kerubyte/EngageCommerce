package com.kerubyte.engagecommerce.presentation.ui.fragment.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.application.utils.Status
import com.kerubyte.engagecommerce.databinding.FragmentTitleBinding
import com.kerubyte.engagecommerce.presentation.ui.RootFragment
import com.kerubyte.engagecommerce.presentation.ui.adapter.TitleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TitleFragment : RootFragment() {

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
        setupObserver()
        setUpRecycler()

        return binding.root
    }

    private fun setUpRecycler() {

        titleAdapter = TitleAdapter()
        binding.recyclerTitleFragment.apply {
            adapter = titleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObserver() {
        titleViewModel.products.observe(viewLifecycleOwner, {

            when (it.status) {

                Status.LOADING -> {
                    showProgressBar()
                }
                Status.SUCCESS -> {
                    hideProgressBar()
                    titleAdapter.differ.submitList(it.data)
                }
                Status.ERROR -> {
                    hideProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.progressBarTitleFragment.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBarTitleFragment.visibility = View.VISIBLE
    }
}