package com.example.app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sesiones_locales")
data class SesionLocal(
    @PrimaryKey val id: String,
    val nombre: String,
    val codigo_sesion: String,
    val estado: String
)

@Dao
interface SesionDao {
    @Query("SELECT * FROM sesiones_locales")
    fun obtenerTodas(): List<SesionLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(sesion: SesionLocal)
}
