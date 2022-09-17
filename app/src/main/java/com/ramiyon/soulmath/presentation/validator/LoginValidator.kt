package com.ramiyon.soulmath.presentation.validator

import android.annotation.SuppressLint
import android.util.Patterns
import com.jakewharton.rxbinding2.widget.RxTextView
import com.ramiyon.soulmath.databinding.FragmentLoginBinding
import io.reactivex.Observable

class LoginValidator: ConstraintValidator<FragmentLoginBinding> {

    private fun FragmentLoginBinding.showPasswordMinimalAlert(isNotValid: Boolean?) {
        edtPassword.error = if (isNotValid == true) "Password minimal 6 karakter" else null
    }

    private fun FragmentLoginBinding.showEmailExistAlert(isNotValid: Boolean?) {
        edtEmail.error = if (isNotValid == true) "Email tidak valid" else null
    }

    @SuppressLint("CheckResult")
    override fun FragmentLoginBinding.validate() {

        val emailObservable = RxTextView.textChanges(edtEmail)
            .map { it.toString() }
            .map { return@map it.isEmpty() }
            .distinctUntilChanged()

        val passwordObservable = RxTextView.textChanges(edtPassword)
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
            btnLogin.isEnabled = it
        }

        val emailStream = RxTextView.textChanges(edtEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(edtPassword)
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
            btnLogin.isEnabled = isValid
        }
    }
}