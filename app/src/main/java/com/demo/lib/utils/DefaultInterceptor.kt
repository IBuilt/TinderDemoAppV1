package com.demo.lib.utils

import okhttp3.Interceptor
import okhttp3.Response

class DefaultInterceptor : Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
                .header("content-type", "application/json")
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}
