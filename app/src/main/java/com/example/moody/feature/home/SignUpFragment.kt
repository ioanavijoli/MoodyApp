package com.example.moody.feature.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moody.R
import com.example.moody.databinding.FragmentSignUpBinding
import com.example.moody.utils.HttpClient
import com.example.moody.MainActivity
import com.example.moody.feature.authentification.DataValidators
import com.example.moody.data.User
import com.example.moody.data.UserDetails

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var dataValidators: DataValidators

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSignUpBinding.bind(view)

        dataValidators = DataValidators(binding)

        binding.signUpButton.setOnClickListener {
            if (dataValidators.validateData())
                registerUser(binding)
            setupListeners(binding)
        }

        binding.logInButton.setOnClickListener {
              (activity as MainActivity).openLogInScreen()
          }

        binding.toolbar.setNavigationOnClickListener { (activity as MainActivity).goBack() }

    }

    private fun setupListeners(binding: FragmentSignUpBinding) {
        binding.firstName.addTextChangedListener(dataValidators.TextFieldValidation(binding.firstName))
        binding.lastName.addTextChangedListener(dataValidators.TextFieldValidation(binding.lastName))
        binding.email.addTextChangedListener(dataValidators.TextFieldValidation(binding.email))
        binding.password.addTextChangedListener(dataValidators.TextFieldValidation(binding.password))
        binding.confirmPassword.addTextChangedListener(dataValidators.TextFieldValidation(binding.confirmPassword))
    }
    private fun createAccount(binding: FragmentSignUpBinding){

        HttpClient.userService.createUser(
            User ( binding.email.text.toString(),
                binding.password.text.toString(),
                binding.firstName.text.toString(),
                binding.lastName.text.toString() )
        ).enqueue(
            object : Callback<UserDetails> {
                override fun onResponse(
                    call: Call<UserDetails>,
                    response: Response<UserDetails>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            context,
                            getString(R.string.create_user_success),
                            Toast.LENGTH_LONG
                        )
                            .show()
                        (activity as MainActivity).openLogInScreen()
                    } else {
                        binding.emailLayout.error = getString(R.string.create_user_error)
                        binding.email.requestFocus()
                    }
                }

                override fun onFailure(call: Call<UserDetails>, t: Throwable) {

                    t.printStackTrace()
                    Toast.makeText(
                        context,
                        getString(R.string.create_user_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            })
    }
    private fun registerUser(binding: FragmentSignUpBinding) {

        HttpClient.userService.getUserByEmail(binding.email.text.toString()).enqueue( object : Callback<List<UserDetails>> {
            override fun onResponse(
                call: Call<List<UserDetails>>,
                response: Response<List<UserDetails>>
            ) {
                if (response.body()?.isEmpty()  == false) {
                    binding.emailLayout.error = getString(R.string.existing_account_message)
                    binding.email.requestFocus()
                } else {
                    createAccount(binding)
                }
            }

            override fun onFailure(call: Call<List<UserDetails>>, t: Throwable) {

                t.printStackTrace()
                Toast.makeText(
                    context,
                    getString(R.string.create_user_error),
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        })


    }

    companion object {
        fun newInstance() = SignUpFragment()
    }
}