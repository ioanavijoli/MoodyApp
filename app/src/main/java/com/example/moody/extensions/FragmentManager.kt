package com.example.moody.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moody.R
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis


inline fun <reified T : Fragment> FragmentManager.handleReplace(
    tag: String = T::class.java.name,
    addToBackStack: Boolean = false,
    isDetailScreen: Boolean = false,
    @IdRes containerId: Int = R.id.fragment_container,
    crossinline newInstance: () -> T
) {
    beginTransaction().apply {
        val currentFragment = findFragmentById(containerId)
        val newFragment = findFragmentByTag(tag) ?: newInstance()
        if (isDetailScreen) {
            currentFragment?.let {
                currentFragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
                currentFragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            }
            newFragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            newFragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
        } else {
            currentFragment?.let {
                currentFragment.exitTransition = MaterialFadeThrough()
                currentFragment.reenterTransition = MaterialFadeThrough()
            }
            newFragment.enterTransition = MaterialFadeThrough()
            newFragment.returnTransition = MaterialFadeThrough()
        }
        replace(containerId, newFragment, tag)
        if (addToBackStack) {
            addToBackStack(null)
        }
        setReorderingAllowed(true)
        commitAllowingStateLoss()
    }
}