package com.mvpbook.strorage

import android.annotation.SuppressLint
import android.content.Context
import com.mvpbook.model.User


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SharedPrefManager private constructor(private val mCtx: Context) {


    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: User
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", ""),
                sharedPreferences.getString("name", ""),
                sharedPreferences.getString("profile_image","")
            )
        }
    val isreg:Int
        get(){
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("tre",0)

        }

    fun saveUser(user: User) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.putInt("id", user.id!!)
        editor.putString("email", user.email)
        editor.putString("name", user.name)
        editor.putString("profile_image",user.profile_img)

//        editor.putString("school", user.school)

        editor.apply()

    }
    fun savereg(tre:Int){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("tre",1)
        editor.apply()
    }


    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
//
    companion object {
        val SHARED_PREF_NAME = "my_shared_preff"
        private val SHARED_PREF_ADVER = "my_shared_adver"

        @SuppressLint("StaticFieldLeak")
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}