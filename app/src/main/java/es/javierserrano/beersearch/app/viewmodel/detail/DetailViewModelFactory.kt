package es.javierserrano.beersearch.app.viewmodel.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.javierserrano.beersearch.data.model.Beer

class DetailViewModelFactory(private val beer: Beer) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(beer) as T
        }
        throw IllegalArgumentException("Incorrect ViewModel Class")
    }

}