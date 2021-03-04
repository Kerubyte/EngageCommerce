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
    private lateinit var product: Product
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
        auth = Firebase.auth
        repository = FirebaseCloud()

        viewModelFactory = ProductDetailViewModelFactory(
            ProductDetailFragmentArgs
                .fromBundle(requireArguments())
                .productUid
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductDetailViewModel::class.java)

        // Set observer to attach product data to the view
        viewModel.product.observe(viewLifecycleOwner, {
            bindProductData(it)
            viewModel.sendProductEvent(it, ProductEventType.DETAIL)
            product = viewModel.product.value!!
        })

        // Listener to observe changes in current user and check for Product in cart
        snapshotListenerRegistration = viewModel.currentUser?.addSnapshotListener { querySnapshot, error ->
            error?.let {
                Log.d("snapshotProduct", it.message.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val user = it.toObject<User>()
                val state = checkForProductInCart(user!!)
                setButtonState(state)
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

    // Handle clicks in the fragment
    override fun onClick(view: View?) {
        when (view) {
            binding.buttonAddToCart ->
                if (auth.currentUser == null) {
                    navigateToLogin()
                } else {
                    viewModel.addToCart(
                        ProductDetailFragmentArgs
                            .fromBundle(requireArguments())
                            .productUid
                    )
                    viewModel.sendProductEvent(
                        product,
                        ProductEventType.ADD_TO_CART
                    )
                    setButtonState(false)
                }
        }
    }

    // Populate view with Product data
    private fun bindProductData(product: Product) {
        binding.textProductNameDetail.text = product.name
        binding.textProductDescriptionDetail.text = product.description
        binding.textProductPriceDetail.text = Utils.formatPrice.format(product.price)
        val image = binding.imageProductImageDetaills
        Glide.with(requireView())
            .load(product.imageUrl)
            .into(image)
    }

    // Check if viewed product is already in cart
    private fun checkForProductInCart(currentUser: User): Boolean {
        val cart = currentUser.cart
        val productUid = ProductDetailFragmentArgs.fromBundle(requireArguments()).productUid

        return if (cart != null) productUid !in cart
        else true
    }

    // Enable or Disable 'add to cart' button
    private fun setButtonState(state: Boolean) {
        val button = binding.buttonAddToCart
        button.isEnabled = state
        if (state) button.text = getString(R.string.button_add_to_cart_text)
        else button.text = getString(R.string.button_in_cart_text)
    }
}