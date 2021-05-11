package es.javierserrano.beersearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import es.javierserrano.beersearch.app.viewmodel.main.MainViewModel
import es.javierserrano.beersearch.app.viewmodel.main.MainViewModelStatus
import es.javierserrano.beersearch.data.api.PunkApiHelper
import es.javierserrano.beersearch.data.api.PunkRetrofitBuilder
import es.javierserrano.beersearch.data.repository.BeerRepository
import kotlinx.coroutines.GlobalScope
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun correctSearch() {
        val mainViewModel = MainViewModel(null, BeerRepository(PunkApiHelper(PunkRetrofitBuilder.punkApiService)))

        mainViewModel.newSearch(GlobalScope, "aaa")
        var values = mainViewModel.status.test().awaitValue()

        while (values.value().status == MainViewModelStatus.STATUS.IDLE || values.value().status == MainViewModelStatus.STATUS.SEARCHING) {
            values = mainViewModel.status.test().awaitValue()
        }

        assertEquals(values.value().status, MainViewModelStatus.STATUS.OK)
        assertTrue(mainViewModel.beerlist!!.isNotEmpty())
    }

    @Test
    fun emptySearch() {
        val mainViewModel = MainViewModel(null, BeerRepository(PunkApiHelper(PunkRetrofitBuilder.punkApiService)))

        mainViewModel.newSearch(GlobalScope, "")
        var values = mainViewModel.status.test().awaitValue()

        while (values.value().status == MainViewModelStatus.STATUS.IDLE || values.value().status == MainViewModelStatus.STATUS.SEARCHING) {
            values = mainViewModel.status.test().awaitValue()
        }

        assertEquals(values.value().status, MainViewModelStatus.STATUS.ERROR)
    }

    @Test
    fun emptyResults() {
        val mainViewModel = MainViewModel(null, BeerRepository(PunkApiHelper(PunkRetrofitBuilder.punkApiService)))

        mainViewModel.newSearch(GlobalScope, "abcdefgh")
        var values = mainViewModel.status.test().awaitValue()

        while (values.value().status == MainViewModelStatus.STATUS.IDLE || values.value().status == MainViewModelStatus.STATUS.SEARCHING) {
            values = mainViewModel.status.test().awaitValue()
        }

        assertEquals(values.value().status, MainViewModelStatus.STATUS.ERROR)
        assertTrue(mainViewModel.beerlist!!.isEmpty())
    }

    @Test
    fun newPageSearch() {
        val mainViewModel = MainViewModel(null, BeerRepository(PunkApiHelper(PunkRetrofitBuilder.punkApiService)))

        mainViewModel.newSearch(GlobalScope, "aaa")
        var values = mainViewModel.status.test().awaitValue()

        while (values.value().status == MainViewModelStatus.STATUS.IDLE || values.value().status == MainViewModelStatus.STATUS.SEARCHING) {
            values = mainViewModel.status.test().awaitValue()
        }

        val firstPageLastBeer = mainViewModel.beerlist!!.last()

        mainViewModel.continueSearch(GlobalScope)
        values = mainViewModel.status.test().awaitValue()
        while (values.value().status == MainViewModelStatus.STATUS.IDLE || values.value().status == MainViewModelStatus.STATUS.SEARCHING) {
            values = mainViewModel.status.test().awaitValue()
        }

        val secondPageLastBeer = mainViewModel.beerlist!!.last()

        assertEquals(values.value().status, MainViewModelStatus.STATUS.OK)
        assert(mainViewModel.beerlist!!.isNotEmpty())
        assert(firstPageLastBeer.Name != secondPageLastBeer.Name)
    }
}