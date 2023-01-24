package com.example.genericproyect.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.genericproyect.R
import com.example.genericproyect.databinding.FragmentHomeLoginBinding
import com.example.genericproyect.viewmodel.UserViewModel


class HomeLoginFragment : Fragment() {

    private lateinit var binding: FragmentHomeLoginBinding
    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeLoginBinding.inflate(inflater, container, false)

        action()
        observer()
        validationField()
        call()

        return binding.root
    }

    private fun observer() {

        userViewModel.liveUserData.observe(viewLifecycleOwner) {
            binding.btLogin.setOnClickListener {
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
            }
        }

        userViewModel.liveCheckUserData.observe(viewLifecycleOwner) {
            binding.btLogin.isEnabled = it
        }

    }

    private fun call() {
        userViewModel.getLogin(
            binding.etUser.text.toString(),
            binding.etPassword.text.toString()
        )
    }

    private fun validationField() {
        binding.etUser.doAfterTextChanged {
            userViewModel.checkState(
                it.toString(), binding.etPassword.text.toString()
            )
        }

        binding.etPassword.doAfterTextChanged {
            userViewModel.checkState(
                binding.etUser.text.toString(), it.toString()
            )
        }
    }

    private fun action() {
        binding.btCreate.setOnClickListener {
            findNavController().navigate(R.id.createAccountFragment)
            binding.etUser.text?.clear()
            binding.etPassword.text?.clear()
        }

//        binding.btLogin.setOnClickListener {
//            findNavController().navigate(R.id.homeFragment)
//            binding.etUser.text?.clear()
//            binding.etPassword.text?.clear()
//        }

        binding.tvTextHelp.setOnClickListener {
            findNavController().navigate(R.id.helpFragment)
            binding.etUser.text?.clear()
            binding.etPassword.text?.clear()
        }
    }
}