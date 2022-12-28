package com.example.moody.authentification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moody.HttpClient
import com.example.moody.MainActivity
import com.example.moody.MoodyApplication
import com.example.moody.R
import com.example.moody.data.UserDetails
import com.example.moody.databinding.FragmentLogInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInFragment : Fragment(R.layout.fragment_log_in) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLogInBinding.bind(view)

        binding.logIn.setOnClickListener {
            logInUser(binding)
        }

        binding.signUpButton.setOnClickListener {
            (activity as MainActivity).openSignUpScreen()
        }
        binding.toolbar.setNavigationOnClickListener { (activity as MainActivity).goBack() }
    }

    private fun authenticateUser(userDetails: UserDetails) {
        HttpClient.userService.logInUser(
            UserDetails(
                userDetails.email,
                userDetails.password,
                userDetails.id
            )
        ).enqueue(
            object : Callback<UserDetails> {
                override fun onResponse(
                    call: Call<UserDetails>,
                    response: Response<UserDetails>
                ) {
                    if (response.isSuccessful) {
                        val token = response.body()!!.id.toString() + response.body()!!.email
                        MoodyApplication.preferenceManager.authenticationToken = token
                        Toast.makeText(
                            context,
                            getString(R.string.log_in_success),
                            Toast.LENGTH_LONG
                        ).show()
                        (activity as MainActivity).goHome()

                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.invalid_credentials),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserDetails>, t: Throwable) {

                    t.printStackTrace()
                    Toast.makeText(
                        context,
                        getString(R.string.invalid_credentials),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    private fun logInUser(binding: FragmentLogInBinding) {

        HttpClient.userService.getUserByEmail(binding.email.text.toString())
            .enqueue(object : Callback<List<UserDetails>> {
                override fun onResponse(
                    call: Call<List<UserDetails>>,
                    response: Response<List<UserDetails>>
                ) {
                    if (response.body()?.isEmpty() == false) {
                        if (response.body()!![0].password == binding.password.text.toString()) {
                            authenticateUser(response.body()!![0])
                        } else {
                            binding.passwordLayout.error = getString(R.string.verify_password)
                            binding.password.requestFocus()
                            setUpOnChangeListeners(binding)
                        }
                    } else {
                        binding.emailLayout.error = getString(R.string.verify_email)
                        binding.email.requestFocus()
                        binding.passwordLayout.error = getString(R.string.verify_password)
                        binding.password.requestFocus()
                        setUpOnChangeListeners(binding)
                    }
                }

                override fun onFailure(call: Call<List<UserDetails>>, t: Throwable) {

                    t.printStackTrace()
                    Toast.makeText(
                        context,
                        getString(R.string.invalid_credentials),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }


    private fun setUpOnChangeListeners(binding: FragmentLogInBinding) {
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                binding.emailLayout.isErrorEnabled = false
                binding.passwordLayout.isErrorEnabled = false


            }
        })
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                binding.emailLayout.isErrorEnabled = false
                binding.passwordLayout.isErrorEnabled = false
            }
        })

    }

    companion object {
        fun newInstance() = LogInFragment()
    }
}