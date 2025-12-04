// ui/MenuFragment.kt (VERSION CORREGIDA CON CARDVIEWS)
package com.example.animalia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.animalia.MainActivity
import com.example.animalia.R
import com.example.animalia.data.AnimalCategory

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener referencias a los CardViews (usaremos los IDs card_...)
        val cardTerrestres: CardView = view.findViewById(R.id.card_terrestres)
        val cardAcuaticos: CardView = view.findViewById(R.id.card_acuaticos)
        val cardAereos: CardView = view.findViewById(R.id.card_aereos)
        val cardInsectos: CardView = view.findViewById(R.id.card_insectos)

        setupCategoryCard(cardTerrestres, AnimalCategory.TERRESTRIAL)
        setupCategoryCard(cardAcuaticos, AnimalCategory.AQUATIC)
        setupCategoryCard(cardAereos, AnimalCategory.AERIAL)
        setupCategoryCard(cardInsectos, AnimalCategory.INSECTS)
    }

    private fun setupCategoryCard(cardView: CardView, category: AnimalCategory) {
        // Configuramos el listener en toda la tarjeta
        cardView.setOnClickListener {
            // Navega al fragmento de juego y pasa la categoría seleccionada
            val gameFragment = GameFragment.newInstance(category)
            (activity as? MainActivity)?.navigateTo(gameFragment, "GameFragment")
        }
    }
}