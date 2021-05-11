package es.javierserrano.beersearch.app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import es.javierserrano.beersearch.R
import es.javierserrano.beersearch.app.adapter.MainAdapter
import es.javierserrano.beersearch.app.viewmodel.main.MainViewModel
import es.javierserrano.beersearch.app.viewmodel.main.MainViewModelFactory
import es.javierserrano.beersearch.app.viewmodel.main.MainViewModelStatus
import es.javierserrano.beersearch.data.api.PunkApiHelper
import es.javierserrano.beersearch.data.api.PunkRetrofitBuilder
import es.javierserrano.beersearch.data.repository.BeerRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupView()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(this, BeerRepository(PunkApiHelper(PunkRetrofitBuilder.punkApiService)))).get(MainViewModel::class.java)
    }

    private fun setupView() {
        if (viewModel.currentName != "")
            searchET.setText(viewModel.currentName)

        searchET.addTextChangedListener {
            it?.let {
                viewModel.newSearch(this.lifecycle.coroutineScope, it.toString())
            }
        }
        resultsRV.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter()
        adapter.apply {
            newPageListener = {
                viewModel.continueSearch(this@MainActivity.lifecycle.coroutineScope)
            }
            beerClicked = {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("beer", it)
                startActivity(intent)
            }
        }
        resultsRV.addItemDecoration(
            DividerItemDecoration(
                resultsRV.context,
                DividerItemDecoration.HORIZONTAL
            )
        )
        resultsRV.adapter = adapter
        viewModel.beerlist?.let {
            adapter.addBeers(viewModel.beerlist!!)
        }
    }

    private fun setupObservers() {
        viewModel.status.observe(this) {
            when (it.status) {
                MainViewModelStatus.STATUS.IDLE -> {

                }
                MainViewModelStatus.STATUS.SEARCHING -> {
                    adapter.apply {
                        showLoading = true
                        showError = false
                        clear()
                        notifyDataSetChanged()
                    }
                }
                MainViewModelStatus.STATUS.OK -> {
                    adapter.apply {
                        showLoading = viewModel.beerlist!!.size % PunkApiHelper.PER_PAGE  == 0
                        showError = false
                        addBeers(viewModel.beerlist!!)
                        notifyDataSetChanged()
                    }
                }
                MainViewModelStatus.STATUS.ERROR -> {
                    adapter.apply {
                        showLoading = false
                        showError = true
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}