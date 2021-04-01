package com.demo.lib.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

fun <T, A> remoteGet(

        coroutineContext: CoroutineContext = Dispatchers.IO,

        manipulateFetchedResult: suspend (T) -> A,

        fetchResult: suspend () -> FetchedResult<T>): LiveData<FetchedResult<A>> =

        liveData(Dispatchers.IO) {

            emit(FetchedResult.loading())

            val responseStatus = fetchResult.invoke()
            if (responseStatus.status == FetchedResult.Status.SUCCESS) {

                val manipulateFetchedResult = manipulateFetchedResult(responseStatus.data!!)

                emit(FetchedResult.success(manipulateFetchedResult))

            } else if (responseStatus.status == FetchedResult.Status.ERROR) {
                emit(FetchedResult.error(responseStatus.message!!))
            }
        }