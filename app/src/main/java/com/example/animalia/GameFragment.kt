// ui/GameFragment.kt
package com.example.animalia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.animalia.MainActivity
import com.example.animalia.R
import com.example.animalia.data.AnimalCategory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.animalia.ui.CardActivityFragment
import com.example.animalia.ui.ScoreFragment
class GameFragment : Fragment() {

    private lateinit var currentCategory: AnimalCategory
    private lateinit var cardContainer: FrameLayout // Contenedor para la vista de tarjetas/score
    private var isShowingCards = true // Estado para alternar entre vistas

    companion object {
        private const val ARG_CATEGORY = "animal_category"

        // Método estático para crear instancias con argumentos
        fun newInstance(category: AnimalCategory): GameFragment {
            val fragment = GameFragment()
            val args = Bundle()
            args.putSerializable(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recupera la categoría de los argumentos
        arguments?.getSerializable(ARG_CATEGORY)?.let {
            currentCategory = it as AnimalCategory
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Usa un layout con el contenedor de navegación
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardContainer = view.findViewById(R.id.card_score_container)

        // Configuración de los botones de navegación interna (Home y Score)
        val fabHome: FloatingActionButton = view.findViewById(R.id.fab_home)
        val fabScore: FloatingActionButton = view.findViewById(R.id.fab_score)

        // 1. Botón de Casa (Vector Asset: ic_baseline_home_24)
        fabHome.setOnClickListener {
            // Vuelve al menú principal. Quitamos este fragmento del back stack.
            (activity as? MainActivity)?.supportFragmentManager?.popBackStack()
        }

        // 2. Botón de Score (Vector Asset: ic_baseline_leaderboard_24)
        fabScore.setOnClickListener {
            toggleCardScoreView()
        }

        // Carga la vista inicial: Tarjetas
        loadCardView()
    }

    /** Alterna entre la vista de tarjetas y la vista de score/progreso. */
    private fun toggleCardScoreView() {
        isShowingCards = !isShowingCards
        if (isShowingCards) {
            loadCardView()
        } else {
            loadScoreView()
        }
    }

    /** Carga el fragmento de la actividad de emparejamiento. */
    private fun loadCardView() {
        val cardFragment = CardActivityFragment.newInstance(currentCategory)
        childFragmentManager.beginTransaction()
            .replace(R.id.card_score_container, cardFragment)
            .commit()
    }

    /** Carga el fragmento del score y la barra de progreso. */
    private fun loadScoreView() {
        val scoreFragment = ScoreFragment.newInstance(currentCategory)
        childFragmentManager.beginTransaction()
            .replace(R.id.card_score_container, scoreFragment)
            .commit()
    }
}