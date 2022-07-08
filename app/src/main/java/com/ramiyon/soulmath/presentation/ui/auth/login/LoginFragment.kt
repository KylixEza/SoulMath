package com.ramiyon.soulmath.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.findNavController
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.databinding.DialogLottieBinding
import com.ramiyon.soulmath.databinding.FragmentLoginBinding
import com.ramiyon.soulmath.presentation.common.buildAestheticDialog
import com.ramiyon.soulmath.presentation.common.buildLottieDialog
import com.ramiyon.soulmath.presentation.ui.MainActivity
import com.ramiyon.soulmath.util.Constanta
import com.ramiyon.soulmath.util.Resource
import com.thecode.aestheticdialogs.DialogType
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class LoginFragment : Fragment() {

    private val viewModel by viewModel<LoginViewModel>()
    private val binding by viewBinding<FragmentLoginBinding>()
    private var isRememberMe by Delegates.notNull<Boolean>()
    private lateinit var lottieBinding: DialogLottieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lottieBinding = DialogLottieBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isRememberMe = false

        with(binding) {
            activity?.apply {
                btnLogin.setOnClickListener {
                    val email = edtEmail.editText?.text.toString()
                    val password = edtPassword.editText?.text.toString()
                    viewModel.signIn(email, password).observe(viewLifecycleOwner) { resource ->
                        val lottieDialog = buildLottieDialog(lottieBinding, "loading_blue_paper_airplane.json")
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
                                    else
                                        view.findNavController().navigate(LoginFragmentDirections.actionLoginDestinationToMainDestination())
                                    viewModel.savePrefRememberMe(isRememberMe)
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
            }

            sbRememberMe.setOnCheckedChangeListener { _, isChecked ->
                isRememberMe = isChecked
            }
        }
    }
}