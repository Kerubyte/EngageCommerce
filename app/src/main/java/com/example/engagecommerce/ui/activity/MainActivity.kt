package com.example.engagecommerce.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.engagecommerce.R
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.user.sdk.events.ScreenName
import kotlinx.android.synthetic.main.drawer_header_layout.view.*

@ScreenName(name = "Activity")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    private var snapshotListenerRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = DataBindingUtil
            .setContentView(
                this,
                R.layout.activity_main
            )

        auth = Firebase.auth
        viewModel = MainViewModel()

        // Drawer Menu
        navigationView = binding.layoutNavigationMenu

        navController = Navigation.findNavController(
            this, R.id.navHostFragment
        )

        NavigationUI.setupWithNavController(navigationView, navController)

        binding.imageMenuAction.setOnClickListener(this)
        binding.imageProfileAction.setOnClickListener(this)
        binding.imageCartAction.setOnClickListener(this)
    }

    override fun onStart() {
        setNavigationMenuContent()

        // Listener to observe changes in current user and update Cart Size view
        snapshotListenerRegistration =
            viewModel.currentUser?.addSnapshotListener { querySnapshot, error ->
                error?.let {
                    Log.d("snapshotMain", it.message.toString())
                    return@addSnapshotListener
                }
                querySnapshot?.let {

                    val currentUser = it.toObject<User>()
                    updateCartSize(currentUser!!)
                }
            }

        viewModel.user?.observe(this, { user ->
            updateDrawerHeader(user)
        })
        super.onStart()
    }

    override fun onStop() {
        snapshotListenerRegistration?.remove()
        super.onStop()
    }

    override fun onBackPressed() {
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.layoutDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // Handle Toolbar interactions
    override fun onClick(v: View?) {
        when (v) {
            binding.imageMenuAction ->
                openDrawerMenu()
            binding.imageProfileAction ->
                if (auth.currentUser != null) navigateToProfile()
                else navigateToLogin()
            binding.imageCartAction ->
                if (auth.currentUser != null) navigateToCart()
                else navigateToLogin()
        }
    }

    private fun openDrawerMenu() {
        binding.layoutDrawer.openDrawer(GravityCompat.START)
    }

    private fun navigateToProfile() {
        navController.navigate(R.id.menuProfile)
    }

    private fun navigateToCart() {
        navController.navigate(R.id.menuCart)
    }

    private fun navigateToLogin() {
        navController.navigate(R.id.menuLogin)
    }

    private fun setNavigationMenuContent() {
        if (auth.currentUser != null) {
            navigationView.menu.setGroupVisible(R.id.unLoggedGroup, false)
            navigationView.menu.setGroupVisible(R.id.loggedInGroup, true)
        } else {
            navigationView.menu.setGroupVisible(R.id.unLoggedGroup, true)
            navigationView.menu.setGroupVisible(R.id.loggedInGroup, false)
        }
    }

    private fun updateCartSize(user: User) {
        val cartSize = user.cart?.size

        if (user.cart.isNullOrEmpty()) {
            binding.textCartQuantityMain.text = "0"
        } else {
            binding.textCartQuantityMain.text = cartSize.toString()
        }
    }

    private fun updateDrawerHeader(user: User) {
        if (auth.currentUser != null) {
            binding.layoutDrawer.userDataHeader.text_first_name_value_header.text = user.firstName
            binding.layoutDrawer.userDataHeader.text_last_name_value_header.text = user.lastName
            binding.layoutDrawer.userDataHeader.text_email_value_header.text = user.email
        }
    }
}
