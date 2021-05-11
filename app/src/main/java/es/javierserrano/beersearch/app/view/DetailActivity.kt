package es.javierserrano.beersearch.app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.javierserrano.beersearch.R
import es.javierserrano.beersearch.app.viewmodel.detail.DetailViewModel
import es.javierserrano.beersearch.app.viewmodel.detail.DetailViewModelFactory
import es.javierserrano.beersearch.data.model.Beer
import es.javierserrano.beersearch.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupViewModel()
        setupBinding()
    }

    private fun setupBinding() {
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        binding.viewModel = viewModel
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, DetailViewModelFactory(intent.getParcelableExtra<Beer>("beer")!!)).get(DetailViewModel::class.java)
    }
}