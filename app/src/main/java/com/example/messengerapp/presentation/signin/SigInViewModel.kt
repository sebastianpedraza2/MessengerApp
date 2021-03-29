package com.example.messengerapp.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.messengerapp.repository.signin.SignInRepo
import com.example.messengerapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class SignInViewModel(private val signInRepo: SignInRepo) : ViewModel() {
    fun login(username: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(
                    signInRepo.login(username, password)
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class SignViewModelFactory(private val signInRepo: SignInRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignInViewModel(signInRepo) as T
    }
}
