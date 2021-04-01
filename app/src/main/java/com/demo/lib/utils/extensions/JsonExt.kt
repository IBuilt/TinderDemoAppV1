package com.demo.lib.utils.extensions

import androidx.core.text.isDigitsOnly
import com.demo.lib.utils.ContextAccessor
import com.demo.lib.utils.DiModuleUtil
import com.demo.lib.utils.RemoteEntryPoint

fun String.isInt() : Boolean {
    return isDigitsOnly()
}


inline fun <reified T : Any?> T.toJson(): String? {

    val hiltRemoteEntryPoint =
        DiModuleUtil.accessEntryPoint<RemoteEntryPoint>(ContextAccessor.context)

    val adapter = hiltRemoteEntryPoint
        .moshi()
        .adapter(T::class.java)

    return adapter.toJson(this)
}


inline fun <reified T> String.toJsonObject(): T? {

    val hiltRemoteEntryPoint =
        DiModuleUtil.accessEntryPoint<RemoteEntryPoint>(ContextAccessor.context)

    val adapter = hiltRemoteEntryPoint
        .moshi()
        .adapter<T>(T::class.java)

    return adapter.fromJson(this)
}