package es.javierserrano.beersearch.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PunkRetrofitBuilder {
    private const val BASE = "https://api.punkapi.com/v2/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val punkApiService: PunkApiService = getRetrofit().create(PunkApiService::class.java)
}