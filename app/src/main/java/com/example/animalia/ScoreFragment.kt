package com.example.animalia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.animalia.R
import com.example.animalia.data.AnimalCategory

class ScoreFragment : Fragment() {

    companion object {
        private const val ARG_CATEGORY = "animal_category"

        fun newInstance(category: AnimalCategory): ScoreFragment {
            val fragment = ScoreFragment()
            val args = Bundle()
            args.putSerializable(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    private var currentCategory: AnimalCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentCategory = arguments?.getSerializable(ARG_CATEGORY) as? AnimalCategory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scoreText = view.findViewById<TextView>(R.id.tv_score)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val btnPlayAgain = view.findViewById<Button>(R.id.btn_play_again)

        val score = CardActivityFragment.lastScore
        val categoryName = currentCategory

        scoreText.text = "Tu puntaje es: $score\nCategoría: $categoryName"

        progressBar.max = 30
        progressBar.progress = score.coerceAtMost(30)

        btnPlayAgain.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
