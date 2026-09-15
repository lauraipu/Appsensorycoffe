package com.example.app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// @Dao = "las operaciones que se pueden hacer sobre la tabla sesiones"
@Dao
interface SesionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(sesiones: List<Sesion>)

    @Query("SELECT * FROM sesiones ORDER BY fecha DESC")
    suspend fun obtenerTodas(): List<Sesion>

    @Query("SELECT COUNT(*) FROM sesiones")
    suspend fun contar(): Int
}
