package com.org.twitterdata.repository

import androidx.lifecycle.LiveData
import com.org.twitterdata.db.dao.TwitterDao
import com.org.twitterdata.db.entity.TwitterData
import com.org.twitterdata.remote.ServerDataSource
import com.org.twitterdata.remote.fetchData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitterRepository @Inject constructor(
    private val twitterDao: TwitterDao,
    private var serverDataSource: ServerDataSource

) {

    fun getTwitterData() = fetchData(
        databaseQuery = { twitterDao.getTwitterDetails() },
        networkCall = { serverDataSource.getTwitterData() },
        saveCallResult = { twitterDao.insertDetails(it.data) }
    )

    fun getQueryData(query:String): LiveData<List<TwitterData>> {
        return twitterDao.getQueryData(query)
    }

}

