/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.utils.SingleLiveEvent
import retrofit2.Response

/**
<<<<<<< Updated upstream
 * Created by Richard Uzor  on 23/12/2022
 */
/**
 * Created by Richard Uzor  on 23/12/2022
=======
 * Created by Richard Uzor  on 24/12/2022
>>>>>>> Stashed changes
 */
open class BaseViewModel : ViewModel() {
    var progressLiveEvent = SingleLiveEvent<Boolean>()
    var errorMessage = SingleLiveEvent<String>()

    inline fun <T> launchAsync(
        crossinline execute: suspend () -> Response<T>,
        crossinline onSuccess: (T) -> Unit,
        showProgress: Boolean = true
    ) {
        viewModelScope.launch {
            if (showProgress)
                progressLiveEvent.value = true
            try {
                val result = execute()
                if (result.isSuccessful)
                    onSuccess(result.body()!!)
                else
                    errorMessage.value = result.message()
            } catch (ex: Exception) {
                errorMessage.value = ex.message
            } finally {
                progressLiveEvent.value = false
            }
        }
    }

    inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (ex: Exception) {
                errorMessage.value = ex.message
            }
        }
    }
}