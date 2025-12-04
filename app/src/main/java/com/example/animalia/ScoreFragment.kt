package com.example.animalia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager // Importar FragmentManager para popBackStack
import com.example.animalia.R
import com.example.animalia.data.AnimalCategory

class ScoreFragment : Fragment() {

    // Necesitas asegurar que QuizFragment esté accesible
    // import com.example.animalia.ui.QuizFragment

    companion object {
        private const val ARG_CATEGORY = "animal_category"
        private const val ARG_SCORE = "final_score"

        fun newInstance(category: AnimalCategory, score: Int): ScoreFragment {
            val fragment = ScoreFragment()
            val args = Bundle()
            args.putSerializable(ARG_CATEGORY, category)
            args.putInt(ARG_SCORE, score)
            fragment.arguments = args
            return fragment
        }
    }

    private var currentCategory: AnimalCategory? = null
    private var finalScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentCategory = arguments?.getSerializable(ARG_CATEGORY) as? AnimalCategory
        finalScore = arguments?.getInt(ARG_SCORE, 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup ?,
        savedInstanceState: Bundle?
    ): View? {
        // Asegúrate de que R.layout.fragment_score exista y tenga el diseño de botones
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scoreText = view.findViewById<TextView>(R.id.tv_score)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val btnPlayAgain = view.findViewById<Button>(R.id.btn_play_again)
        val btnGoToQuiz = view.findViewById<Button>(R.id.btn_go_to_quiz)
        val btnGoToMenu = view.findViewById<Button>(R.id.btn_go_to_menu) // 🆕 NUEVO BOTÓN
        val tvCategoryInfo = view.findViewById<TextView>(R.id.tv_category_info)

        val score = finalScore
        val categoryName = currentCategory

        scoreText.text = "$score\npts"

        tvCategoryInfo.text = "Categoría: ${categoryName.toString()}"


        progressBar.max = 90
        progressBar.progress = score

        // 🕹️ 1. Volver a Jugar (Vuelve al último CardActivityFragment)
        btnPlayAgain.setOnClickListener {
            // Elimina ScoreFragment, revelando CardActivityFragment
            parentFragmentManager.popBackStack()
        }

        // 🕹️ 2. Ir a Quiz (Navega a la pantalla de preguntas)
        btnGoToQuiz.setOnClickListener {
            currentCategory?.let { category ->
                goToQuizScreen(category)
            }
        }

        // 🕹️ 3. Cambiar Categoría (Vuelve al Menú Inicial)
        btnGoToMenu.setOnClickListener {
            goToMainMenu()
        }
    }

    private fun goToQuizScreen(category: AnimalCategory) {
        // Asegúrate de que QuizFragment esté definido y la importación sea correcta
        val fragment = QuizFragment.newInstance(category)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun goToMainMenu() {
        // 🎯 Elimina *todos* los fragmentos de la pila de retroceso
        // Esto regresa al fragmento base (que asumimos es el menú de categorías)
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}