package com.ramiyon.soulmath.presentation.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.databinding.FragmentRegisterBinding
import com.ramiyon.soulmath.domain.model.User
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.toUserBody
import org.koin.android.ext.android.inject

class RegisterFragment : Fragment() {

    private val viewModel by inject<RegisterViewModel>()
    private val binding by viewBinding<FragmentRegisterBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            activity.apply {
                btnRegister.apply {
                    val email = edtEmail.editText?.text.toString()
                    val password = edtPassword.editText?.text.toString()
                    val username = edtUsername.editText?.text.toString()
                    val address = edtAddress.editText?.text.toString()
                    val phoneNumber = edtPhoneNumber.editText?.text.toString()

                    val user = User(
                        address = address, email = email, name = username, phoneNumber = phoneNumber
                    )

                    viewModel.signUp(
                        email,
                        password,
                        user.toUserBody()
                    )
                }
            }
        }
    }

}