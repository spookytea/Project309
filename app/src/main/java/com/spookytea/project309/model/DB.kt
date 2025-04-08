package com.spookytea.project309.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
@Database(entities = [Creature::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {
    abstract fun creatureDao() : CreatureDao

    companion object {
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