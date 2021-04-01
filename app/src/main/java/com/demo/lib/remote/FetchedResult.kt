package com.demo.lib.remote

data class FetchedResult<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): FetchedResult<T> {
            return FetchedResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): FetchedResult<T> {
            return FetchedResult(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): FetchedResult<T> {
            return FetchedResult(Status.LOADING, data, null)
        }
    }
}