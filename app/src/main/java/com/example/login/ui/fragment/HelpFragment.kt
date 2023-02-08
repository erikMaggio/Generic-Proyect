package com.example.login.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.login.R
import com.example.login.databinding.FragmentHelpBinding
import com.example.login.ui.viewmodel.UserViewModel


class HelpFragment : Fragment() {


    private val userViewModel by viewModels<UserViewModel>()

    private lateinit var binding: FragmentHelpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(inflater, container, false)

        observer()
        actions()

        return binding.root
    }

    private fun observer() {
        userViewModel.liveRecoverData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun actions() {
        binding.btRecover.setOnClickListener {
            userViewModel.postRecoverPass(binding.etEmail.text.toString())
        }

        binding.ivArrowPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}