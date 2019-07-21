

package com.mvpbook.strorage
//

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticlesItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getArticlesDAO(): ArticlesDAO

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "databae.db").build().also { instance = it }
            }
        }
    }
}