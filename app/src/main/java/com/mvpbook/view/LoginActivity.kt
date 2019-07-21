package com.mvpbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.input_card.view.*
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.mvpbook.R
import com.mvpbook.repository.LoginRepository
import com.mvpbook.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.input_card.*
import kotlinx.android.synthetic.main.input_card.view.name_text
import org.jetbrains.anko.toast
import java.lang.reflect.Array
import java.util.ArrayList


class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private var eye: Int = 0
    private var sign: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)



        val factory: UserViewModel.Factory =  UserViewModel.Factory(LoginRepository.getInstance())
        userViewModel = ViewModelProviders.of(this,factory).get(UserViewModel::class.java)


        input_card.eye_image.setOnClickListener {
            if(eye.equals(0)){
                input_card.password_text.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD; eye = 1}
            else{
                input_card.password_text.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD; eye = 0}

        }

        input_card.signup.setOnClickListener {
            if(sign.equals(0)){userViewModel.setVisibily(sign,input_card);signup.text = getText(R.string.sgnin); sign = 1}
            else{userViewModel.setVisibily(sign,input_card); signup.text = getText(R.string.sgnup); sign=0}



        }
        input_card.sign_btn.setOnClickListener {
            toast("sign")
            if(sign.equals(0))
                userViewModel.login(email_text,password_text,this)

            else
                userViewModel.register(name_text,email_text,password_text,repeatpass_text,this)
        }
    }


}
