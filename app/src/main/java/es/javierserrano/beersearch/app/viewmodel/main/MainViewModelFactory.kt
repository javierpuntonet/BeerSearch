package es.javierserrano.beersearch.app.viewmodel.main

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import es.javierserrano.beersearch.data.repository.BeerRepository

class MainViewModelFactory(owner: SavedStateRegistryOwner, private val beerRepository: BeerRepository) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(key: String,modelClass: Class<T>,handle: SavedStateHandle): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(handle, beerRepository) as T
        }
        throw IllegalArgumentException("Incorrect ViewModel Class")
    }

}