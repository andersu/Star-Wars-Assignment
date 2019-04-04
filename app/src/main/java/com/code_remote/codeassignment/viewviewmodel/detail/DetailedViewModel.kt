package com.code_remote.codeassignment.viewviewmodel.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code_remote.codeassignment.RemoteDataSource.StarWarsApi
import com.code_remote.codeassignment.models.Person
import com.code_remote.codeassignment.models.Planet
import com.code_remote.codeassignment.models.Starship
import com.code_remote.codeassignment.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class DetailedViewModel : ViewModel() {

    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()


    private var _detailedArguments = MutableLiveData<Bundle>()
    val detailedArguments: LiveData<Bundle>
        get() = _detailedArguments

    private var _person = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = _person

    private var _starship = MutableLiveData<Starship>()
    val starship: LiveData<Starship>
        get() = _starship

    private var _planet = MutableLiveData<Planet>()
    val planet: LiveData<Planet>
        get() = _planet

    //Dagger Repository injection would've been nice
    private var starWarsInterface: StarWarsApi = Retrofit.Builder()
        .baseUrl(SW_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(StarWarsApi::class.java)

    fun processIntent(intent: Intent) {
        when (intent.getIntExtra(SW_DATA_TYPE, SW_PEOPLES)) {
            SW_PEOPLES -> {
                _person.postValue(intent.getSerializableExtra(::SW_PEOPLES.name) as Person)
            }
            SW_STARSHIPS -> {
                _starship.postValue(intent.getSerializableExtra(::SW_STARSHIPS.name) as Starship)
            }
            SW_PLANETS -> {
                _planet.postValue(intent.getSerializableExtra(::SW_PLANETS.name) as Planet)
            }
        }
    }

    fun fetchHomeworld(it: Person) {
        mCompositeDisposable?.add(
            starWarsInterface.getPlanet(it.homeworld.removePrefix(SW_BASE_URL))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _detailedArguments
                    _detailedArguments.postValue(Bundle().apply {
                        putInt(SW_DATA_TYPE, SW_PLANETS)
                        putSerializable(::SW_PLANETS.name, it)
                    })
                }, this::handleError)
        )
    }

    private fun handleError(error: Throwable) {
        Log.e("MainViewModel", error.localizedMessage)
    }

    fun fetchPilot(it: Starship?) {
        fetchPerson(it?.pilots?.get(Random.nextInt(0, it.pilots.size)))
    }

    fun fetchResident(it: Planet?) {
        fetchPerson(it?.residents?.get(Random.nextInt(0, it.residents.size)))
    }

    private fun fetchPerson(personUrl: String?) {
        mCompositeDisposable?.add(
            starWarsInterface.getPerson(personUrl?.removePrefix(SW_BASE_URL))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _detailedArguments
                    _detailedArguments.postValue(Bundle().apply {
                        putInt(SW_DATA_TYPE, SW_PEOPLES)
                        putSerializable(::SW_PEOPLES.name, it)
                    })
                }, this::handleError)
        )
    }

    fun clearDetailedArguments() {
        _detailedArguments.value = null
    }
}
