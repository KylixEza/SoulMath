package com.ramiyon.soulmath.presentation.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.DialogLottieBinding
import com.ramiyon.soulmath.presentation.common.buildAestheticDialog
import com.ramiyon.soulmath.presentation.common.buildLottieDialog
import com.ramiyon.soulmath.databinding.FragmentRegisterBinding
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.presentation.ui.MainActivity
import com.ramiyon.soulmath.util.Constanta
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ScreenOrientation
import com.ramiyon.soulmath.util.toStudentBody
import com.thecode.aestheticdialogs.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel by viewModel<RegisterViewModel>()
    private lateinit var lottieBinding: DialogLottieBinding

    override fun inflateViewBinding(container: ViewGroup?): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentRegisterBinding.binder() {
        lottieBinding = DialogLottieBinding.inflate(layoutInflater)

        activity?.apply {
            val lottieDialog = buildLottieDialog(lottieBinding, "loading_blue_paper_airplane.json")
            btnRegister.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val username = edtUsername.text.toString()
                val phoneNumber = edtPhoneNumber.text.toString()

                val user = Student(
                    address = "", email = email, username = username, phoneNumber = phoneNumber
                )

                viewModel.signUp(
                    email,
                    password,
                    user
                ).observe(viewLifecycleOwner) { resource ->
                    when (resource) {
                        is Resource.Loading<*> -> {
                            lottieDialog.show()
                        }

                        is Resource.Success<*> -> {
                            lottieDialog.dismiss()
                            buildAestheticDialog(
                                DialogType.SUCCESS,
                                "Register Success",
                                "Congratulations!\nYour account has been registered"
                            ) {
                                lottieDialog.dismiss()
                                if (Constanta.SOURCE == Constanta.SOURCE_LOGOUT)
                                    startActivity(Intent(requireContext(), MainActivity::class.java))
                                else
                                    view?.findNavController()?.navigate(RegisterFragmentDirections.actionRegisterDestinationToMainDestination())
                                viewModel.savePrefHaveRunAppBefore(true)
                                activity?.finish()
                            }
                        }

                        is Resource.Error<*> -> {
                            lottieDialog.dismiss()
                            buildAestheticDialog(
                                DialogType.ERROR,
                                "Something Went Wrong!",
                                resource.message.toString()
                            ) {
                                it.dismiss()
                            }
                        }

                        else -> {
                            lottieDialog.dismiss()
                        }
                    }
                }
            }

            onBackPressedDispatcher.addCallback {
                view?.findNavController()?.navigate(
                    RegisterFragmentDirections.actionRegisterDestinationToOnBoardingDestination("Register")
                )
            }
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
}