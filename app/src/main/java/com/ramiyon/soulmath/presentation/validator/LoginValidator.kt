package com.ramiyon.soulmath.presentation.validator

import android.annotation.SuppressLint
import android.util.Patterns
import com.jakewharton.rxbinding2.widget.RxTextView
import com.ramiyon.soulmath.databinding.FragmentLoginBinding
import io.reactivex.Observable

class LoginValidator(
    private val binding: FragmentLoginBinding
) {

    init {
        initObserve()
    }

    @SuppressLint("CheckResult")
    private fun initObserve() {
        val emailObservable = RxTextView.textChanges(binding.edtEmail)
            .map { it.toString() }
            .map { return@map it.isEmpty() }
            .distinctUntilChanged()

        val passwordObservable = RxTextView.textChanges(binding.edtPassword)
            .map { it.toString() }
            .map { return@map it.isEmpty() }
            .distinctUntilChanged()

        val loginObservable = Observable.combineLatest(
            emailObservable,
            passwordObservable
        ) { emailInvalid: Boolean, passwordInvalid: Boolean ->
            !emailInvalid && !passwordInvalid
        }

        loginObservable.subscribe {
            binding.btnLogin.isEnabled = it
        }
    }

    @SuppressLint("CheckResult")
    fun requirementObserve() {
        val emailStream = RxTextView.textChanges(binding.edtEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.edtPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showPasswordMinimalAlert(it)
        }

        val invalidFieldStream = Observable.combineLatest(
            emailStream,
            passwordStream
        ) { emailInvalid: Boolean, passwordInvalid: Boolean ->
            !emailInvalid && !passwordInvalid
        }

        invalidFieldStream.subscribe { isValid ->
            binding.btnLogin.isEnabled = isValid
        }
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean?) {
        binding.edtPassword.error = if (isNotValid == true) "Password minimal 6 karakter" else null
    }

    private fun showEmailExistAlert(isNotValid: Boolean?) {
        binding.edtEmail.error = if (isNotValid == true) "Email tidak valid" else null
    }
}