package com.example.genericproyect.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.genericproyect.R
import com.example.genericproyect.databinding.FragmentHomeLoginBinding


class HomeLoginFragment : Fragment() {

    private lateinit var binding: FragmentHomeLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeLoginBinding.inflate(inflater, container, false)

        navigation()

        return binding.root
    }

    private fun navigation() {
        binding.btLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.btCreate.setOnClickListener {
            findNavController().navigate(R.id.termsAndConditionsFragment)
        }
    }
}