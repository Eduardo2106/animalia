// data/AnimalCategory.kt
package com.example.animalia.data

import java.io.Serializable

// Enum de las categorías de animales que tienes
enum class AnimalCategory : Serializable {
    TERRESTRIAL, // Terrestres
    AQUATIC,     // Acuáticos
    AERIAL,       // Aves
    INSECTS      // Insectos
}