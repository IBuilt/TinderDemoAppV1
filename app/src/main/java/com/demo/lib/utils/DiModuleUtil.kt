package com.demo.lib.utils

import android.content.Context
import dagger.hilt.android.EntryPointAccessors

object DiModuleUtil {

    inline fun <reified T> accessEntryPoint(context: Context): T {
        return EntryPointAccessors.fromApplication(context, T::class.java)
    }
}