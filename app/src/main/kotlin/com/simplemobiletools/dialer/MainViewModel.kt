package com.simplemobiletools.dialer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplemobiletools.dialer.helpers.NetworkConnectionInterceptor
import com.simplemobiletools.dialer.models.TrueCallerResponse
import com.simplemobiletools.dialer.services.TrueCallerService
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel(private val trueCallerService: TrueCallerService) : ViewModel() {

    val myResponse: MutableLiveData<Response<TrueCallerResponse>> = MutableLiveData()

    fun getResponse(mobileNumber: String, authorizationToken: String, serverMode: String, networkConnectionInterceptor: NetworkConnectionInterceptor) {
        viewModelScope.launch {
            val response: Response<TrueCallerResponse> =
                trueCallerService.getTrueCallerResponse(mobileNumber, authorizationToken, serverMode, networkConnectionInterceptor)
            myResponse.value = response
        }
    }
}
