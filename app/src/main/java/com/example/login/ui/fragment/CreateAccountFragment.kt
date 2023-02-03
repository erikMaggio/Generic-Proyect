package com.example.login.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.login.R
import com.example.login.databinding.CreateAccountFragmentBinding
import com.example.login.model.dataSource.LoginDataSource
import com.example.login.utils.AlertErrorField
import com.example.login.ui.viewmodel.UserViewModel


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
        binding.btCreate.isEnabled = true


        return binding.root
    }

    private fun observer() {

        userViewModel.liveNewAccountData.observe(viewLifecycleOwner) {
            usesCase(it.message)
        }

        userViewModel.liveCheckUserData.observe(viewLifecycleOwner) {
       //     binding.btCreate.isEnabled = it
        }

        userViewModel.liveAlertData.observe(viewLifecycleOwner) {
            alertCase(it)
        }
    }

    private fun validationField() {
        binding.etUser.doAfterTextChanged {
            checkFields()
        }
        binding.etEmail.doAfterTextChanged {
            checkFields()
        }
        binding.etPassword.doAfterTextChanged {
            checkFields()
        }
        binding.etConfirmPassword.doAfterTextChanged {
            checkFields()
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
            userViewModel.postSignUp(
                "erik","test@asd.com","123123"
//                binding.etUser.text.toString(),
//                binding.etEmail.text.toString(),
//                binding.etPassword.text.toString()
            )
        }
    }

    private fun alertCase(status: AlertErrorField) {
        when (status) {
            AlertErrorField.SUCCESS -> {
                binding.tfUser.isErrorEnabled = false
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
                binding.tfConfirmPassword.isErrorEnabled = false
            }
            AlertErrorField.ERROR_USER -> {
                binding.tfUser.error =
                    "Usuario incorrecto, el campo no cumple con los requisitos"
            }
            AlertErrorField.ERROR_EMAIL -> {
                binding.tfEmail.error =
                    "Email incorrecto, el campo no cumple con los requisitos"
            }
            AlertErrorField.ERROR_PASSWORD -> {
                binding.tfPassword.error =
                    "Contraseña incorrecta, el campo no cumple con los requisitos"
            }
            AlertErrorField.ERROR_CONFIRM_PASS -> {
                binding.tfPassword.error =
                    "Contraseña incorrecta, el campo no cumple con los requisitos"
            }
        }
    }

    private fun usesCase(errorCode: String) {
        when (errorCode) {
            "200" -> {

                   // findNavController().navigate(R.id.homeFragment)
                Toast.makeText(context, userViewModel.liveNewAccountData.value.toString(), Toast.LENGTH_SHORT).show()
            }
            "401" -> {
                binding.btCreate.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
            "500" -> {
                Toast.makeText(context, "error al ingresar", Toast.LENGTH_SHORT).show()
            }
            "404" -> {
                //alertDialog error masivo
            }
        }

    }

    private fun checkFields() {
        userViewModel.checkStateCreate(
            binding.etUser.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString(),
            binding.etConfirmPassword.text.toString()
        )
    }
}