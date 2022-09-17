package com.ramiyon.soulmath.presentation.validator

import com.jakewharton.rxbinding2.widget.RxTextView
import com.ramiyon.soulmath.databinding.FragmentRegisterBinding

class RegisterValidator: ConstraintValidator<FragmentRegisterBinding> {
    override fun FragmentRegisterBinding.validate() {

        val usernameStream = RxTextView.textChanges(edtUsername)
            .map { it.toString() }
            .map { it.isEmpty() }
            .subscribe { isNotValid ->

            }
    }
}