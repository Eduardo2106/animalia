// data/AnimalCategory.kt
package com.example.animalia.data

import java.io.Serializable

// Enum de las categorías de animales que tienes
enum class AnimalCategory : Serializable {
    TERRESTRES, // Terrestres
    ACUATICOS,     // Acuáticos
    AEREOS,       // Aves
    INSECTOS      // Insectos
}