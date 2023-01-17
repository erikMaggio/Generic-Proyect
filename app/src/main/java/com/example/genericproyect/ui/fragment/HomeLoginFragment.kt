package com.example.genericproyect.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.genericproyect.R
import com.example.genericproyect.databinding.FragmentHomeLoginBinding
import com.example.genericproyect.viewmodel.LoginViewModel


class HomeLoginFragment : Fragment() {

    private lateinit var binding: FragmentHomeLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeLoginBinding.inflate(inflater, container, false)

        action()
        observer()
        validationField()

        return binding.root
    }

    private fun observer() {
        loginViewModel.liveCheckLoginData.observe(viewLifecycleOwner) {
            binding.btLogin.isEnabled = it
        }
    }

    private fun validationField() {
        binding.etUser.doAfterTextChanged {
            loginViewModel.checkState(
                it.toString(), binding.etPassword.text.toString()
            )
        }

        binding.etPassword.doAfterTextChanged {
            loginViewModel.checkState(
                binding.etUser.text.toString(), it.toString()
            )
        }
    }

    private fun action() {
        binding.btCreate.setOnClickListener {
            findNavController().navigate(R.id.createAccountFragment)
        }

        binding.btLogin.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }
}