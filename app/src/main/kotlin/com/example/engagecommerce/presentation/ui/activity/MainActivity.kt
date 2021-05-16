package com.example.engagecommerce.presentation.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.engagecommerce.R
import com.example.engagecommerce.databinding.ActivityMainBinding
import com.example.engagecommerce.domain.model.User
import com.google.android.material.navigation.NavigationView
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.drawer_header_layout.view.*

@AndroidEntryPoint
@ScreenName(name = "Activity")
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .setContentView(
                this,
                R.layout.activity_main
            )

        navigationView = binding.layoutNavigationMenu
        navController = Navigation.findNavController(
            this, R.id.navHostFragment
        )

        NavigationUI.setupWithNavController(navigationView, navController)

        setBindings()
        subscribeObservers()
        setNavigationMenuContent()
    }

    override fun onBackPressed() {
        if (isDrawerOpen()) {
            closeDrawerMenu()
        } else {
            super.onBackPressed()
        }
    }

    fun onProfileClick() {
        if (viewModel.isUserLoggedIn()) navigateToProfile()
        else navigateToLogin()
    }

    fun onCartClick() {
        if (viewModel.isUserLoggedIn()) navigateToCart()
        else navigateToLogin()
    }

    fun openDrawerMenu() {
        binding.layoutDrawer.openDrawer(GravityCompat.START)
    }

    private fun navigateToProfile() {
        navController.navigate(R.id.profileFragment)
    }

    private fun navigateToCart() {
        navController.navigate(R.id.cartFragment)
    }

    private fun navigateToLogin() {
        navController.navigate(R.id.loginFragment)
    }

    private fun closeDrawerMenu() {
        binding.layoutDrawer.closeDrawer(GravityCompat.START)
    }

    private fun isDrawerOpen(): Boolean {
        return binding.layoutDrawer.isDrawerOpen(GravityCompat.START)
    }

    private fun setNavigationMenuContent() {
        if (viewModel.isUserLoggedIn()) {
            navigationView.menu.setGroupVisible(R.id.unLoggedGroup, false)
            navigationView.menu.setGroupVisible(R.id.loggedInGroup, true)
        } else {
            navigationView.menu.setGroupVisible(R.id.unLoggedGroup, true)
            navigationView.menu.setGroupVisible(R.id.loggedInGroup, false)
        }
    }

    private fun updateDrawerHeader(user: User) {
        if (viewModel.isUserLoggedIn()) {
            binding.layoutDrawer.userDataHeader.text_first_name_value_header.text = user.firstName
            binding.layoutDrawer.userDataHeader.text_last_name_value_header.text = user.lastName
            binding.layoutDrawer.userDataHeader.text_email_value_header.text = user.email
        }
    }

    private fun subscribeObservers() {
        viewModel.currentUser?.observe(this) { user ->
            updateDrawerHeader(user)
        }
    }

    private fun setBindings() {
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
