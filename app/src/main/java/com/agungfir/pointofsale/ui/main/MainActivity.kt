package com.agungfir.pointofsale.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.agungfir.pointofsale.R
import com.agungfir.pointofsale.databinding.ActivityMainBinding
import com.agungfir.pointofsale.ui.checkout.CheckoutFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val fabMain: FloatingActionButton = binding.fabMain
        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_product,
                R.id.navigation_transaction,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_product) {
                fabMain.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add))
            } else {
                fabMain.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_scan))
            }
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }

        binding.fabMain.setOnClickListener {
            val checkoutFragment = CheckoutFragment()
            checkoutFragment.show(supportFragmentManager, "TAG")
        }
    }
}