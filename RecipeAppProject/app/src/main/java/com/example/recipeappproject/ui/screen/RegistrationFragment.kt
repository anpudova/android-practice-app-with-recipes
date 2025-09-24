package com.example.recipeappproject.ui.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipeappproject.R
import com.example.recipeappproject.bd.DatabaseHandler
import com.example.recipeappproject.bd.model.UserModel
import com.example.recipeappproject.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class RegistrationFragment: Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        with(binding) {
            btnRegister.setOnClickListener {
                val id: Long = (Math.random() * 10000000 + 1).roundToLong()
                lifecycleScope.launch {
                    val regPass = "^[a-zA-Z0-9]{8,20}$".toRegex()
                    val regName = "^[a-zA-Z]{2,20}$".toRegex()
                    if (regPass.matchEntire(etPassword.text.toString()) != null &&
                        regName.matchEntire(etUsername.text.toString()) != null) {
                        val username: String? = DatabaseHandler.getUsername(etUsername.text.toString())
                        if (username == null) {
                            val user = UserModel(
                                id = id,
                                username = etUsername.text.toString(),
                                password = etPassword.text.toString()
                            )

                            DatabaseHandler.createUser(user)

                            findNavController().navigate(
                                R.id.action_registrationFragment_to_loginFragment
                            )

                        } else {
                            tvMessage.text = "This username already exists"
                        }
                    } else {
                        tvMessage.text = "Password/Username entered incorrectly (password 8-20 symb a-zA-Z0-9, username 2-20 sumb)"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("TEST TAG - RegisterFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - RegisterFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - RegisterFragment onPause")
    }
}