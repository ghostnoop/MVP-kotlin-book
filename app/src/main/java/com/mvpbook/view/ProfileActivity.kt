package com.mvpbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvpbook.R
import com.mvpbook.repository.ProfileRepository
import com.mvpbook.strorage.SharedPrefManager
import com.mvpbook.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.toast


class ProfileActivity : AppCompatActivity() {
    private lateinit var profileViewModel: ProfileViewModel

    private val url = "https://pp.userapi.com/c837133/v837133231/21b23/7FWjsNmv62Q.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)




        val factory: ProfileViewModel.Factory =  ProfileViewModel.Factory(ProfileRepository.getInstance())
        profileViewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel::class.java)



        val user = SharedPrefManager.getInstance(this).user
        name_text.text = user.name
        toast(SharedPrefManager.getInstance(this).user.toString())
        profileViewModel.getUser(user,this,ContentLayout)

        profileView.setOnClickListener {
//            SharedPrefManager.getInstance(this).clear()
//            finish()
            toast("HELLO")


        }


    }
}
