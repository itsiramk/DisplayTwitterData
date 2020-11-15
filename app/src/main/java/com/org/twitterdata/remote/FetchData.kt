package com.org.twitterdata.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.org.twitterdata.utils.Resource
import com.org.twitterdata.utils.Resource.Status.ERROR
import com.org.twitterdata.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <T, A> fetchData(databaseQuery: () -> LiveData<T>,
                     networkCall: suspend () -> Resource<A>,
                     saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map {
            Resource.success(
                it
            )
        }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == ERROR) {
            emit(
                Resource.error(
                    responseStatus.message!!
                )
            )
            emitSource(source)
        }
    }