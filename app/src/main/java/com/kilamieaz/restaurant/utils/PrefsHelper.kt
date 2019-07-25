package com.kilamieaz.restaurant.utils

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper {
    internal var prefs: SharedPreferences
    internal var context: Context

    var PREF = "USER_SESSION"

    private var instance: PrefsHelper? = null
    fun sharedInstance(context: Context): PrefsHelper {
        if (instance == null) {
            instance = PrefsHelper(context)
        }
        return instance as PrefsHelper
    }

    constructor(context: Context) {
        this.context = context
        this.prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    }

    fun setLogin(boolean: Boolean){
        val editor = prefs.edit()
        editor.putBoolean("isLogin", boolean)
        editor.apply()
    }

    fun getLogin() : Boolean {
        return prefs.getBoolean("isLogin", false)
    }


    fun setValue(pref: String, value: String) {
        val editor = prefs.edit()
        editor.putString(pref, value)
        editor.apply()
    }

    fun getValue(pref: String): String? {
        return prefs.getString(pref, "TIDAK ADA")
    }
}