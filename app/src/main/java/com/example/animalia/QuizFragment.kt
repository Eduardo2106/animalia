package com.example.animalia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.animalia.R
import com.example.animalia.data.AnimalCategory
import com.example.animalia.data.QuizQuestion // Importar la clase de datos
import com.example.animalia.data.QuizRepository // Importar el repositorio de preguntas

class QuizFragment : Fragment() {

    private lateinit var questionTextView: TextView
    private lateinit var explanationTextView: TextView
    private lateinit var optionAButton: Button
    private lateinit var optionBButton: Button
    private lateinit var optionCButton: Button

    private lateinit var questions: List<QuizQuestion>
    private var currentQuestionIndex: Int = 0
    private var quizScore: Int = 0

    private var currentCategory: AnimalCategory? = null

    companion object {
        private const val ARG_CATEGORY = "animal_category"

        fun newInstance(category: AnimalCategory): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putSerializable(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentCategory = arguments?.getSerializable(ARG_CATEGORY) as? AnimalCategory

        // Cargar las preguntas según la categoría
        currentCategory?.let {
            questions = QuizRepository.getQuestions(it)
        } ?: run {
            // Si la categoría es nula, usar una lista vacía para evitar fallos
            questions = emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar vistas
        questionTextView = view.findViewById(R.id.tv_quiz_question)
        explanationTextView = view.findViewById(R.id.tv_quiz_explanation)
        optionAButton = view.findViewById(R.id.btn_option_a)
        optionBButton = view.findViewById(R.id.btn_option_b)
        optionCButton = view.findViewById(R.id.btn_option_c)

        // Esconder la explicación al inicio
        explanationTextView.visibility = View.GONE

        // Asignar listeners a los botones
        optionAButton.setOnClickListener { checkAnswer(0) }
        optionBButton.setOnClickListener { checkAnswer(1) }
        optionCButton.setOnClickListener { checkAnswer(2) }

        // Iniciar el quiz
        displayQuestion()
    }

    private fun displayQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]

            // Resetear la interfaz
            explanationTextView.visibility = View.GONE
            optionAButton.isEnabled = true
            optionBButton.isEnabled = true
            optionCButton.isEnabled = true
            resetButtonColors()

            // Mostrar pregunta
            questionTextView.text = question.text

            // Mostrar opciones
            optionAButton.text = question.options[0].text
            optionBButton.text = question.options[1].text
            optionCButton.text = question.options[2].text

        } else {
            // Quiz Terminado
            showFinalScore()
        }
    }

    private fun checkAnswer(selectedIndex: Int) {
        val question = questions[currentQuestionIndex]
        val selectedOption = question.options[selectedIndex]
        val buttons = listOf(optionAButton, optionBButton, optionCButton)

        // Deshabilitar todos los botones para evitar doble clic
        buttons.forEach { it.isEnabled = false }

        // Mostrar explicación
        explanationTextView.text = question.explanation
        explanationTextView.visibility = View.VISIBLE

        if (selectedOption.isCorrect) {
            quizScore += 1 // Sumar 1 punto por respuesta correcta
            buttons[selectedIndex].setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark))
            Toast.makeText(requireContext(), "¡Correcto!", Toast.LENGTH_SHORT).show()
        } else {
            buttons[selectedIndex].setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))

            // Resaltar la respuesta correcta
            val correctIndex = question.options.indexOfFirst { it.isCorrect }
            buttons[correctIndex].setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark))

            Toast.makeText(requireContext(), "Incorrecto.", Toast.LENGTH_SHORT).show()
        }

        // Avanzar a la siguiente pregunta después de un pequeño retraso
        view?.postDelayed({
            currentQuestionIndex++
            displayQuestion()
        }, 2000)
    }

    private fun resetButtonColors() {
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.white)
        optionAButton.setBackgroundColor(defaultColor)
        optionBButton.setBackgroundColor(defaultColor)
        optionCButton.setBackgroundColor(defaultColor)
    }

    private fun showFinalScore() {
        questionTextView.text = "¡Quiz Terminado! Obtuviste $quizScore de ${questions.size} correctas."
        explanationTextView.text = "¡Felicidades por aprender más sobre los ${currentCategory.toString()}!"
        explanationTextView.visibility = View.VISIBLE

        // Ocultar botones de opción
        optionAButton.visibility = View.GONE
        optionBButton.visibility = View.GONE
        optionCButton.visibility = View.GONE

        // Puedes añadir un botón para volver al menú principal aquí si lo deseas
    }
}