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
import com.example.login.databinding.FragmentHelpBinding
import com.example.login.ui.viewmodel.UserViewModel
import com.example.login.ui.viewmodel.UserViewModelEvent
import com.example.login.utils.*
import com.example.login.utils.ModalAlert.goneModal
import com.example.login.utils.ModalAlert.gonePb

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

                is UserViewModelEvent.ClearData -> {
                    goneModal(binding.icModal)
                    gonePb(binding.icPb)
                    binding.tvErrorAlert.visibility = GONE
                    clearFields()
                }

                is UserViewModelEvent.UserSuccessful -> {
                    showSuccessRecover()
                }

                is UserViewModelEvent.UserNotRegister -> {
                    showUserNotRegister(it.message)
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
            showLoadingView()
            it.isEnabled = false
            userViewModel.postRecoverPass(binding.etEmail.text.toString())
        }

        binding.ivArrowPrevious.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateField() {
        binding.etEmail.doAfterTextChanged {
            userViewModel.checkEmailRecover(binding.etEmail.text.toString())
        }
    }

    private fun showSuccessRecover() {
        binding.icPb.root.visibility = GONE
        setModalAlert()
        binding.icModal.root.visibility = VISIBLE
    }

    private fun showLoadingView() {
        binding.icPb.root.visibility = VISIBLE
    }

    private fun showUserNotRegister(msg: String) {
        binding.icPb.root.visibility = GONE
        visible(msg)
        clearFields()
    }

    private fun showUserError500() {
        binding.icPb.root.visibility = GONE
        findNavController().navigate(R.id.homeLoginFragment)
        clearFields()
    }

    private fun showError404() {
        Toast.makeText(context, "Error en la aplicacion ", Toast.LENGTH_SHORT).show()
    }

    private fun visible(msg: String) {
        binding.tvErrorAlert.visibility = VISIBLE
        binding.tvErrorAlert.text = msg
        Handler().postDelayed({
            binding.tvErrorAlert.visibility = GONE
        }, 3000)
    }

    private fun setModalAlert() {
        ModalAlert.show(
            R.drawable.verify,
            getString(R.string.item_verify_tittle),
            getString(R.string.item_verify_subtitle),
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

    private fun clearFields() {
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