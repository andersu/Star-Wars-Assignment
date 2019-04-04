package com.code_remote.codeassignment.viewviewmodel.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.code_remote.codeassignment.R
import com.code_remote.codeassignment.models.Person
import com.code_remote.codeassignment.models.Planet
import com.code_remote.codeassignment.models.Starship
import kotlinx.android.synthetic.main.main_list_item.view.*
import kotlin.random.Random


class MainRecyclerAdapter private constructor(private val context: Context?) :
    RecyclerView.Adapter<MainRecyclerAdapter.ItemHolder>() {

    private var people: LiveData<MutableList<Person>>? = null
    private lateinit var personClickListener: (person: Person?) -> Unit
    private var starShips: LiveData<MutableList<Starship>>? = null
    private lateinit var starShipClickListener: (starship: Starship?) -> Unit
    private var planets: LiveData<MutableList<Planet>>? = null
    private lateinit var planetChipClickListener: (planet: Planet?) -> Unit

    constructor(
        context: Context?,
        people: LiveData<MutableList<Person>>,
        personClickListener: (person: Person?) -> Unit
    ) : this(context) {
        this.people = people
        this.personClickListener = personClickListener
    }

    constructor(
        context: Context?,
        starShipClickListener: (starship: Starship?) -> Unit,
        starShips: LiveData<MutableList<Starship>>
    ) : this(context) {
        this.starShips = starShips
        this.starShipClickListener = starShipClickListener
    }


    constructor(
        planetChipClickListener: (planet: Planet?) -> Unit,
        planets: LiveData<MutableList<Planet>>,
        context: Context?
    ) : this(context) {
        this.planets = planets
        this.planetChipClickListener = planetChipClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.main_list_item, parent, false)
        )
    }


    override fun getItemCount(): Int {
        val count = when {
            people != null -> people?.value?.size ?: 0
            starShips != null -> starShips?.value?.size ?: 0
            planets != null -> planets?.value?.size ?: 0
            else -> 0
        }
        return count
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        when {
            people != null -> holder.bindPeople(people?.value?.get(position), personClickListener)
            starShips != null -> holder.bindStarShips(starShips?.value?.get(position), starShipClickListener)
            planets != null -> holder.bindPlanets(planets?.value?.get(position), planetChipClickListener)
        }
    }

    class ItemHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val STAR_WARS_IAMGES = intArrayOf(
            R.drawable.luke,
            R.drawable.darth_vader,
            R.drawable.millanium_falcon,
            R.drawable.x_wing,
            R.drawable.death_star,
            R.drawable.c3po,
            R.drawable.yoda,
            R.drawable.r2d2,
            R.drawable.star_destroyer,
            R.drawable.star_wars,
            R.drawable.tatooine,
            R.drawable.phantom_manace
        )

        private var view: View = v

        fun bindPeople(person: Person?, listener: (personClickListener: Person?) -> Unit?) {
            setViews("Name: ${person?.name}", "Height:\n${person?.height}")
            view.setOnClickListener {
                listener(person)
            }
        }

        fun bindStarShips(starship: Starship?, starShipClickListener: (starship: Starship?) -> Unit) {
            setViews("Name of Starship: ${starship?.name}", "Starship class:\n${starship?.starship_class}")
            view.setOnClickListener {
                starShipClickListener(starship)
            }
        }

        fun bindPlanets(planet: Planet?, planetChipClickListener: (planet: Planet?) -> Unit) {
            setViews("Planet name: ${planet?.name}", "Population:\n${planet?.population}")
            view.setOnClickListener {
                planetChipClickListener(planet)
            }
        }

        private fun setViews(title: String, description: String) {
            view.title.text = title
            view.description.text = description
            view.itemImage.setImageResource(STAR_WARS_IAMGES[Random.nextInt(0, STAR_WARS_IAMGES.size)])
        }

    }
}