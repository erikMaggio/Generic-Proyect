package com.example.login.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.example.login.utils.Action
import com.example.login.utils.CodesError.CODE_404
import com.example.login.utils.ModalAlert.gone
import com.example.login.utils.ModalAlert.isInternetAvailable
import com.example.login.utils.ModalAlert.noConnectionInternet
import com.example.login.utils.ModalAlert.show
import com.example.login.utils.Type

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

                is UserViewModelEvent.ClearData -> {
                    gone(binding.icModal)
                    clearFields()
                }

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

            if (isInternetAvailable(requireContext())) {
                findNavController().navigate(R.id.createAccountFragment)
                clearFields()
            } else {
                noConnectionInternet(
                    requireActivity(),
                    onRestart = { onRestart() },
                    binding.icModal
                )
                binding.icModal.root.visibility = VISIBLE
            }

        }

        binding.btLogin.setOnClickListener {

            if (isInternetAvailable(requireContext())) {
                userViewModel.postLogin(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            } else {
                noConnectionInternet(
                    requireActivity(),
                    onRestart = { onRestart() },
                    binding.icModal
                )
                binding.icModal.root.visibility = VISIBLE
            }
        }

        binding.tvTextHelp.setOnClickListener {
            findNavController().navigate(R.id.helpFragment)
            clearFields()
        }
    }

    private fun onRestart() {
        if (isInternetAvailable(requireContext())) {
            userViewModel.postLogin(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
            binding.icModal.root.visibility = GONE
        }
    }

    private fun clearFields() {
        binding.etEmail.text?.clear()
        binding.etPassword.text?.clear()
    }

    private fun checkFields() {
        userViewModel.checkStateLogin(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
    }

    private fun showSuccessLogin() {
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.homeFragment)
        clearFields()
    }

    private fun showError401() {
        Toast.makeText(context, "error 401", Toast.LENGTH_SHORT).show()
    }

    private fun showUserNotRegister() {
        setModalAlert()
        binding.icModal.root.visibility = VISIBLE
    }

    private fun showUserError500() {
        findNavController().navigate(R.id.homeLoginFragment)
        clearFields()
    }

    private fun showError404() {
        Toast.makeText(context, "Error en la application ", Toast.LENGTH_SHORT).show()
    }

    private fun setModalAlert() {
        show(
            R.drawable.alert,
            getString(R.string.item_error_login_subtitle),
            getString(R.string.item_error_login_subtitle2),
            listOf(
                Action(
                    Type.LEFT,
                    R.drawable.border_radius_blue,
                    getString(R.string.login_bt_continue),
                    onClick = {
                        userViewModel.clearData()
                        findNavController().navigate(R.id.createAccountFragment)
                    }
                ),
                Action(Type.RIGHT,
                    R.drawable.border_radius_red,
                    getString(R.string.item_bt_cancel),
                    onClick = {
                        gone(binding.icModal)
                    })
            ), binding.icModal
        )
    }


    private fun alertCase(status: AlertErrorField) {
        when (status) {
            AlertErrorField.SUCCESS -> {
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
            }
            AlertErrorField.ERROR_EMAIL -> {
                binding.tfPassword.isErrorEnabled = false
                binding.tfEmail.error = "Email incorrect"
            }

            AlertErrorField.ERROR_PASSWORD -> {
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.error = "Contrast Incorrect"
            }
            else -> {
                CODE_404
            }
        }
    }
}

