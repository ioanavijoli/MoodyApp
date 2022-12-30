package com.example.moody.feature.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moody.utils.HttpClient
import com.example.moody.MainActivity
import com.example.moody.MoodyApplication
import com.example.moody.R
import com.example.moody.data.UserDetails
import com.example.moody.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfileBinding.bind(view)

        binding.backButton.setOnClickListener { goBack() }

        if (MoodyApplication.preferenceManager.authenticationToken != "") {
            val userId =
                Character.getNumericValue(MoodyApplication.preferenceManager.authenticationToken[0])
            val email = MoodyApplication.preferenceManager.authenticationToken.substring(1)
            val message = "Logged in with email: $email"
            binding.profileInfo.text = message
            binding.logInOutButton.text = getString(R.string.log_out)
            binding.logInOutButton.setOnClickListener {
                logOutUser(userId)
            }
        } else {
            binding.profileInfo.text = getString(R.string.sign_in_profile_text)
            binding.logInOutButton.text = getString(R.string.sign_up_log_in)
            binding.logInOutButton.setOnClickListener {
                (activity as MainActivity).openSignUpScreen()
            }
        }
    }

    private fun logOutUser(id: Int) {
        HttpClient.userService.logOutUser(
            id
        ).enqueue(
            object : retrofit2.Callback<UserDetails> {
                override fun onResponse(
                    call: Call<UserDetails>,
                    response: Response<UserDetails>
                ) {
                    if (response.isSuccessful) {
                        MoodyApplication.preferenceManager.authenticationToken = ""
                        Toast.makeText(
                            context,
                            getString(R.string.logged_out),
                            Toast.LENGTH_LONG
                        ).show()
                        (activity as MainActivity).goHome()

                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.error_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserDetails>, t: Throwable) {

                    t.printStackTrace()
                    Toast.makeText(
                        context,
                        getString(R.string.error_message),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }


    private fun goBack() {
        (activity as MainActivity).goBack()
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}