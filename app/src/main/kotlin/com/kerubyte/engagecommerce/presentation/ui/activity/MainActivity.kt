package com.kerubyte.engagecommerce.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
    }
}