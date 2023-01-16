package com.example.genericproyect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.genericproyect.databinding.FragmentLoginBinding
import com.example.genericproyect.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        observer()
        validationField()
        actions()

        return binding.root
    }

    private fun observer() {
        loginViewModel.liveCheckLoginData.observe(viewLifecycleOwner) {
            binding.btContinue.isEnabled = it
        }
    }

    private fun validationField() {
        binding.etUser.doAfterTextChanged {
            loginViewModel.checkState(
                it.toString()
            )

        }

    }

    private fun actions() {

        binding.ivArrowPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btContinue.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
            binding.etUser.text.clear()
        }
        binding.tvHelp.setOnClickListener {
            findNavController().navigate(R.id.helpFragment)
        }
    }
}