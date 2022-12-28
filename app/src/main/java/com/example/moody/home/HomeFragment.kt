package com.example.moody.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moody.MainActivity
import com.example.moody.MoodyApplication.Companion.preferenceManager
import com.example.moody.R
import com.example.moody.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var greeting: String = "Hello"
    private fun greetings(binding: FragmentHomeBinding) {

        greeting = if (preferenceManager.authenticationToken.isEmpty()) {
            when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                in 3..10 -> getString(R.string.good_morning)
                in 10..14 -> getString(R.string.have_a_nice_day)
                in 14..19 -> getString(R.string.good_afternoon)
                in 19..22 -> getString(R.string.good_evening)
                else -> getString(R.string.good_night)
            }
        } else {
            "Hello, " + preferenceManager.authenticationToken.substringBefore('@').substring(1)
        }

        binding.helloMessage.text = greeting
    }


    private fun checkAuthStateProfileButton(binding: FragmentHomeBinding) {
        if (preferenceManager.authenticationToken.isEmpty()) {
            binding.profileIcon.setImageResource(R.drawable.ic_profile_picture)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        greetings(binding)
        checkAuthStateProfileButton(binding)
        binding.profileIcon.setOnClickListener {
            (activity as MainActivity).openProfileScreen()
        }
        binding.nextButton.setOnClickListener {
            if(binding.movies.isChecked)
            {
                if(binding.happy.isChecked)
                        (activity as MainActivity).openMoviesScreen("Happy")

                if(binding.sad.isChecked)
                        (activity as MainActivity).openMoviesScreen("Sad")

                if(binding.tired.isChecked)
                        (activity as MainActivity).openMoviesScreen("Tired")

                if(binding.enthusiastic.isChecked)
                    (activity as MainActivity).openMoviesScreen("Enthusiastic")

                if(binding.nostalgic.isChecked)
                    (activity as MainActivity).openMoviesScreen("Nostalgic")

            }
        }

    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}