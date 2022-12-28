package com.example.moody.data

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Wrapper for SharedPreferences that can be used to save primitive data as simple key-value pairs.
 * This class encapsulates all the complexities of dealing with the platform API and exposes a simple read-write property-based interface
 * that is specific to this application.
 */
class PreferenceManager(context: Context) {

    private val preferences = context.applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    var authenticationToken by MutablePreferenceFieldDelegate.String("authenticationToken")

    private sealed class MutablePreferenceFieldDelegate<T>(protected val key: kotlin.String) : ReadWriteProperty<PreferenceManager, T> {

        class Boolean(key: kotlin.String) : MutablePreferenceFieldDelegate<kotlin.Boolean>(key) {

            override fun getValue(thisRef: PreferenceManager, property: KProperty<*>) = thisRef.preferences.getBoolean(key, false)

            override fun setValue(thisRef: PreferenceManager, property: KProperty<*>, value: kotlin.Boolean) = thisRef.preferences.edit().putBoolean(key, value).apply()
        }

        class Int(key: kotlin.String) : MutablePreferenceFieldDelegate<kotlin.Int>(key) {

            override fun getValue(thisRef: PreferenceManager, property: KProperty<*>) = thisRef.preferences.getInt(key, -1)

            override fun setValue(thisRef: PreferenceManager, property: KProperty<*>, value: kotlin.Int) = thisRef.preferences.edit().putInt(key, value).apply()
        }

        class String(key: kotlin.String) : MutablePreferenceFieldDelegate<kotlin.String>(key) {

            override fun getValue(thisRef: PreferenceManager, property: KProperty<*>) = thisRef.preferences.getString(key, "").orEmpty()

            override fun setValue(thisRef: PreferenceManager, property: KProperty<*>, value: kotlin.String) = thisRef.preferences.edit().putString(key, value).apply()
        }
    }
}