package com.code_remote.codeassignment.models

import java.io.Serializable

data class Person(
    val birth_year: String,
    val created: String,
    val edited: String,
    val eye_color: String,
    val films: List<String>,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    val skin_color: String,
    val species: List<String>,
    val starships: List<String>,
    val url: String,
    val vehicles: List<String>
) : Serializable

data class PeopleResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: MutableList<Person>
)

data class Starship(
    val MGLT: String,
    val cargo_capacity: String,
    val consumables: String,
    val cost_in_credits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val hyperdrive_rating: String,
    val length: String,
    val manufacturer: String,
    val max_atmosphering_speed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<String>,
    val starship_class: String,
    val url: String
) : Serializable

data class StarShipResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: MutableList<Starship>
)

data class Planet(
    val climate: String,
    val created: String,
    val diameter: String,
    val edited: String,
    val films: List<String>,
    val gravity: String,
    val name: String,
    val orbital_period: String,
    val population: String,
    val residents: List<String>,
    val rotation_period: String,
    val surface_water: String,
    val terrain: String,
    val url: String
) : Serializable

data class PlanetResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: MutableList<Planet>
)