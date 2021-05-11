package es.javierserrano.beersearch.data.api

import es.javierserrano.beersearch.data.model.Beer
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApiService {

    @GET("beers")
    suspend fun getBeers(
        @Query("beer_name") name : String,
        @Query("page")  page : Int,
        @Query("per_page") perPage : Int
    ): List<Beer>
}