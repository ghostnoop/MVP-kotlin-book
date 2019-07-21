package com.mvpbook.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvpbook.model.User
import com.mvpbook.repository.ProfileRepository

class ProfileViewModel internal constructor(private val profileRepository: ProfileRepository) : ViewModel() {


    fun getUser(user: User, context: Context, ContentLayout: View) = profileRepository.getUser(user,context,ContentLayout)





    class Factory(private val profileRepository: ProfileRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) = ProfileViewModel(profileRepository) as T
    }
}