package com.code_remote.codeassignment.viewviewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code_remote.codeassignment.RemoteDataSource.StarWarsApi
import com.code_remote.codeassignment.models.Person
import com.code_remote.codeassignment.models.Planet
import com.code_remote.codeassignment.models.Starship
import com.code_remote.codeassignment.utils.SW_BASE_URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    //Dagger Repository injection would've been nice
    private val starWarsInterface: StarWarsApi = Retrofit.Builder()
        .baseUrl(SW_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(StarWarsApi::class.java)

    //To make sure the View won't be changing viewmodel data
    private val _peopleLiveData = MutableLiveData<MutableList<Person>>()
    val peopleLiveDate: LiveData<MutableList<Person>>
        get() = _peopleLiveData

    private val _starshipsLiveData = MutableLiveData<MutableList<Starship>>()
    val starshipsLiveData: LiveData<MutableList<Starship>>
        get() = _starshipsLiveData

    private val _planetsLiveData = MutableLiveData<MutableList<Planet>>()
    val planetsLiveData: LiveData<MutableList<Planet>>
        get() = _planetsLiveData

    //Would've been nice to put in separate Repository class
    fun fetchPeoples() {
        mCompositeDisposable?.add(
            starWarsInterface.getPeople()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ _peopleLiveData.postValue(it.results) }, this::handleError)
        )
    }

    fun fetchPlanets() {
        mCompositeDisposable?.add(
            starWarsInterface.getPlanets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ _planetsLiveData.postValue(it.results) }, this::handleError)
        )
    }

    fun fetchStarships() {
        mCompositeDisposable?.add(
            starWarsInterface.getStarships()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ _starshipsLiveData.postValue(it.results) }, this::handleError)
        )
    }

    private fun handleError(error: Throwable) {
        Log.e("MainViewModel", error.localizedMessage)
    }

}
