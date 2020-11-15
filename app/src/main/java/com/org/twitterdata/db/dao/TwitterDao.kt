package com.org.twitterdata.db.dao;

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.twitterdata.db.entity.TwitterData

@Dao
interface TwitterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(twitterDetails: List<TwitterData>)

    @Query("SELECT * FROM TwitterDetails")
    fun getTwitterDetails(): LiveData<List<TwitterData>>

    @Query("SELECT * FROM TwitterDetails where name Like :query " +
            "or handle Like :query or text Like :query")
    fun getQueryData(query:String): LiveData<List<TwitterData>>
}