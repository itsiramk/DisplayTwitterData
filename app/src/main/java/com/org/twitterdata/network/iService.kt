package com.org.twitterdata.network

import com.org.twitterdata.db.entity.TwitterDetails
import retrofit2.Response
import retrofit2.http.GET

interface iService
{

    @GET("tweets")
    suspend fun getTwitterData(): Response<TwitterDetails>

}