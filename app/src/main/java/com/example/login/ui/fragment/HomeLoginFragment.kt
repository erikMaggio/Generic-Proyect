package com.example.login.ui.fragment

import android.app.AlertDialog
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
import com.example.login.databinding.FragmentHomeLoginBinding
import com.example.login.utils.AlertErrorField
import com.example.login.ui.viewmodel.UserViewModel


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

        return binding.root
    }

    private fun observer() {

        userViewModel.liveUserData.observe(viewLifecycleOwner) {
            //Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()

        }

        userViewModel.liveCheckUserData.observe(viewLifecycleOwner) {
            binding.btLogin.isEnabled = it
        }
        userViewModel.liveAlertData.observe(viewLifecycleOwner) {
            alertCase(it)
        }
    }

    private fun validationField() {
        binding.etEmail.doAfterTextChanged {
            checkFields()
        }

        binding.etPassword.doAfterTextChanged {
            checkFields()
        }
    }

    private fun action() {
        binding.btCreate.setOnClickListener {
            findNavController().navigate(R.id.createAccountFragment)
            binding.etEmail.text?.clear()
            binding.etPassword.text?.clear()
        }

        binding.btLogin.setOnClickListener {
            userViewModel.postLogin(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.tvTextHelp.setOnClickListener {
            findNavController().navigate(R.id.helpFragment)
            binding.etEmail.text?.clear()
            binding.etPassword.text?.clear()
        }
    }

    private fun alertCase(status: AlertErrorField) {
        when (status) {
            AlertErrorField.SUCCESS -> {
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
            }
            AlertErrorField.ERROR_EMAIL -> {
                binding.tfPassword.isErrorEnabled = false
                binding.tfEmail.error = "Email incorrecto"
            }

            AlertErrorField.ERROR_PASSWORD -> {
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.error = "Contraseña Incorrecta"
            }
            else -> {
                "error"
            }
        }
    }

    private fun checkFields() {
        userViewModel.checkStateLogin(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
    }

    private fun alertDialogError() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Falla Del Sistema")
        alertDialog.setMessage("Ha ocurrido un error obteniendo la información")
        alertDialog.setPositiveButton("Reintentar") { _, _ ->
            findNavController().navigate(R.id.homeLoginFragment)
        }
        alertDialog.setNegativeButton("Cancelar") { _, _ ->

        }
        alertDialog.show()
    }
}
