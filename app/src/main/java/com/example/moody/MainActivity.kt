package com.example.moody

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moody.authentification.LogInFragment
import com.example.moody.databinding.ActivityMainBinding
import com.example.moody.authentification.SignUpFragment
import com.example.moody.extensions.handleReplace
import com.example.moody.home.HomeFragment
import com.example.moody.home.ProfileFragment
import utils.MovieFragment
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.Calendar.getInstance

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.handleReplace(newInstance = HomeFragment::newInstance)
        }
    }

    fun openProfileScreen() = supportFragmentManager.handleReplace(
        addToBackStack = true,
        newInstance = ProfileFragment::newInstance
    )

    fun openSignUpScreen() = supportFragmentManager.handleReplace(
        addToBackStack = true,
        newInstance = SignUpFragment::newInstance
    )

    fun openLogInScreen() = supportFragmentManager.handleReplace(
        addToBackStack = true,
        newInstance = LogInFragment::newInstance
    )

    fun goBack() {
        supportFragmentManager.popBackStack()
    }

    fun goHome() = supportFragmentManager.handleReplace(
        addToBackStack = true,
        newInstance = HomeFragment::newInstance
    )
    fun openMoviesScreen(category: String) = supportFragmentManager.handleReplace(
        addToBackStack = true
    ) { MovieFragment.newInstance(category) }

}