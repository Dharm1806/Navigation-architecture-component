package com.example.navigationcomponentsample.viewmodel.uiData


class Resource<T> private constructor(val status: Resource.Status, val data: List<T>?, val exception: String?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: List<T>?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: String?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}