package com.prbansal.favtrack

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RepoViewModelFactory (
    private val application: Application) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepoViewModel::class.java)) {
                return RepoViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

}