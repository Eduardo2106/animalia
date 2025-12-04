package com.example.animalia.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.animalia.R
import com.example.animalia.data.AnimalCategory

class CardActivityFragment : Fragment() {

    private lateinit var category: AnimalCategory
    private lateinit var gridBoard: GridLayout

    private var firstCard: View? = null
    private var secondCard: View? = null

    private var isBusy = false
    private var score = 0

    companion object {
        var lastScore = 0   // ← Aquí se guarda el puntaje final

        private const val ARG_CATEGORY = "animal_category"

        fun newInstance(category: AnimalCategory): CardActivityFragment {
            val fragment = CardActivityFragment()
            val args = Bundle()
            args.putSerializable(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridBoard = view.findViewById(R.id.game_board_container)

        arguments?.let {
            category = it.getSerializable(ARG_CATEGORY) as AnimalCategory
        }

        initGame()
    }

    // -----------------------------
    //     INICIO DEL JUEGO
    // -----------------------------
    private fun initGame() {
        val cards = getCardsForCategory(category)
        val duplicated = (cards + cards).shuffled()

        gridBoard.removeAllViews()

        for (item in duplicated) {
            val cardView = layoutInflater.inflate(R.layout.item_card, gridBoard, false)
            val img = cardView.findViewById<ImageView>(R.id.card_image)
            val txt = cardView.findViewById<TextView>(R.id.card_text)

            img.visibility = View.INVISIBLE
            txt.visibility = View.INVISIBLE

            if (item.isImage) {
                img.setImageResource(item.value)
            } else {
                txt.text = item.text
            }

            cardView.tag = item.pairId

            cardView.setOnClickListener { handleCardClick(cardView) }
            gridBoard.addView(cardView)
        }
    }

    // -----------------------------
    //      LÓGICA DE CLICKS
    // -----------------------------
    private fun handleCardClick(card: View) {
        if (isBusy) return
        if (card == firstCard) return

        showCard(card)

        if (firstCard == null) {
            firstCard = card
        } else {
            secondCard = card
            isBusy = true

            Handler(Looper.getMainLooper()).postDelayed({
                checkMatch()
            }, 800)
        }
    }

    private fun showCard(card: View) {
        card.findViewById<ImageView>(R.id.card_image).visibility = View.VISIBLE
        card.findViewById<TextView>(R.id.card_text).visibility = View.VISIBLE
        card.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_default_bg))
    }

    private fun hideCard(card: View) {
        card.findViewById<ImageView>(R.id.card_image).visibility = View.INVISIBLE
        card.findViewById<TextView>(R.id.card_text).visibility = View.INVISIBLE
        card.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
    }

    // -----------------------------
    //       REVISAR SI HACE MATCH
    // -----------------------------
    private fun checkMatch() {
        val tag1 = firstCard?.tag as Int
        val tag2 = secondCard?.tag as Int

        if (tag1 == tag2) {
            score += 10
            Toast.makeText(requireContext(), "¡Correcto! +10 puntos", Toast.LENGTH_SHORT).show()

            firstCard?.setOnClickListener(null)
            secondCard?.setOnClickListener(null)

        } else {
            hideCard(firstCard!!)
            hideCard(secondCard!!)
        }

        firstCard = null
        secondCard = null
        isBusy = false

        // -------------------------------
        //   ⚡ GUARDAR PUNTAJE FINAL
        // -------------------------------
        if (allCardsMatched()) {
            lastScore = score
            Toast.makeText(requireContext(), "Juego terminado. Puntaje: $score", Toast.LENGTH_LONG).show()
        }
    }

    // -----------------------------
    //   VERIFICAR SI TERMINÓ EL JUEGO
    // -----------------------------
    private fun allCardsMatched(): Boolean {
        return (0 until gridBoard.childCount).all { index ->
            gridBoard.getChildAt(index).hasOnClickListeners().not()
        }
    }

    // -----------------------------
    //   GENERAR PARES DE CARTAS
    // -----------------------------
    data class CardItem(
        val pairId: Int,
        val isImage: Boolean,
        val value: Int,
        val text: String = ""
    )

    private fun getCardsForCategory(cat: AnimalCategory): List<CardItem> {
        return when (cat) {

            AnimalCategory.TERRESTRIAL -> listOf(
                CardItem(1, true, R.drawable.card_lion),
                CardItem(1, false, 0, "Panthera leo"),

                CardItem(2, true, R.drawable.card_elephant),
                CardItem(2, false, 0, "Loxodonta africana"),

                CardItem(3, true, R.drawable.card_dog),
                CardItem(3, false, 0, "Canis lupus familiaris")
            )

            AnimalCategory.AQUATIC -> listOf(
                CardItem(1, true, R.drawable.card_shark),
                CardItem(1, false, 0, "Carcharodon carcharias"),

                CardItem(2, true, R.drawable.card_dolphin),
                CardItem(2, false, 0, "Delphinus delphis"),

                // 🐋 Manatí (nuevo)
                CardItem(3, true, R.drawable.card_manatee),
                CardItem(3, false, 0, "Trichechus manatus")
            )

            AnimalCategory.AERIAL -> listOf(
                CardItem(1, true, R.drawable.card_eagle),
                CardItem(1, false, 0, "Aquila chrysaetos"),

                CardItem(2, true, R.drawable.card_parrot),
                CardItem(2, false, 0, "Psittaciformes"),

                // 🦅 Cuervo (nuevo)
                CardItem(3, true, R.drawable.card_crow),
                CardItem(3, false, 0, "Corvus corax")
            )

            AnimalCategory.INSECTS -> listOf(
                CardItem(1, true, R.drawable.card_bee),
                CardItem(1, false, 0, "Apis mellifera"),

                CardItem(2, true, R.drawable.card_ant),
                CardItem(2, false, 0, "Formicidae"),

                // 🦗 Grillo (nuevo)
                CardItem(3, true, R.drawable.card_cricket),
                CardItem(3, false, 0, "Gryllidae")
            )
        }
    }
}
