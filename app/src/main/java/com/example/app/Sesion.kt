package com.example.app

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity = "esta clase es una tabla" (lo que pide la profe de Room)
@Entity(tableName = "sesiones")
data class Sesion(
    @PrimaryKey val id: String,
    val nombre: String,
    val formato: String,
    val fecha: String,
    val numeroMuestras: Int,
    val catadoresCount: Int,
    val estado: String
)
