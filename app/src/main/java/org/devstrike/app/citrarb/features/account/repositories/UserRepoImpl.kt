/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.repositories

import dagger.hilt.android.scopes.ActivityRetainedScoped
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.features.account.data.UserApi
import org.devstrike.app.citrarb.features.account.data.models.requests.CreateAccountRequest
import org.devstrike.app.citrarb.features.account.data.models.responses.CreateAccountResponse
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.isNetworkConnected
import org.devstrike.app.citrarb.utils.SessionManager
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 20/01/2023
 */
/**
 * Created by Richard Uzor  on 20/01/2023
 */
@ActivityRetainedScoped
class UserRepoImpl @Inject constructor(
    val userApi: UserApi,
    val sessionManager: SessionManager
) : UserRepo, BaseRepo(){
    override suspend fun createUser(user: CreateAccountRequest): Resource<String> {
        return try {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }

            //create user using api
            val result = userApi.createAccount(user)
            if (result.status == "success") {
                //save the details in data store
                sessionManager.updateSession(result.token, result.user.username ?: "", result.user.email)
                Resource.Success("User Created Successfully!")
            } else {
                Resource.Failure(value = result.status)
            }

        } catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(value =e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun login(user: CreateAccountRequest): Resource<String> {
        return try {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }

            //log in user using api
            val result = userApi.login(user)
            if (result.status == "success") {
                //save details in the data store
                sessionManager.updateSession(result.token, result.user.username ?: "", result.user.email)
                //getUserInfo //get details from server for specific user once user logs in
                Resource.Success(value = "Logged In Successfully!")
            } else {
                Resource.Failure(value = result.status)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(value = e.message ?: "Some Problem Occurred!")
        }    }

    override suspend fun getUser(): Resource<CreateAccountResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Resource<String> {
        return try {
            //clear the data store
            sessionManager.logout()
            Resource.Success("Logged Out Successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(value = e.message ?: "Some Problem Occurred!")
        }    }
}