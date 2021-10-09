package com.abdalltif.paybackchallenge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdalltif.paybackchallenge.data.models.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = true)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {
        private var INSTANCE: PhotoDatabase? = null

        fun getDatabase(context: Context): PhotoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null )
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotoDatabase::class.java,
                    "photo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}