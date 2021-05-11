package es.javierserrano.beersearch.data.repository

import es.javierserrano.beersearch.data.api.PunkApiHelper

class BeerRepository(private val punkApiHelper: PunkApiHelper) {
    suspend fun getBeers(name: String, page: Int) = punkApiHelper.getBeers(name, page)
}