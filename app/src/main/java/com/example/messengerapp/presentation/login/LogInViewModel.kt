package com.example.messengerapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.messengerapp.repository.login.LogInRepo
import com.example.messengerapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class LogInViewModel(private val logInRepo: LogInRepo) : ViewModel() {
    fun login(username: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(logInRepo.login(username, password)))
        } catch (e: Exception) {
            emit(
                Resource.Failure(e)
            )
        }
    }
}

class LogInViewModelFactory(private val logInRepo: LogInRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LogInViewModel(logInRepo) as T
    }
}