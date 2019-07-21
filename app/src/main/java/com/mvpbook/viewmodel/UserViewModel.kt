package com.mvpbook.viewmodel

import android.content.Context
import android.view.View
import android.widget.EditText

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvpbook.empty
import com.mvpbook.model.User
import com.mvpbook.repository.LoginRepository
import com.mvpbook.strorage.SharedPrefManager
import kotlinx.android.synthetic.main.input_card.view.*

class UserViewModel internal constructor(private val loginRepository: LoginRepository) : ViewModel() {


    fun loginUser(user: User, context: Context, password:String)  =    loginRepository.loginUser(user,context,password)
    fun registerUser(user: User,context:Context,password: String)=    loginRepository.registerUser(user,context,password)


    fun login(email_text:EditText,password_text:EditText,context: Context){
        if (!empty(email_text)) return
        if (!empty(password_text)) return
        if (email_text.text.indexOf("@") == -1) { email_text.error = "not email"; email_text.requestFocus(); return }

        val email = email_text.text.toString()
        val password = password_text.text.toString()
        val user = User(email = email, name = "", id = 0, profile_img = "")

        loginUser(user,context,password)
    }
    fun register(name_text:EditText,email_text:EditText,password_text:EditText,repeatpass_text:EditText,context: Context){
        if (!empty(email_text)) return
        if (!empty(password_text)) return
        if (email_text.text.indexOf("@") == -1 && email_text.text.indexOf("@")>=email_text.text.indexOf(".")) {
            email_text.error = "not email"; email_text.requestFocus(); return}
        if(repeatpass_text.text.toString() != password_text.text.toString()) {password_text.error="!="; password_text.requestFocus();return}

        val name = name_text.text.toString()
        val email = email_text.text.toString()
        val password = password_text.text.toString()
        val user = User(email = email, name = name, id = 0, profile_img = "")
        SharedPrefManager.getInstance(context).saveUser(user)


        registerUser(user,context,password)



    }

    fun setVisibily(sign:Int,incard:View){
       if (sign.equals(0)) {
           incard.name_text.visibility = View.VISIBLE
           incard.repeatpass_text.visibility = View.VISIBLE
           incard.eye_image.visibility = View.GONE
           incard.Sign_text.text = "Please Sign Up"
           incard.screen_text.text = "Join us"
       } else {
           incard.name_text.visibility = View.GONE
           incard.repeatpass_text.visibility = View.GONE
           incard.eye_image.visibility = View.VISIBLE
           incard.Sign_text.text = "Please Sign In"
           incard.screen_text.text = "Welcome\nback"
       }
   }













    class Factory(private val loginRepository: LoginRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) = UserViewModel(loginRepository) as T
    }

}