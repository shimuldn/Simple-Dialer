package com.simplemobiletools.dialer.helpers

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.lang.Exception

class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {



        try {
            if (!isInternetAvailable()) {
                val noInternetResponse = "{ \"name\":\"$NO_INTERNET\"}"

                val responseBody = noInternetResponse.toResponseBody("application/json".toMediaTypeOrNull())
                val noInternet = Response.Builder()
                    .code(200)
                    .message(NO_INTERNET)
                    .body(responseBody)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .build()
                return noInternet
            }
//            try {
//                println(chain.proceed(chain.request()))
//            } catch (e: Exception){}
//            try {
//                println(chain.proceed(chain.request()).code)
//            } catch (e: Exception){}
            return chain.proceed(chain.request())
        } catch (e: Exception) {
//            println("Error 112 in intercept ${e.localizedMessage}")
//            println("Error 113 in intercept ${e.cause}")
//            println("Error 114 in intercept ${e.message}")

            val noInternetResponse = "{ \"name\":\"$NO_INTERNET\"}"
            val responseBody = noInternetResponse.toResponseBody("application/json".toMediaTypeOrNull())
            return Response.Builder()
                .code(502)
                .message(e.localizedMessage)
                .body(responseBody)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build()

//            return chain.proceed(chain.request())
        }
    }


//   Nginx stopped = java.net.SocketTimeoutException: failed to connect to true.shimul.me/152.67.10.85 (port 443) from /10.0.2.16 (port 57504) after 10000ms
//   nginx on tc service off = java.lang.IllegalStateException: cannot make a new request because the previous response is still open: please call response.close()
//    Response{protocol=http/1.1, code=502, message=Bad Gateway, url=https://true.shimul.me/search?countryCode=in&q=%2B917483457122}
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}
