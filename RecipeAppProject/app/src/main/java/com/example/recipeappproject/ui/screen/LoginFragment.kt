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
import com.example.recipeappproject.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        with(binding) {
            btnLogin.setOnClickListener {
                val preferences: SharedPreferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)
                val editPreferences: SharedPreferences.Editor = preferences.edit()
                var user: UserModel?
                lifecycleScope.launch {
                    user = DatabaseHandler.getUser(etUsername.text.toString(), etPassword.text.toString())
                    val id: Long? = user?.id
                    val username: String? = user?.username
                    val password: String? = user?.password
                    if (id != null && username != null && password != null) {
                        editPreferences.putString("username", username)
                        editPreferences.putLong("id", id)
                        editPreferences.apply()
                        findNavController().navigate(
                            R.id.action_loginFragment_to_profileFragment
                        )
                    } else {
                        tvMessage.text = ERROR_MESSAGE
                    }
                }
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registrationFragment
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("TEST TAG - LoginFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - LoginFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - LoginFragment onPause")
    }

    companion object {

        const val ERROR_MESSAGE = "Invalid username or password"
    }
}