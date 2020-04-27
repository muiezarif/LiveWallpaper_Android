package com.muiezarif.livewallpaper.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class ApplicationSharedPreference
private constructor(context: Context?){
    private var instanceSP:SharedPreferences
    private val  context:Context?
    init {
        this.context=context
        instanceSP=PreferenceManager.getDefaultSharedPreferences(this.context?.applicationContext)
    }

    fun readString(key: String): String {
        return instanceSP.getString(key, "") ?: ""
    }

    fun readBool(key: String, defaultValue: Boolean): Boolean {
        return instanceSP.getBoolean(key, defaultValue)
    }
    fun readInt(key: String, defaultValue: Int): Int {
        return instanceSP.getInt(key, defaultValue)
    }

    //Save String Preference
    fun savePreferences(key: String, value: String): Boolean? {
        return instanceSP.edit()?.putString(key, value)?.commit()
    }

    //Save Boolean Preference
    fun savePreferences(key: String, value: Boolean): Boolean? {
        return instanceSP.edit()?.putBoolean(key, value)?.commit()
    }
    //Save Int Preference
    fun savePreferences(key: String, value: Int): Boolean? {
        return instanceSP.edit()?.putInt(key, value)?.commit()
    }

    companion object{
        private var instance: ApplicationSharedPreference? = null

//        @Synchronized
        fun getInstance(context: Context?): ApplicationSharedPreference {
            if (null== instance) {
                instance = ApplicationSharedPreference(context)
            }
            return instance as ApplicationSharedPreference
        }


    }

}