package com.org.twitterdata.remote

import com.org.twitterdata.network.iService
import javax.inject.Inject

class ServerDataSource @Inject constructor(
    private val iService: iService
): BaseDataSource() {

    suspend fun getTwitterData() = getResult { iService.getTwitterData() }
}
