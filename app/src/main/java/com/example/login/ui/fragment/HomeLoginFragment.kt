package com.example.login.ui.fragment

import android.os.Bundle
import android.os.Handler
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
import com.example.login.ui.viewmodel.UserViewModelFactory
import com.example.login.utils.CodesError.CODE_404
import com.example.login.utils.ModalAlert.goneModal
import com.example.login.utils.ModalAlert.gonePb
import com.example.login.utils.ModalAlert.isInternetAvailable
import com.example.login.utils.ModalAlert.noConnectionInternet

class HomeLoginFragment : Fragment() {

    private lateinit var binding: FragmentHomeLoginBinding
    private lateinit var userViewModel:UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeLoginBinding.inflate(inflater, container, false)

        getViewModel()
        action()
        observer()
        validationField()

        return binding.root
    }

    private fun observer() {

        userViewModel.data.observe(viewLifecycleOwner) {

            when (it) {

                is UserViewModelEvent.ClearData -> {
                    binding.tvErrorAlert.visibility = GONE
                    goneModal(binding.icModal)
                    gonePb(binding.icPb)
                    clearFields()
                }

                is UserViewModelEvent.UserSuccessful -> {
                    showSuccessLogin()
                    userViewModel.saveTokenUser(it.message)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()

                }

                is UserViewModelEvent.UserAuthError -> {
                    showAuthError(it.message)
                }

                is UserViewModelEvent.UserNotRegister -> {
                    showUserNotRegister(it.message)

                }

                is UserViewModelEvent.RegisterError401 -> {
                    showError401(it.message)
                }
                is UserViewModelEvent.UserError500 -> {
                    showUserError500(it.message)
                }

                else -> {
                    showError404(it.toString())
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
                binding.tvErrorAlert.visibility = GONE
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
                showLoadingView()
                it.isEnabled = false
                userViewModel.postLogin(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                binding.tvErrorAlert.visibility = GONE

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
            binding.tvErrorAlert.visibility = GONE
            findNavController().navigate(R.id.helpFragment)
            binding.etEmail.text?.clear()
            binding.etPassword.text?.clear()
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
        binding.icPb.root.visibility = GONE
        findNavController().navigate(R.id.homeFragment)
        clearFields()
    }

    private fun showLoadingView() {
        binding.icPb.root.visibility = VISIBLE
    }

    private fun showError401(msg: String) {
        binding.icPb.root.visibility = GONE
        visible(msg)
    }

    private fun showAuthError(msg:String) {
        binding.icPb.root.visibility = GONE
        binding.etPassword.text?.clear()
        visible(msg)
    }

    private fun showUserNotRegister(msg: String) {
        binding.icPb.root.visibility = GONE
        visible(msg)
        clearFields()
    }

    private fun showUserError500(msg: String) {
        binding.icPb.root.visibility = GONE
        visible(msg)
    }

    private fun showError404(msg: String) {
        binding.icPb.root.visibility = GONE
        visible(msg)
    }

    private fun visible(msg: String) {
        binding.tvErrorAlert.visibility = VISIBLE
        binding.tvErrorAlert.text = msg
        Handler().postDelayed({
            binding.tvErrorAlert.visibility = GONE
        }, 3000)
    }

    private fun alertCase(status: AlertErrorField) {
        when (status) {
            AlertErrorField.SUCCESS -> {
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
            }
            AlertErrorField.ERROR_EMAIL -> {
                binding.tfPassword.isErrorEnabled = false
                binding.tfEmail.error = getString(R.string.field_email_error)
            }

            AlertErrorField.ERROR_PASSWORD -> {
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.error = getString(R.string.field_pass_error)
            }
            else -> {
                CODE_404
            }
        }
    }

    private fun getViewModel() {
        userViewModel =
            UserViewModelFactory(requireActivity().application).create(UserViewModel::class.java)
    }
}
