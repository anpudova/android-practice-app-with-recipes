package com.example.recipeappproject.ui.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipeappproject.R
import com.example.recipeappproject.bd.DatabaseHandler
import com.example.recipeappproject.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        val preferences: SharedPreferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editPreferences: SharedPreferences.Editor = preferences.edit()
        with(binding) {
            val username = preferences.getString("username", "")
            if (username != "") {
                llProfile.isVisible = true
                llLogin.isVisible = false
                tvUsername.text = username
                btnExit.isVisible = true
                btnDelete.isVisible = true
            } else {
                llProfile.isVisible = false
                llLogin.isVisible = true
                btnExit.isVisible = false
                btnDelete.isVisible = false
            }

            mbLogin.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profileFragment_to_loginFragment
                )
            }
            btnExit.setOnClickListener {
                editPreferences.clear()
                editPreferences.apply()
                findNavController().navigate(
                    R.id.action_profileFragment_self
                )
            }
            btnDelete.setOnClickListener {
                lifecycleScope.launch {
                    username?.let { username ->
                        val user = DatabaseHandler.getUserByUsername(username)
                        user?.let { user ->
                            DatabaseHandler.deleteUser(user)
                        }
                    }
                    editPreferences.clear()
                    editPreferences.apply()
                    findNavController().navigate(
                        R.id.action_profileFragment_self
                    )
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("TEST TAG - ProfileFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - ProfileFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - ProfileFragment onPause")
    }
}