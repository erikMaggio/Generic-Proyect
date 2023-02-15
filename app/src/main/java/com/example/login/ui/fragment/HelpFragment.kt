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
import com.example.login.databinding.FragmentHelpBinding
import com.example.login.ui.viewmodel.UserViewModel
import com.example.login.ui.viewmodel.UserViewModelEvent
import com.example.login.utils.AlertErrorField
import com.example.login.utils.CodesError

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
        validateField()

        return binding.root
    }

    private fun observer() {

        userViewModel.liveEmailData.observe(viewLifecycleOwner) {
            binding.btRecover.isEnabled = it
        }

        userViewModel.data.observe(viewLifecycleOwner) {
            when (it) {

                is UserViewModelEvent.UserSuccessful -> {
                    showSuccessRecover()
                }

                is UserViewModelEvent.UserNotRegister -> {
                    showUserNotRegister()
                }

                is UserViewModelEvent.UserError500 -> {
                    showUserError500()
                }

                else -> {
                    showError404()
                }

            }
        }

        userViewModel.liveAlertData.observe(viewLifecycleOwner) {
            alertCase(it)
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

    private fun validateField() {
        binding.etEmail.doAfterTextChanged {
            userViewModel.checkEmailRecover(binding.etEmail.text.toString())
        }
    }

    private fun alertDialogSuccess() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setMessage("Se ha enviado un correo de a su email")
        alertDialog.setPositiveButton("Continuar") { _, _ ->
            findNavController().navigate(R.id.homeLoginFragment)
        }
        alertDialog.show()
    }

    private fun showSuccessRecover() {
        alertDialogSuccess()
    }

    private fun showUserNotRegister() {
        Toast.makeText(context, "Usuario no registrado", Toast.LENGTH_SHORT).show()
        clearFields()
    }

    private fun showUserError500() {
        findNavController().navigate(R.id.homeLoginFragment)
        clearFields()
    }

    private fun showError404() {
        Toast.makeText(context, "Error en la aplicacion ", Toast.LENGTH_SHORT).show()
    }


    private fun clearFields(){
        binding.etEmail.text?.clear()
    }


    private fun alertCase(status: AlertErrorField) {
        when (status) {
            AlertErrorField.SUCCESS -> {
                binding.tfEmail.isErrorEnabled = false
            }
            AlertErrorField.ERROR_EMAIL -> {
                binding.tfEmail.error = "Email incorrecto"
            }

            else -> {
                CodesError.CODE_404
            }
        }
    }
}