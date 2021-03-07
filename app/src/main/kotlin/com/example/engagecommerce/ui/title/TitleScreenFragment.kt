package com.example.engagecommerce.ui.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.adapter.OnProductClick
import com.example.engagecommerce.adapter.ProductAdapter
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.databinding.FragmentTitleScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName

@ScreenName(name = "Home")
class TitleScreenFragment : RootFragment(), OnProductClick {

    private val titleScreenViewModel: TitleScreenViewModel by viewModels()
    private val productAdapter = ProductAdapter(this)
    private lateinit var binding: FragmentTitleScreenBinding
    private lateinit var auth: FirebaseAuth

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
        auth = Firebase.auth

        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerTitleScreen.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        titleScreenViewModel.products.observe(viewLifecycleOwner, { list ->
            productAdapter.setProducts(list)
        })
    }

    override fun onProductClick(product: Product, position: Int) {
        openProductDetails(product.uid!!)
    }

    private fun openProductDetails(productUid: String) {
        findNavController().navigate(
            TitleScreenFragmentDirections
                .actionTitleScreenFragmentToProductDetailFragment(productUid)
        )

    }
}