package com.code_remote.codeassignment.viewviewmodel.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_remote.codeassignment.R
import com.code_remote.codeassignment.utils.*
import com.code_remote.codeassignment.viewviewmodel.detail.DetailedActivity
import kotlinx.android.synthetic.main.main_tab_fragment.view.*
import java.io.Serializable

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: MainRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainTabPos = arguments?.getInt(SW_MAIN_TAB_POS) ?: SW_PEOPLES

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        when (mainTabPos) {
            SW_PEOPLES -> {
                createPeopleRecyclerListAdapter()
            }
            SW_STARSHIPS -> {
                createStarshipReclcyerListAdapter()
            }
            SW_PLANETS -> {
                createPlanetsRecyclerListAdapter()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        val view = inflater.inflate(R.layout.main_tab_fragment, container, false)
        view.recyclerView.layoutManager = linearLayoutManager
        if (adapter != null) {
            view.recyclerView.adapter = adapter
        }

        return view
    }

    private fun createPeopleRecyclerListAdapter() {
        adapter = MainRecyclerAdapter(this.context, viewModel.peopleLiveDate) {
            startDetailedActivity(::SW_PEOPLES.name, SW_PEOPLES, it)
        }
        viewModel.peopleLiveDate.observe(this, Observer { adapter?.notifyDataSetChanged() })
        //Loader would've been nice
        viewModel.fetchPeoples()
    }

    private fun createStarshipReclcyerListAdapter() {
        adapter = MainRecyclerAdapter(this.context, {
            startDetailedActivity(::SW_STARSHIPS.name, SW_STARSHIPS, it)
        }, viewModel.starshipsLiveData)
        viewModel.starshipsLiveData.observe(this, Observer { adapter?.notifyDataSetChanged() })
        //Loader would've been nice
        viewModel.fetchStarships()
    }

    private fun createPlanetsRecyclerListAdapter() {
        adapter = MainRecyclerAdapter({
            startDetailedActivity(::SW_PLANETS.name, SW_PLANETS, it)
        }, viewModel.planetsLiveData, this.context)
        viewModel.planetsLiveData.observe(this, Observer { adapter?.notifyDataSetChanged() })
        //Loader would've been nice
        viewModel.fetchPlanets()
    }

    //Dagger router injection would've been nice
    private fun startDetailedActivity(key: String, datatype: Int, serializableObject: Serializable?) {
        activity?.window?.exitTransition = android.transition.Fade()
        exitTransition = android.transition.Fade()
        startActivity(Intent(context, DetailedActivity::class.java).apply {
            putExtra(key, serializableObject)
            putExtra(SW_DATA_TYPE, datatype)
        })
    }
}