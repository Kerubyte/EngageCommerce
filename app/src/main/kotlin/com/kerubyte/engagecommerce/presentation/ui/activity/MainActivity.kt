package com.kerubyte.engagecommerce.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: ActivityMainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
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
            this,
            R.id.nav_host_fragment
        )
        NavigationUI.setupWithNavController(navigationView, navController)

        firrre()
        setBindings()
        setupObserver()
        val dupi = listOf("aa", "xx", "ww")
        Log.d("dupik", "$dupi")
    }

    override fun onBackPressed() {
        if (isDrawerOpen()) {
            closeDrawerMenu()
        } else {
            super.onBackPressed()
        }
    }

    private fun isDrawerOpen(): Boolean {
        return binding.layoutDrawer.isDrawerOpen(GravityCompat.START)
    }

    private fun closeDrawerMenu() {
        binding.layoutDrawer.closeDrawer(GravityCompat.START)
    }

    private fun setBindings() {
        binding.mainActivity = this
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    private fun setupObserver() {
        mainViewModel.currentUser.observe(this, { response ->
            when (response) {
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Success ->
                    updateUI()
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.AuthenticationError ->
                    Log.d("MainActivity", "${R.string.authentication_error}")
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.NetworkError ->
                    Log.d("MainActivity", "${R.string.network_error}")
            }
        })
    }

    private fun updateUI() {
        //Update nav drawer header and items in cart
    }

    private fun navigateToCart() {
        navController.navigate(R.id.cartFragment)
    }

    private fun navigateToProfile() {
        navController.navigate(R.id.profileFragment)
    }

    private fun navigateToLogin() {
        navController.navigate(R.id.loginFragment)
    }

    fun openDrawerMenu() {
        binding.layoutDrawer.openDrawer(GravityCompat.START)
    }

    fun onProfileClick() {
        mainViewModel.currentUser.value?.data?.let {
            navigateToProfile()
        } ?: navigateToLogin()
    }

    fun onCartClick() {
        mainViewModel.currentUser.value?.data?.let {
            navigateToCart()
        } ?: navigateToLogin()
    }

    private fun firrre() {

        FirebaseMessaging.getInstance()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("wiadomosc", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("wiadomoscToken", "$token")
            //Toast.makeText(this, "$token", Toast.LENGTH_LONG).show()
        })
    }
}