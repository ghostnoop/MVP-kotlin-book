package com.mvpbook.strorage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articlesItem: List<ArticlesItem>)

    @Query("DELETE FROM articles_table")
    fun deleteAll()

    @Query("SELECT * from articles_table")
    fun getAll(): LiveData<List<ArticlesItem>>
}