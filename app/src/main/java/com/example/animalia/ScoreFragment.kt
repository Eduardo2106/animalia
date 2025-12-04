package com.example.animalia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.animalia.ui.QuizFragment
import com.example.animalia.R
import com.example.animalia.data.AnimalCategory

class ScoreFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scoreText = view.findViewById<TextView>(R.id.tv_score)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val btnPlayAgain = view.findViewById<Button>(R.id.btn_play_again)
        val btnGoToQuiz = view.findViewById<Button>(R.id.btn_go_to_quiz)
        val tvCategoryInfo = view.findViewById<TextView>(R.id.tv_category_info)

        val score = finalScore
        val categoryName = currentCategory

        // Ajustado para el nuevo diseño (muestra solo el número y "pts")
        scoreText.text = "$score\npts"

        // Muestra la categoría
        tvCategoryInfo.text = "Categoría: ${categoryName.toString()}"


        progressBar.max = 90
        progressBar.progress = score

        // Vuelve al fragmento anterior (tablero finalizado)
        btnPlayAgain.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Navega al Quiz, pasando la categoría
        btnGoToQuiz.setOnClickListener {
            currentCategory?.let { category ->
                goToQuizScreen(category)
            }
        }
    }

    private fun goToQuizScreen(category: AnimalCategory) {
        val fragment = QuizFragment.newInstance(category)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}