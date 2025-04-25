package com.spookytea.project309.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Entity(tableName = "creatures")
@Serializable
data class Creature(
    @Transient @PrimaryKey(true) val id: Int = 0,
    val energyLevel: Int = 100,
    val hungerLevel: Int = 100,
    val funLevel: Int = 100,
    val name: String = "",
    val artIndex: Int = 0,
    val hue: Float = 0f,
    @Transient val sleepUntil: Long = 0L
)

@Dao
interface CreatureDao {
    @Query("SELECT * FROM creatures")
    fun getAll(): Flow<List<Creature>>

    @Query("SELECT * FROM creatures")
    fun getAllNotFlow(): List<Creature>

    @Query("SELECT * FROM creatures WHERE id = :id")
    fun get(id:Int): Flow<Creature>

    @Query("SELECT * FROM creatures LIMIT 1 OFFSET :id")
    fun getCurrent(id:Int): Creature

    @Query("SELECT * FROM creatures LIMIT 1 OFFSET :id")
    suspend fun getCurrentSuspend(id:Int): Creature

    @Query("SELECT count(*) FROM creatures LIMIT 1")
    fun rowCount(): Flow<Int>

    @Update
    suspend fun update(c: Creature)


    @Insert
    suspend fun add(creature: Creature)

}




