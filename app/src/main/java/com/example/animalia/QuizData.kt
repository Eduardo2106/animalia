package com.example.animalia.data

// Define la estructura de una opción de respuesta
data class QuizOption(
    val text: String,
    val isCorrect: Boolean
)

// Define la estructura de una pregunta
data class QuizQuestion(
    val text: String,
    val options: List<QuizOption>,
    val explanation: String // La descripción de la respuesta correcta
)

// Mapa de preguntas por categoría
object QuizRepository {

    // --- Insectos (TERRESTRIAL en tu juego, pero ajustaremos el mapeo) ---
    private val insectQuestions = listOf(
        // Pregunta 1
        QuizQuestion(
            text = "¿Qué animal es omnívoro y vive hasta 3 años?",
            options = listOf(
                QuizOption("Hormiga roja", isCorrect = true),
                QuizOption("Cigarra", isCorrect = false),
                QuizOption("Abeja melífera", isCorrect = false)
            ),
            explanation = "Hormiga roja. ¡Es muy trabajadora y come de todo!"
        ),
        // Pregunta 2
        QuizQuestion(
            text = "¿Qué animal succiona savia de plantas y vive hasta 17 años bajo tierra?",
            options = listOf(
                QuizOption("Hormiga roja", isCorrect = false),
                QuizOption("Cigarra", isCorrect = true),
                QuizOption("Abeja melífera", isCorrect = false)
            ),
            explanation = "Cigarra. ¡Pasa mucho tiempo escondida antes de cantar!"
        ),
        // Pregunta 3
        QuizQuestion(
            text = "¿Qué animal se alimenta de néctar y polen, y produce miel?",
            options = listOf(
                QuizOption("Hormiga roja", isCorrect = false),
                QuizOption("Cigarra", isCorrect = false),
                QuizOption("Abeja melífera", isCorrect = true)
            ),
            explanation = "Abeja melífera. ¡Ayuda a las flores y hace miel deliciosa!"
        ),
        // Pregunta 4
        QuizQuestion(
            text = "¿Qué animal vive en suelos y troncos?",
            options = listOf(
                QuizOption("Hormiga roja", isCorrect = true),
                QuizOption("Cigarra", isCorrect = false),
                QuizOption("Abeja melífera", isCorrect = false)
            ),
            explanation = "Hormiga roja. ¡Trabaja duro en la tierra!"
        )
    )

    // --- Aves (AERIAL) ---
    private val aerialQuestions = listOf(
        // Pregunta 1
        QuizQuestion(
            text = "¿Qué animal es carnívoro y caza conejos y aves?",
            options = listOf(
                QuizOption("Águila real", isCorrect = true),
                QuizOption("Loro amazónico", isCorrect = false),
                QuizOption("Cuervo", isCorrect = false)
            ),
            explanation = "Águila real. ¡Tiene una visión increíble para cazar!"
        ),
        // Pregunta 2
        QuizQuestion(
            text = "¿Qué animal come frutas y semillas, y vive hasta 60 años?",
            options = listOf(
                QuizOption("Águila real", isCorrect = false),
                QuizOption("Loro amazónico", isCorrect = true),
                QuizOption("Cuervo", isCorrect = false)
            ),
            explanation = "Loro amazónico. ¡Es muy inteligente y vive en selvas!"
        ),
        // Pregunta 3
        QuizQuestion(
            text = "¿Qué animal es omnívoro y aprende sonidos?",
            options = listOf(
                QuizOption("Águila real", isCorrect = false),
                QuizOption("Loro amazónico", isCorrect = false),
                QuizOption("Cuervo", isCorrect = true)
            ),
            explanation = "Cuervo. ¡Es súper listo y vive en bosques!"
        ),
        // Pregunta 4
        QuizQuestion(
            text = "¿Qué animal vive en selvas tropicales?",
            options = listOf(
                QuizOption("Águila real", isCorrect = false),
                QuizOption("Loro amazónico", isCorrect = true),
                QuizOption("Cuervo", isCorrect = false)
            ),
            explanation = "Loro amazónico. ¡Le encantan las frutas tropicales!"
        )
    )

    // --- Marinos (AQUATIC) ---
    private val aquaticQuestions = listOf(
        // Pregunta 1
        QuizQuestion(
            text = "¿Qué animal es carnívoro y caza focas y peces?",
            options = listOf(
                QuizOption("Tiburón blanco", isCorrect = true),
                QuizOption("Manatí", isCorrect = false),
                QuizOption("Delfín común", isCorrect = false)
            ),
            explanation = "Tiburón blanco. ¡Es un gran depredador del océano!"
        ),
        // Pregunta 2
        QuizQuestion(
            text = "¿Qué animal come algas y vive hasta 60 años?",
            options = listOf(
                QuizOption("Tiburón blanco", isCorrect = false),
                QuizOption("Manatí", isCorrect = true),
                QuizOption("Delfín común", isCorrect = false)
            ),
            explanation = "Manatí. ¡Es muy pacífico en aguas cálidas!"
        ),
        // Pregunta 3
        QuizQuestion(
            text = "¿Qué animal come peces y calamares, y es muy inteligente?",
            options = listOf(
                QuizOption("Tiburón blanco", isCorrect = false),
                QuizOption("Manatí", isCorrect = false),
                QuizOption("Delfín común", isCorrect = true)
            ),
            explanation = "Delfín común. ¡Juega y salta en el océano!"
        ),
        // Pregunta 4
        QuizQuestion(
            text = "¿Qué animal vive en océanos templados?",
            options = listOf(
                QuizOption("Tiburón blanco", isCorrect = false),
                QuizOption("Manatí", isCorrect = false),
                QuizOption("Delfín común", isCorrect = true)
            ),
            explanation = "Delfín común. ¡Es amigo de los humanos y nada rápido!"
        )
    )

    // --- Terrestres (TERRESTRIAL) ---
    private val terrestrialQuestions = listOf(
        // Pregunta 1
        QuizQuestion(
            text = "¿Qué animal come ramas y frutas, y vive hasta 60 años?",
            options = listOf(
                QuizOption("Elefante africano", isCorrect = true),
                QuizOption("León", isCorrect = false),
                QuizOption("Lobo gris", isCorrect = false)
            ),
            explanation = "Elefante africano. ¡Tiene una trompa larga para comer!"
        ),
        // Pregunta 2
        QuizQuestion(
            text = "¿Qué animal caza antílopes y vive en manadas?",
            options = listOf(
                QuizOption("Elefante africano", isCorrect = false),
                QuizOption("León", isCorrect = true),
                QuizOption("Lobo gris", isCorrect = false)
            ),
            explanation = "León. ¡Es el rey de la sabana africana!"
        ),
        // Pregunta 3
        QuizQuestion(
            text = "¿Qué animal caza en manada y vive en bosques y tundras?",
            options = listOf(
                QuizOption("Elefante africano", isCorrect = false),
                QuizOption("León", isCorrect = false),
                QuizOption("Lobo gris", isCorrect = true)
            ),
            explanation = "Lobo gris. ¡Es un cazador social y aúlla con su familia!"
        ),
        // Pregunta 4
        QuizQuestion(
            text = "¿Qué animal vive en sabanas africanas?",
            options = listOf(
                QuizOption("Elefante africano", isCorrect = true),
                QuizOption("León", isCorrect = false),
                QuizOption("Lobo gris", isCorrect = false)
            ),
            explanation = "Elefante africano. ¡Tiene una trompa larga para comer!"
        )
    )

    // Función para obtener las preguntas según la categoría
    fun getQuestions(category: AnimalCategory): List<QuizQuestion> {
        return when (category) {
            AnimalCategory.TERRESTRIAL -> terrestrialQuestions // Animales terrestres (mamíferos grandes)
            AnimalCategory.AQUATIC -> aquaticQuestions
            AnimalCategory.AERIAL -> aerialQuestions
            AnimalCategory.INSECTS -> insectQuestions // Mapeamos INSECTS a las preguntas de insectos
        }
    }
}