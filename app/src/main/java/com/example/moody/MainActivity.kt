package com.example.moody

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moody.feature.authentification.LogInFragment
import com.example.moody.databinding.ActivityMainBinding
import com.example.moody.feature.home.SignUpFragment
import com.example.moody.extensions.handleReplace
import com.example.moody.feature.home.HomeFragment
import com.example.moody.feature.home.ProfileFragment
import com.example.moody.feature.categories.MovieFragment
import com.example.moody.feature.categories.MusicFragment

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

    fun openMusicScreen(category: String) = supportFragmentManager.handleReplace(
        addToBackStack = true
    ) { MusicFragment.newInstance(category) }
}