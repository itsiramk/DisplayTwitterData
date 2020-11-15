package com.tcs.mobile.ionapp17.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.org.twitterdata.db.entity.TwitterData
import com.org.twitterdata.repository.TwitterRepository


class TwitterViewModel @ViewModelInject constructor(
        twitterRepository: TwitterRepository
) : ViewModel() {

    private lateinit var twitterRepo : TwitterRepository

    init {
        twitterRepo = twitterRepository
    }
    val twitterData = twitterRepository.getTwitterData()

    fun getQueryData(query:String): LiveData<List<TwitterData>> {

        return twitterRepo.getQueryData(query)
    }

}
