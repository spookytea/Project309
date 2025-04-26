package com.spookytea.project309.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Persist data with Room (no date) Android Developers. Available at: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room.
@Database(entities = [Creature::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {
    abstract fun creatureDao() : CreatureDao

    companion object {
        //Ensures only one database instance as they are computationally expensive
        @Volatile
        private var db: DB? = null
        fun getDB(context: Context): DB {
            return db?: synchronized(this){
                Room.databaseBuilder(context, DB::class.java, "creature_db")
                    .build()
                    .also { db = it }
            }
        }
    }
}