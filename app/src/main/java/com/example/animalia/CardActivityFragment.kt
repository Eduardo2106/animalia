package com.example.animalia.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalia.R
import com.example.animalia.data.AnimalCategory

class CardActivityFragment : Fragment() {

    private lateinit var category: AnimalCategory
    private lateinit var recycler: RecyclerView

    private var firstCard: View? = null
    private var secondCard: View? = null
    private var isBusy = false
    private var score = 0

    companion object {
        private const val ARG_CATEGORY = "animal_category"

        fun newInstance(category: AnimalCategory): CardActivityFragment {
            return CardActivityFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CATEGORY, category)
                }
            }
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

        recycler = view.findViewById(R.id.recycler_cards)
        category = requireArguments().getSerializable(ARG_CATEGORY) as AnimalCategory

        startGame()
    }

    private fun startGame() {
        val baseList = getCardsForCategory(category)
        val shuffled = baseList.shuffled()

        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.setHasFixedSize(true)
        recycler.adapter = CardAdapter(shuffled)

        score = 0
    }

    // =============================================================
    // ADAPTER
    // =============================================================
    inner class CardAdapter(private val items: List<CardItem>) :
        RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

        inner class CardViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val img = v.findViewById<ImageView>(R.id.card_image)
            val txt = v.findViewById<TextView>(R.id.card_text)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val v = layoutInflater.inflate(R.layout.item_card, parent, false)
            return CardViewHolder(v)
        }

        override fun onBindViewHolder(h: CardViewHolder, pos: Int) {
            val item = items[pos]

            h.img.visibility = View.INVISIBLE
            h.txt.visibility = View.INVISIBLE

            if (item.isImage) {
                h.img.setImageResource(item.value)
            } else {
                h.txt.text = item.text
            }

            h.itemView.tag = item.pairId
            h.itemView.setOnClickListener { handleCardClick(h.itemView) }
        }

        override fun getItemCount() = items.size
    }

    // =============================================================
    // LÓGICA DE CLICS
    // =============================================================
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
            }, 600)
        }
    }

    private fun showCard(card: View) {
        card.findViewById<ImageView>(R.id.card_image).visibility = View.VISIBLE
        card.findViewById<TextView>(R.id.card_text).visibility = View.VISIBLE

        card.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.card_default_bg)
        )
    }

    private fun hideCard(card: View) {
        card.findViewById<ImageView>(R.id.card_image).visibility = View.INVISIBLE
        card.findViewById<TextView>(R.id.card_text).visibility = View.INVISIBLE

        card.setBackgroundColor(
            ContextCompat.getColor(requireContext(), android.R.color.white)
        )
    }

    // =============================================================
    // VALIDACIÓN DE PARES
    // =============================================================
    private fun checkMatch() {
        val id1 = firstCard?.tag as Int
        val id2 = secondCard?.tag as Int

        if (id1 == id2) {
            score += 30

            firstCard?.setOnClickListener(null)
            secondCard?.setOnClickListener(null)

            Toast.makeText(requireContext(), "¡Correcto!", Toast.LENGTH_SHORT).show()

            if (score == 90) {
                goToScoreScreen()
            }

        } else {
            hideCard(firstCard!!)
            hideCard(secondCard!!)
        }

        firstCard = null
        secondCard = null
        isBusy = false
    }

    // =============================================================
    // IR A LA PANTALLA DE SCORE
    // =============================================================
    private fun goToScoreScreen() {
        val fragment = ScoreFragment.newInstance(category, score)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    // =============================================================
    // DATOS DE LAS CARTAS (5 pares)
    // =============================================================
    data class CardItem(
        val pairId: Int,
        val isImage: Boolean,
        val value: Int,
        val text: String = ""
    )

    private fun getCardsForCategory(cat: AnimalCategory): List<CardItem> {
        return when (cat) {

            AnimalCategory.TERRESTRES -> listOf(
                CardItem(1, true, R.drawable.card_lion),
                CardItem(1, false, 0, "Panthera leo"),
                CardItem(2, true, R.drawable.card_elephant),
                CardItem(2, false, 0, "Loxodonta africana"),
                CardItem(3, true, R.drawable.card_dog),
                CardItem(3, false, 0, "Canis lupus familiaris")
            )

            AnimalCategory.ACUATICOS -> listOf(
                CardItem(1, true, R.drawable.card_shark),
                CardItem(1, false, 0, "Carcharodon carcharias"),
                CardItem(2, true, R.drawable.card_dolphin),
                CardItem(2, false, 0, "Delphinus delphis"),
                CardItem(3, true, R.drawable.card_manatee),
                CardItem(3, false, 0, "Trichechus manatus")
            )

            AnimalCategory.AEREOS -> listOf(
                CardItem(1, true, R.drawable.card_eagle),
                CardItem(1, false, 0, "Aquila chrysaetos"),
                CardItem(2, true, R.drawable.card_parrot),
                CardItem(2, false, 0, "Psittaciformes"),
                CardItem(3, true, R.drawable.card_crow),
                CardItem(3, false, 0, "Corvus corax"),
            )

            AnimalCategory.INSECTOS -> listOf(
                CardItem(1, true, R.drawable.card_bee),
                CardItem(1, false, 0, "Apis mellifera"),
                CardItem(2, true, R.drawable.card_ant),
                CardItem(2, false, 0, "Formicidae"),
                CardItem(3, true, R.drawable.card_cricket),
                CardItem(3, false, 0, "Gryllidae")
            )
        }
    }
}