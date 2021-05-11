package es.javierserrano.beersearch.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.javierserrano.beersearch.R
import es.javierserrano.beersearch.data.model.Beer
import kotlinx.android.synthetic.main.item_beer.view.*

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var newPageListener : (()->Unit)? = null
    var beerClicked :((Beer)->Unit)? = null
    var showLoading : Boolean = false
    var showError : Boolean = false

    private val beers : ArrayList<Beer> = ArrayList()

    private inner class BeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(beer: Beer) {
            itemView.apply {
                nameTV.text = beer.Name
                setOnClickListener {
                    beerClicked?.invoke(beer)
                }
            }
        }
    }

    private inner class LoaderWithSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            newPageListener?.invoke()
        }
    }

    private inner class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private inner class NoResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && beers.size == 0 && showLoading)
            1
        else if (position != 0 && position == beers.size)
            2
        else if (showError)
            3
        else
            0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> BeerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false))
            1 -> LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))
            2 -> LoaderWithSearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))
            else -> NoResultsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_no_results, parent, false))
        }

    override fun getItemCount(): Int =
        when {
            showLoading -> beers.size+1
            beers.size == 0 && showError -> 1
            else -> beers.size
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                (holder as BeerViewHolder).bind(beers[position])
            }
            1 -> {

            }
            2 -> {
                (holder as LoaderWithSearchViewHolder).bind()
            }
            3 -> {

            }
        }
    }

    fun clear() {
        this.beers.apply {
            clear()
        }
    }

    fun addBeers(beers: List<Beer>) {
        this.beers.apply {
            clear()
            addAll(beers)
        }
    }
}