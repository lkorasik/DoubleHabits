package com.lkorasik.data.net

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val AUTHORIZATION_TOKEN = "0f01165e-6ec7-4c6a-aeb2-01c7e961bad8"
    }
}