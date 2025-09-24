package com.example.recipeappproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.recipeappproject.R
import com.example.recipeappproject.bd.DatabaseHandler
import com.example.recipeappproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applicationContext?.let {
            DatabaseHandler.dbInit(appContext = it)
        }
        controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
        val bottomView = findViewById<BottomNavigationView>(R.id.btn_view)
        bottomView.setupWithNavController(controller)

        controller.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.registrationFragment, R.id.loginFragment, R.id.detailRecipeFragment -> bottomView.visibility = View.GONE
                else -> bottomView.visibility = View.VISIBLE
            }
        }
    }
}