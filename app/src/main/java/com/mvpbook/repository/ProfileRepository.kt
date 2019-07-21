package com.mvpbook.repository

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.google.gson.GsonBuilder
import com.mvpbook.model.User
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.view.*
import org.json.JSONObject


class ProfileRepository private constructor() {
    private val AUTH = "Basic " + Base64.encodeToString("belalkhan:123456".toByteArray(), Base64.NO_WRAP)
    private val URL = "http://f0312303.xsph.ru/public/"

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null

        fun getInstance(): ProfileRepository = instance
            ?: synchronized(this) {
                instance
                    ?: ProfileRepository().also { instance = it }
            }

    }

    fun setView(ContentLayout:View, homefeed: User, context: Context){
        Glide.with(context).load(homefeed.profile_img).apply(RequestOptions.circleCropTransform()).into(ContentLayout.profileView)

        ContentLayout.name_text.text = homefeed.name
    }

    fun getUser(user: User, context: Context,ContentLayout: View) {
        AndroidNetworking.post(URL + "getuser")
            .addHeaders("Authorization", AUTH)
            .addBodyParameter("email", user.email)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {



                    val body = response?.toString()
                    println(body)
                    Log.e("N21.22", body.toString())


                    val gson = GsonBuilder().create()


                    val homeFeed = gson.fromJson(body, User::class.java)
                    setView(ContentLayout,homeFeed,context)
//                    Log.e("N21.12", homeFeed.id.toString())
                    Toast.makeText(context, homeFeed.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onError(anError: ANError?) {
                    Log.e("N21.2", anError.toString())

                }


            })
//        Log.e("N21.2@@@", saru.toString())

    }





}