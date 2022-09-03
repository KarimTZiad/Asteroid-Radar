package com.example.asteroidradar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.PictureOfDay

@Database(version=1, entities = [Asteroid::class, PictureOfDay::class])
abstract class AsteroidDatabase : RoomDatabase() {

    abstract fun asteroidDao() : AsteroidDao

    companion object{
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getDatabase(context: Context):AsteroidDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null)
                return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroid_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}