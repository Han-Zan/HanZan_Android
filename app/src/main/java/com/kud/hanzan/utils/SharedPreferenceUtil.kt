package com.kud.hanzan.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil(context: Context) {
    private val spfManager: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun spfClear() {
        spfManager.edit().clear().apply()
    }

    // userToken 메서드
    fun getUserToken(): String {
        return spfManager.getString("user_token", "").toString()
    }
    fun setUserToken(token: String) {
        spfManager.edit().putString("user_token", token).apply()
    }
    fun checkUserToken() : Boolean {
        return spfManager.contains("user_token")
    }

    // userIdx 메서드
    fun getUserIdx(): Long {
        return spfManager.getLong("user_idx", 0)
    }
    fun setUserIdx(token: Long) {
        spfManager.edit().putLong("user_idx", token).apply()
    }
    fun checkUserIdx() : Boolean {
        return spfManager.contains("user_idx")
    }
}