package com.mvpbook.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.GsonBuilder
import com.mvpbook.model.User
import com.mvpbook.strorage.SharedPrefManager
import com.mvpbook.view.MainActivity
import org.jetbrains.anko.startActivity
import org.json.JSONArray
import org.json.JSONObject
import java.security.AccessController.getContext


class LoginRepository private constructor() {


    companion object {
        @Volatile
        private var instance: LoginRepository? = null

        fun getInstance(): LoginRepository = instance
            ?: synchronized(this) {
                instance
                    ?: LoginRepository().also { instance = it }
            }

    }


    private val AUTH = "Basic " + Base64.encodeToString("belalkhan:123456".toByteArray(), Base64.NO_WRAP)
    private val URL = "http://f0312303.xsph.ru/public/"


    fun insert(body: Int, context: Context) {

        SharedPrefManager.getInstance(context).savereg(body)
        Toast.makeText(context,SharedPrefManager.getInstance(context).isreg.toString(), Toast.LENGTH_LONG).show()

        val intent =
            Intent(context, MainActivity::class.java)
        intent.flags =
            (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) and (Intent.FLAG_ACTIVITY_NO_ANIMATION)

        context.startActivity(intent)
        val activity = context as Activity
        activity.finish()


    }

    fun loginUser(user: User, context: Context, password:String) {
        AndroidNetworking.post(URL + "userlogin")
            .addHeaders("Authorization", AUTH)
            .addBodyParameter("email", user.email)
            .addBodyParameter("password", password)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.e("N21.1", response.toString())
                    SharedPrefManager.getInstance(context).saveUser(user)

                    insert(1, context)

                }

                override fun onError(anError: ANError?) {
                    Log.e("N21.2", anError.toString())

                }


            })
    }


    fun registerUser(user: User, context: Context,password:String) {
        Log.e("reg", "12213")
        AndroidNetworking.post(URL + "createuser")
            .addHeaders("Authorization", AUTH)
            .addBodyParameter("name", user.name)
            .addBodyParameter("email", user.email)
            .addBodyParameter("password", password)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.e("N21.1", response.toString())
                    insert(1, context)

                }

                override fun onError(anError: ANError?) {
                    Log.e("N21.2", anError.toString())

                }
//                Log.e("N21.3",body)


            })


    }


}
