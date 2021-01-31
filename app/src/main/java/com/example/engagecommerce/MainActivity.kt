package com.example.engagecommerce

import android.app.Activity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.ActivityMainBinding
import com.example.engagecommerce.databinding.DrawerHeaderLayoutBinding
import com.example.engagecommerce.repo.FirebaseCloud
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.security.acl.Owner


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var repo: FirebaseCloud

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .setContentView(
                this,
                R.layout.activity_main
            )

        auth = Firebase.auth
        repo = FirebaseCloud()

        // Drawer Menu
        navigationView = binding.layoutNavigationMenu

        // Container to switch fragments in
        navController = Navigation.findNavController(
            this, R.id.navHostFragment
        )

        NavigationUI.setupWithNavController(navigationView, navController)

        binding.imageMenuAction.setOnClickListener(this)
        binding.imageProfileAction.setOnClickListener(this)
        binding.imageCartAction.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        setNavigationMenuContent()
        val userDocument = repo.getCurrentUser()

        userDocument?.addSnapshotListener { querySnapshot, error ->
            error?.let {
                Log.d("snapshot", it.message.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let {

                val currentUser = it.toObject<User>()
                updateCartSize(currentUser!!)
            }
        }
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
        binding.textCartQuantityMain.text = cartSize.toString()

    }
}
