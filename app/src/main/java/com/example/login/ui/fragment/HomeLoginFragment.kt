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
import com.example.login.ui.viewmodel.UserViewModelEvent
import com.example.login.utils.CodesError.CODE_404


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

        userViewModel.data.observe(viewLifecycleOwner) {
            when (it) {

                is UserViewModelEvent.UserSuccessful -> {
                    showSuccessLogin()
                }

                is UserViewModelEvent.UserNotRegister -> {
                    showUserNotRegister()

                }

                is UserViewModelEvent.RegisterError401 -> {
                    showError401()
                }
                is UserViewModelEvent.UserError500 -> {
                    showUserError500()
                }

                else -> {
                    showError404()
                }

            }
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
            clearFields()
        }

        binding.btLogin.setOnClickListener {
            userViewModel.postLogin(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.tvTextHelp.setOnClickListener {
            findNavController().navigate(R.id.helpFragment)
            clearFields()
        }
    }

    private fun clearFields(){
        binding.etEmail.text?.clear()
        binding.etPassword.text?.clear()
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

    private fun alertDialogUserNotRegister() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("User Not Register")
        alertDialog.setMessage("This account is not registered. ¿Would you like to register?")
        alertDialog.setPositiveButton("Sign In") { _, _ ->
            findNavController().navigate(R.id.createAccountFragment)
        }

        alertDialog.show()
        clearFields()
    }

    private fun showSuccessLogin() {
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.homeFragment)
        clearFields()
    }

    private fun showError401() {
        alertDialogError()
    }

    private fun showUserNotRegister() {
        alertDialogUserNotRegister()

    }

    private fun showUserError500() {
        findNavController().navigate(R.id.homeLoginFragment)
        clearFields()
    }

    private fun showError404() {
        Toast.makeText(context, "Error en la aplicacion ", Toast.LENGTH_SHORT).show()
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
                CODE_404
            }
        }
    }
}
