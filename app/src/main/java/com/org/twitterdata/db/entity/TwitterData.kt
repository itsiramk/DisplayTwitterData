package com.org.twitterdata.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.org.twitterdata.db.entity.TwitterData.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class TwitterData(

    val name: String,

    @PrimaryKey
    val handle: String,
    val text: String,
    val profileImageUrl: String,
    val retweetCount: String,
    val favoriteCount: String
): Parcelable {
    companion object {
        const val TABLE_NAME = "twitterDetails"
        const val COLUMN_NAME = "name"
        const val COLUMN_HANDLE = "handle"
        const val COLUMN_TEXT = "text"
    }
}