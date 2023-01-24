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
import com.example.genericproyect.databinding.CreateAccountFragmentBinding
import com.example.genericproyect.viewmodel.UserViewModel


class CreateAccountFragment : Fragment() {

    private lateinit var binding: CreateAccountFragmentBinding
    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateAccountFragmentBinding.inflate(inflater, container, false)


        observer()
        validationField()
        actions()

        return binding.root
    }

    private fun observer() {
        userViewModel.liveCheckUserData.observe(viewLifecycleOwner) {
            binding.btCreate.isEnabled = it
        }
    }

    private fun validationField() {
        binding.etUser.doAfterTextChanged {
            userViewModel.checkStateCreate(
                it.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        binding.etEmail.doAfterTextChanged {
            userViewModel.checkStateCreate(
                binding.etUser.text.toString(),
                it.toString(),
                binding.etPassword.text.toString()
            )
        }
        binding.etPassword.doAfterTextChanged {
            userViewModel.checkStateCreate(
                binding.etUser.text.toString(),
                binding.etEmail.text.toString(),
                it.toString()
            )
        }
    }

    private fun actions() {
        binding.ivArrowPrevious.setOnClickListener {
            findNavController().popBackStack()
            binding.etUser.text?.clear()
            binding.etPassword.text?.clear()
            binding.etEmail.text?.clear()
        }

        binding.btCreate.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
            binding.etUser.text?.clear()
            binding.etPassword.text?.clear()
            binding.etEmail.text?.clear()
        }
    }

}