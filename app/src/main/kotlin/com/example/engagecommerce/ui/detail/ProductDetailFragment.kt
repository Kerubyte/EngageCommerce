package com.example.engagecommerce.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.FragmentDetailProductBinding
import com.example.engagecommerce.repo.FirebaseCloud
import com.example.engagecommerce.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType
import com.user.sdk.events.ScreenName

@ScreenName(name = "Detail")
class ProductDetailFragment : RootFragment(), View.OnClickListener {

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var viewModelFactory: ProductDetailViewModelFactory
    private lateinit var binding: FragmentDetailProductBinding
    private lateinit var repository: FirebaseCloud
    private lateinit var auth: FirebaseAuth
    private var snapshotListenerRegistration: ListenerRegistration? = null

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
        auth = Firebase.auth
        repository = FirebaseCloud()

        viewModelFactory = ProductDetailViewModelFactory(
            ProductDetailFragmentArgs
                .fromBundle(requireArguments())
                .productUid
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductDetailViewModel::class.java)

        viewModel.product.observe(viewLifecycleOwner, {
            bindProductData(it)
            viewModel.sendProductEvent(ProductEventType.DETAIL)
        })

        snapshotListenerRegistration = viewModel.currentUser?.addSnapshotListener { querySnapshot, error ->
            error?.let {
                Log.d("snapshotProduct", it.message.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val user = it.toObject<User>()
                val state = viewModel.checkForProductInCart(user?.cart)
                enableAddToCartButton(state)
            }
        }
        binding.buttonAddToCart.setOnClickListener(this)
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        snapshotListenerRegistration?.remove()
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.buttonAddToCart ->
                if (auth.currentUser == null) {
                    navigateToLogin()
                } else {
                    viewModel.addToCart()
                    viewModel.sendProductEvent(
                        ProductEventType.ADD_TO_CART
                    )
                }
        }
    }

    private fun bindProductData(product: Product) {
        binding.textProductNameDetail.text = product.name
        binding.textProductDescriptionDetail.text = product.description
        binding.textProductPriceDetail.text = Utils.formatPrice.format(product.price)
        val image = binding.imageProductImageDetaills
        Glide.with(requireView())
            .load(product.imageUrl)
            .into(image)
    }

    private fun enableAddToCartButton(state: Boolean) {
        val button = binding.buttonAddToCart
        button.isEnabled = state
        if (state) button.text = getString(R.string.button_add_to_cart_text)
        else button.text = getString(R.string.button_in_cart_text)
    }
}