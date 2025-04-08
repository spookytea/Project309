package com.spookytea.project309.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Entity(tableName = "creatures")
@Serializable
data class Creature(
    @PrimaryKey(true) val id: Int = 0,
    var energyLevel: Int = 100,
    var hungerLevel: Int = 100,
    var funLevel: Int = 100,
    val name: String = "",
    val artIndex: Int = 0,
    val hue: Float = 0f
)

@Dao
interface CreatureDao {
    @Query("SELECT * FROM creatures")
    fun getAll(): Flow<List<Creature>>

    @Query("SELECT * FROM creatures WHERE id = :id")
    fun get(id:Int): Flow<Creature>

    @Query("SELECT count(*) FROM creatures LIMIT 1")
    fun rowCount(): Flow<Int>




    @Insert
    suspend fun add(creature: Creature)

}




