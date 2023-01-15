package com.example.genericproyect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.genericproyect.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        navigation()

        return binding.root
    }

    fun navigation() {
        binding.ivArrowPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btContinue.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.tvHelp.setOnClickListener {
            findNavController().navigate(R.id.helpFragment)
        }
    }

}