package com.example.moody.feature.authentification

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.moody.R
import com.example.moody.databinding.FragmentSignUpBinding

import java.util.regex.Pattern


class DataValidators(private var binding: FragmentSignUpBinding) {
    fun validateData(): Boolean = validateFirstName()
            && validateLastName()
            && validateEmail()
            && validatePassword()
            && validateConfirmPassword()


    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.first_name -> {
                    binding.firstNameLayout.isErrorEnabled = false
                }
                R.id.last_name -> {
                    binding.lastNameLayout.isErrorEnabled = false
                }
                R.id.email -> {
                    binding.emailLayout.isErrorEnabled = false
                }
                R.id.password -> {
                    binding.passwordLayout.isErrorEnabled = false
                }
                R.id.confirm_password -> {
                    binding.confirmPasswordLayout.isErrorEnabled = false
                }
            }
        }
    }

    private fun validateFirstName(): Boolean {
        if (binding.firstName.text.toString().isEmpty()) {
            binding.firstNameLayout.error =
                binding.firstName.context.getString(R.string.required_field)
            binding.firstName.requestFocus()
            return false
        } else if (!binding.firstName.text.toString().matches("[a-zA-Z ]+".toRegex())) {
            binding.firstNameLayout.error =
                binding.firstName.context.getString(R.string.invalid_first_name)
            binding.firstName.requestFocus()
            return false
        } else {
            binding.firstNameLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateLastName(): Boolean {
        if (binding.lastName.text.toString().isEmpty()) {
            binding.lastNameLayout.error =
                binding.lastName.context.getString(R.string.required_field)
            binding.lastName.requestFocus()
            return false
        } else if (!binding.lastName.text.toString().matches("[a-zA-Z ]+".toRegex())) {
            binding.lastNameLayout.error =
                binding.lastName.context.getString(R.string.invalid_last_name)
            binding.lastName.requestFocus()
            return false
        } else {
            binding.lastNameLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        if (binding.email.text.toString().isEmpty()) {
            binding.emailLayout.error = binding.email.context.getString(R.string.required_field)
            binding.email.requestFocus()
            return false
        } else if (!binding.email.text.toString()
                .matches("[a-zA-Z0-9+._%\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+".toRegex())
        ) {
            binding.emailLayout.error = binding.email.context.getString(R.string.invalid_email)
            binding.email.requestFocus()
            return false
        } else {
            binding.emailLayout.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        if (binding.password.text.toString().isEmpty()) {
            binding.passwordLayout.error =
                binding.password.context.getString(R.string.required_field)
            binding.password.requestFocus()
            return false
        } else if (binding.password.text.toString().length < 6) {
            binding.passwordLayout.error =
                binding.password.context.getString(R.string.less_than_6_characters)
            binding.password.requestFocus()
            return false
        } else if (!binding.password.text.toString().matches("^.*[a-zA-Z]+.*$".toRegex())) {
            binding.passwordLayout.error =
                binding.password.context.getString(R.string.one_letter_required)
            binding.password.requestFocus()
            return false
        } else if (!binding.password.text.toString().matches("^.*[0-9]+.*$".toRegex())) {
            binding.passwordLayout.error =
                binding.password.context.getString(R.string.one_digit_required)
            binding.password.requestFocus()
            return false
        } else {
            val checkPattern = Pattern.compile("[^a-zA-Z0-9]")
            val matcher = checkPattern.matcher(binding.password.text.toString())
            if (!matcher.find()) {
                binding.passwordLayout.error =
                    binding.password.context.getString(R.string.one_special_character_required)
                binding.password.requestFocus()
                return false

            } else {
                binding.passwordLayout.isErrorEnabled = false
            }
        }
        return true
    }

    private fun validateConfirmPassword(): Boolean {
        if (binding.confirmPassword.text.toString().isEmpty()) {
            binding.confirmPasswordLayout.error =
                binding.confirmPassword.context.getString(R.string.required_field)
            binding.confirmPasswordLayout.requestFocus()
            return false
        } else if (binding.confirmPassword.text.toString() != binding.password.text.toString()) {
            binding.confirmPasswordLayout.error =
                binding.confirmPassword.context.getString(R.string.passwords_not_matching)
            binding.confirmPassword.requestFocus()
            return false
        } else {
            binding.confirmPasswordLayout.isErrorEnabled = false
        }
        return true
    }

}