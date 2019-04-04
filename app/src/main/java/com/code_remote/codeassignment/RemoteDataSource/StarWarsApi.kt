package com.code_remote.codeassignment.RemoteDataSource

import com.code_remote.codeassignment.models.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface StarWarsApi {

    @GET("people")
    fun getPeople(): Observable<PeopleResponse>

    @GET("starships")
    fun getStarships(): Observable<StarShipResponse>

    @GET("planets")
    fun getPlanets(): Observable<PlanetResponse>

    @GET("{personUrl}")
    fun getPerson(@Path("personUrl") personUrl: String?): Observable<Person>

    @GET("{planetUrl}")
    fun getPlanet(@Path("planetUrl") planetUrl: String): Observable<Planet>
}