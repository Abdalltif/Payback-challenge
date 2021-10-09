package com.abdalltif.paybackchallenge.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdalltif.paybackchallenge.databinding.FragmentMainBinding
import com.abdalltif.paybackchallenge.ui.adapters.PhotoAdapter
import com.abdalltif.paybackchallenge.ui.SharedViewModel

object HelperMethods {

    fun initPhotoRecyclerView(context: Context, binding: FragmentMainBinding, photoAdapter: PhotoAdapter){
        val recyclerView = binding.recyclerPhotos
        recyclerView.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun searchPhotos(viewModel: SharedViewModel, query: String, isRomote: Boolean){
        val searchQuery = "%$query%"
        if (isRomote)
            viewModel.searchPhotos(searchQuery)
        else
            viewModel.searchLocalPhotos(searchQuery)
    }

}