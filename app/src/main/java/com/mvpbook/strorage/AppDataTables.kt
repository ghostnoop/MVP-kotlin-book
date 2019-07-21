
package com.mvpbook.strorage

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "articles_table", indices = [Index("title", unique = true)])
data class ArticlesItem(
    @PrimaryKey(autoGenerate = true) @NonNull var id: Int,
    var author: String,
    var description: String,
    var title: String,
    var img: String,
    var rating: Float,
    var date: String,
    var cost: String
)