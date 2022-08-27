package com.ramiyon.soulmath.presentation.ui.auth.login

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.findNavController
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.DialogLottieBinding
import com.ramiyon.soulmath.databinding.FragmentLoginBinding
import com.ramiyon.soulmath.presentation.common.buildAestheticDialog
import com.ramiyon.soulmath.presentation.common.buildLottieDialog
import com.ramiyon.soulmath.presentation.ui.MainActivity
import com.ramiyon.soulmath.presentation.validator.ConstraintValidator
import com.ramiyon.soulmath.presentation.validator.LoginValidator
import com.ramiyon.soulmath.util.Constanta
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ScreenOrientation
import com.thecode.aestheticdialogs.DialogType
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel by viewModel<LoginViewModel>()
    private var isRememberMe by Delegates.notNull<Boolean>()
    private lateinit var lottieBinding: DialogLottieBinding

    override fun inflateViewBinding(container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(LayoutInflater.from(container?.context), container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun FragmentLoginBinding.binder() {
        activity?.apply {
            lottieBinding = DialogLottieBinding.inflate(layoutInflater)

            isRememberMe = sbRememberMe.isChecked

            sbRememberMe.setOnCheckedChangeListener { _, isChecked ->
                isRememberMe = isChecked
            }

            tilEmail.setOnFocusChangeListener { view, hasFocus ->
                if(hasFocus) {
                    var focusIcon = requireContext().resources.getDrawable(R.drawable.ic_email_indicator_focused, null)
                    focusIcon = DrawableCompat.wrap(focusIcon)
                    tilEmail.startIconDrawable = focusIcon
                } else {
                    var defaultIcon = requireContext().resources.getDrawable(R.drawable.ic_email_indicator_non_focused, null)
                    defaultIcon = DrawableCompat.wrap(defaultIcon)
                    tilEmail.startIconDrawable = defaultIcon
                }
            }

            val lottieDialog = buildLottieDialog(lottieBinding, "loading_blue_paper_airplane.json")
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.signIn(email, password).observe(viewLifecycleOwner) { resource ->
                    when(resource) {
                        is Resource.Loading -> {
                            lottieDialog.show()
                        }
                        is Resource.Success -> {
                            lottieDialog.dismiss()
                            buildAestheticDialog(
                                DialogType.SUCCESS,
                                "Login Success",
                                "Welcome!"
                            ) {
                                if (Constanta.SOURCE == Constanta.SOURCE_LOGOUT)
                                    startActivity(Intent(requireContext(), MainActivity::class.java))
                                else {
                                    viewModel.savePrefRememberMe(isRememberMe)
                                    view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginDestinationToMainDestination())
                                }
                                activity?.finish()
                            }
                        }
                        is Resource.Error -> {
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

            tvRegister.setOnClickListener {
                view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun constraintValidator(): ConstraintValidator? {
        return binding?.let { LoginValidator(it) }
    }
}