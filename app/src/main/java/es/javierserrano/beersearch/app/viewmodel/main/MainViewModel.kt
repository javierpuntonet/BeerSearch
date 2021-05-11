package es.javierserrano.beersearch.app.viewmodel.main

import androidx.lifecycle.*
import es.javierserrano.beersearch.data.model.Beer
import es.javierserrano.beersearch.data.repository.BeerRepository
import kotlinx.coroutines.*

class MainViewModel(private val savedStateHandle: SavedStateHandle?, private val beerRepository: BeerRepository) : ViewModel() {

    private var searchJob: Job? = null
    private var currentPage = 1
    var currentName = ""
        private set
    val status : MutableLiveData<MainViewModelStatus> = MutableLiveData()
    var beerlist : ArrayList<Beer>? = null

    init {
        val savedList : ArrayList<Beer>? = savedStateHandle?.get("list")
        val savedName : String? = savedStateHandle?.get("name")
        val savedPage : Int? = savedStateHandle?.get("page")
        if (savedList != null) {
            beerlist = savedList
        }
        if (savedName != null) {
            currentName = savedName
        }
        if (savedPage != null) {
            currentPage = savedPage
        }
        status.value = MainViewModelStatus(MainViewModelStatus.STATUS.IDLE, )
    }

    fun newSearch(scope: CoroutineScope, beerName: String) {
        val escapedBeerName = beerName.replace(" ", "_")
        if (currentName != "" && currentName == escapedBeerName) {
            status.value = MainViewModelStatus(MainViewModelStatus.STATUS.IDLE)
        } else {
            currentName = escapedBeerName
            currentPage = 1
            status.value = MainViewModelStatus(MainViewModelStatus.STATUS.IDLE)
            search(scope, currentName, 1000)
        }
    }

    fun continueSearch(scope: CoroutineScope) {
        currentPage++
        status.value = MainViewModelStatus(MainViewModelStatus.STATUS.IDLE)
        search(scope, currentName, 0)
    }

    private fun search(scope: CoroutineScope, beerName: String, delayTime: Long) {
        searchJob?.cancel()
        searchJob = scope.launch {
            val page = currentPage
            delay(delayTime)
            if (isActive) {
                if (page == 1) {
                    status.value = MainViewModelStatus(MainViewModelStatus.STATUS.SEARCHING)
                    beerlist = ArrayList()
                }
                try {
                    val tempList : List<Beer> = beerRepository.getBeers(beerName, page)
                    if (isActive) {
                        if (tempList.isNotEmpty()) {
                            beerlist?.addAll(tempList!!)
                            status.value = MainViewModelStatus(
                                MainViewModelStatus.STATUS.OK
                            )
                        } else {
                            status.value = MainViewModelStatus(
                                MainViewModelStatus.STATUS.ERROR
                            )
                        }
                        savedStateHandle?.set("list", beerlist)
                        savedStateHandle?.set("name", currentName)
                        savedStateHandle?.set("page", currentPage)
                    }
                } catch (exception: Exception) {
                    val currentStatus = MainViewModelStatus(MainViewModelStatus.STATUS.ERROR)
                    if (isActive)
                        status.value = currentStatus
                }
            }
        }
    }
}