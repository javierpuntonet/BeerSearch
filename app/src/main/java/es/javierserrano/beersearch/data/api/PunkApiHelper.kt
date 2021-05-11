package es.javierserrano.beersearch.data.api

class PunkApiHelper(private val punkApiService: PunkApiService) {
    companion object {
        const val PER_PAGE = 50
    }
    suspend fun getBeers(name: String, page: Int) = punkApiService.getBeers(name, page, PER_PAGE)
}