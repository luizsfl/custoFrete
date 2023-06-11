package com.example.custofrete.presentation.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filterable
import android.widget.Filter;
import com.example.custofrete.domain.model.PlaceApi

class PlaceAutoSuggestAdapter(contextCorrente: Context, resId: Int) :
    ArrayAdapter<Any?>(contextCorrente, resId), Filterable {
    var results: ArrayList<String>? = null
    var resource: Int
    var contextInterno : Context
    var placeApi: PlaceApi = PlaceApi()

    init {
        this.contextInterno = contextCorrente
        resource = resId
    }

    override fun getCount() = results!!.size


    override fun getItem(pos: Int): String? {
        return results!![pos]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    results = placeApi.autoComplete(constraint.toString())
                    filterResults.values = results
                    filterResults.count = results!!.size
                }
                return filterResults
            }

             override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}