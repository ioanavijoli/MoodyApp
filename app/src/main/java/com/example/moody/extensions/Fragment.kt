package com.example.moody.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.withArguments(bundleOperations: (Bundle) -> Unit): T = apply {
    arguments = Bundle().apply { bundleOperations(this) }
}
