package com.example.login.ui.fragment

import android.os.Bundle
import android.os.Handler
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
import com.example.login.utils.AlertErrorField
import com.example.login.ui.viewmodel.UserViewModel
import com.example.login.ui.viewmodel.UserViewModelEvent
import com.example.login.utils.Action
import com.example.login.utils.ModalAlert.gone
import com.example.login.utils.ModalAlert.show
import com.example.login.utils.Type


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
        userViewModel.data.observe(viewLifecycleOwner) {
            when (it) {

                is UserViewModelEvent.ClearData -> {
                    gone(binding.icModal)
                    binding.tvErrorAlert.visibility= View.GONE
                    clearFields()
                }

                is UserViewModelEvent.UserSuccessful -> {
                    showSuccessRegister()
                }

                is UserViewModelEvent.UserAlreadyRegister -> {
                    showUserExisting(it.message)
                }

                is UserViewModelEvent.RegisterError401 -> {
                    showError401(it.message)
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
            binding.btCreate.isEnabled = it
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
                binding.etUser.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
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

    private fun showSuccessRegister() {
        setModalAlert()
        binding.icModal.root.visibility = View.VISIBLE
    }

    private fun showError401(msg: String) {
        visible(msg)
    }

    private fun showUserExisting(msg: String) {
        visible(msg)
    }

    private fun showUserError500() {
        findNavController().popBackStack()
    }

    private fun showError404() {
        Toast.makeText(context, "Error en la aplicación ", Toast.LENGTH_SHORT).show()
    }

    private fun visible(msg: String) {
        binding.tvErrorAlert.visibility = View.VISIBLE
        binding.tvErrorAlert.text = msg
        Handler().postDelayed({
            binding.tvErrorAlert.visibility = View.GONE
        }, 3000)
    }

    private fun setModalAlert() {
        show(
            R.drawable.success,
            getString(R.string.item_success_tittle),
            getString(R.string.item_success_subtitle),
            listOf(
                Action(
                    Type.CENTER,
                    R.drawable.border_radius_blue,
                    getString(R.string.login_bt_continue),
                    onClick = {
                        userViewModel.clearData()
                        findNavController().popBackStack()

                    }
                )
            ), binding.icModal
        )
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
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
                binding.tfConfirmPassword.isErrorEnabled = false
                binding.tfUser.error =
                    "Usuario incorrecto, el campo no cumple con los requisitos"
            }
            AlertErrorField.ERROR_EMAIL -> {
                binding.tfUser.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
                binding.tfConfirmPassword.isErrorEnabled = false
                binding.tfEmail.error =
                    "Email incorrecto, el campo no cumple con los requisitos"
            }
            AlertErrorField.ERROR_PASSWORD -> {
                binding.tfConfirmPassword.isErrorEnabled = false
                binding.tfUser.isErrorEnabled = false
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.error =
                    "Contraseña incorrecta, el campo no cumple con los requisitos"
            }
            AlertErrorField.ERROR_CONFIRM_PASS -> {
                binding.tfUser.isErrorEnabled = false
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
                binding.tfPassword.error =
                    "Contraseña incorrecta, el campo no cumple con los requisitos"
            }
        }
    }

    private fun clearFields() {
        binding.etUser.text?.clear()
        binding.etEmail.text?.clear()
        binding.etPassword.text?.clear()
        binding.etConfirmPassword.text?.clear()
    }
}