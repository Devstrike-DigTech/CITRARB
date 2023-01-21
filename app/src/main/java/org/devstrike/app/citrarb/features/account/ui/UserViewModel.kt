/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.ui

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.account.data.UserApi
import org.devstrike.app.citrarb.features.account.data.models.requests.CreateAccountRequest
import org.devstrike.app.citrarb.features.account.data.models.responses.User
import org.devstrike.app.citrarb.features.account.repositories.UserRepo
import org.devstrike.app.citrarb.features.account.repositories.UserRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.utils.Common.MAXIMUM_PASSWORD_LENGTH
import org.devstrike.app.citrarb.utils.Common.MINIMUM_PASSWORD_LENGTH
import org.devstrike.app.citrarb.utils.SessionManager
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 20/01/2023
 */
/**
 * Created by Richard Uzor  on 20/01/2023
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    val userRepo: UserRepoImpl,
): BaseViewModel(userRepo) {

    //state for register user
    private val _registerState = MutableSharedFlow<Resource<String>>()
    val registerState: SharedFlow<Resource<String>> = _registerState

    //state for login user
    private val _loginState = MutableSharedFlow<Resource<String>>()
    val loginState: SharedFlow<Resource<String>> = _loginState


    //function to create a user
    fun createUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = viewModelScope.launch {
        _registerState.emit(Resource.Loading)//set the state to loading

        //input validations
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            _registerState.emit(Resource.Failure(value = "Some Fields are Empty"))
            return@launch
        }

        //email validity check
        if (!isEmailValid(email)){
            _registerState.emit(Resource.Failure(value = "Email is not Valid"))
            return@launch
        }

        //password validity check
        if (!isPasswordValid(password)){
            _registerState.emit(Resource.Failure(value = "Password length should be between $MINIMUM_PASSWORD_LENGTH and $MAXIMUM_PASSWORD_LENGTH"))
            return@launch
        }

        if (!isPasswordMatching(password, confirmPassword)){
            _registerState.emit(Resource.Failure(value = "Password does not match"))
            return@launch
        }

        //new user
        val newUser = CreateAccountRequest(
            username,
            email,
            password,
            confirmPassword
        )

        //use the state to call the create user api
        _registerState.emit(userRepo.createUser(newUser))

    }

    //function to create a user
    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginState.emit(Resource.Loading) //set the state to loading

        //input validations
        if (email.isEmpty() || password.isEmpty()){
            _registerState.emit(Resource.Failure(value = "Some Fields are Empty"))
            return@launch
        }

        //email validity check
        if (!isEmailValid(email)){
            _loginState.emit(Resource.Failure(value = "Email is not Valid"))
            return@launch
        }

        //logged user
        val newUser = CreateAccountRequest(
            email = email,
            password = password
        )

        //use the state to call the login user api
        _loginState.emit(userRepo.login(newUser))

    }


    //email validation function
    private fun isEmailValid(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //password validation function
    private fun isPasswordValid(password: String): Boolean{
        return (password.length in MINIMUM_PASSWORD_LENGTH..MAXIMUM_PASSWORD_LENGTH)
    }

    //password matching validation function
    private fun isPasswordMatching(password: String, confirmPassword: String): Boolean{
        return password == confirmPassword
    }
}