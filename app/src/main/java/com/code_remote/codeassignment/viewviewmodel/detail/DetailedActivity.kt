package com.code_remote.codeassignment.viewviewmodel.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.code_remote.codeassignment.R
import kotlinx.android.synthetic.main.detailed_activity.*

class DetailedActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.code_remote.codeassignment.R.layout.detailed_activity)

        viewModel = ViewModelProviders.of(this).get(DetailedViewModel::class.java)
        viewModel.processIntent(intent)
        viewModel.detailedArguments.observe(this, Observer {
            if (it != null) {
                viewModel.clearDetailedArguments()
                startDetailedActivity(it)
            }
        })

        //String resources would've been nice
        viewModel.person.observe(this, Observer {
            details1Label.text = "Name:"
            details1Value.text = it.name
            details2Label.text = "Date of Birth:"
            details2Value.text = it.birth_year
            details3Label.text = "Weight in kg:"
            details3Value.text = it.mass
            details4Label.text = "Skin color:"
            details4Value.text = it.skin_color
            details5Label.text = "Homeworld:"
            details5Value.text = getString(R.string.sw_planet_click_to_see_where)
            details5Value.setOnClickListener { v ->
                viewModel.fetchHomeworld(it)
                //Loader would've been nice
            }
        })

        viewModel.starship.observe(this, Observer {
            details1Label.text = "Name of Starship:"
            details1Value.text = it.name
            details2Label.text = "Starship class:"
            details2Value.text = it.starship_class
            details3Label.text = "Length:"
            details3Value.text = it.length
            details4Label.text = "Passengers:"
            details4Value.text = it.passengers
            details5Label.text = "One of the Pilots:"
            details5Value.text = if (it.pilots.isEmpty()) {
                getString(R.string.sw_starship_no_one_flies_this_starship)
            } else {
                getString(R.string.sw_person_who_might_it_be)
            }
            details5Value.setOnClickListener { v ->
                viewModel.fetchPilot(it)
            }
        })

        viewModel.planet.observe(this, Observer {
            details1Label.text = "Planet name:"
            details1Value.text = it.name
            details2Label.text = "Population:"
            details2Value.text = it.population
            details3Label.text = "Diameter:"
            details3Value.text = it.diameter
            details4Label.text = "Gravity:"
            details4Value.text = it.gravity
            details5Label.text = "Famous People who lived here"
            details5Value.text = if (it.residents.isEmpty()) {
                getString(R.string.sw_planet_no_one_lives_here)
            } else {
                getString(R.string.sw_person_who_might_it_be)
            }
            details5Value.setOnClickListener { v ->
                viewModel.fetchResident(it)
            }
        })
    }

    private fun startDetailedActivity(bundle: Bundle) {
        startActivity(Intent(this, DetailedActivity::class.java).apply { putExtras(bundle) })
    }

}