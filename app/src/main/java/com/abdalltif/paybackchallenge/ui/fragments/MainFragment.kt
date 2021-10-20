package com.abdalltif.paybackchallenge.ui.fragments

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdalltif.paybackchallenge.R
import com.abdalltif.paybackchallenge.data.models.Photo
import com.abdalltif.paybackchallenge.databinding.FragmentMainBinding
import com.abdalltif.paybackchallenge.adapters.PhotoAdapter
import com.abdalltif.paybackchallenge.ui.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val photoAdapter by lazy { PhotoAdapter(this.findNavController()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Init data binding.
        binding = DataBindingUtil.setContentView<FragmentMainBinding>(requireActivity(), R.layout.fragment_main).apply {
            this.mviewmodel = viewModel
            this.lifecycleOwner = requireActivity()
        }

        // Init recycler view
        setupPhotoRecyclerView()

        setupPullToRefresh()

        if (viewModel.hasInternetConnection()) {

            // Search "fruits" results.
            viewModel.searchPhotos(getString(R.string.fruits))
            // Observe live data.
            observeRemoteData()

        } else {
            binding.root.snakeBar(getString(R.string.no_connection), Snackbar.LENGTH_LONG)
            // Search cached photos if no internet connection.
            viewModel.searchLocalPhotos("%${getString(R.string.fruits)}%")
            observeLocalData()

        }

        // Set option menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.photo_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                binding.progressBar.visibility = VISIBLE

                if (viewModel.hasInternetConnection())
                    viewModel.searchPhotos(query)
                else
                    viewModel.searchLocalPhotos("%$query%")

                searchView.clearFocus()
                return false
            }
        })
    }

    private fun setupPhotoRecyclerView(){
        val recyclerView = binding.recyclerPhotos
        recyclerView.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupPullToRefresh() {
        binding.swipe.setOnRefreshListener {
            viewModel.searchPhotos(getString(R.string.fruits))
        }

    }

    private fun observeRemoteData(){
        viewModel.photosData.observe(requireActivity(), { response ->
            if ( response.isSuccessful ) {

                if (response.body()!!.totalHits <= 0)
                    binding.txtNotFound.visibility = VISIBLE
                else
                    binding.txtNotFound.visibility = GONE

                binding.progressBar.visibility = GONE
                binding.swipe.isRefreshing = false

                val photoList = response.body()!!.hits
                photoAdapter.setPhotos(photoList)

                // Cache the data in background thread.
                cacheData(photoList)

            } else {

                binding.progressBar.visibility = GONE
                binding.swipe.isRefreshing = false
                Toast.makeText(requireContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show()

            }
        } )
    }

    private fun cacheData(photoList: List<Photo>) {
        photoList.forEach { photo ->
            viewModel.addPhoto(photo)
        }
    }

    private fun observeLocalData(){
        viewModel.photosLocalData.observe(requireActivity(), { photos ->
            if (photos.isEmpty())
                binding.txtNotFound.visibility = VISIBLE
            else
                binding.txtNotFound.visibility = GONE

            binding.progressBar.visibility = GONE

            binding.swipe.isRefreshing = false

            photoAdapter.setPhotos(photos)
        } )
    }

    fun View.snakeBar(message: String, duration: Int = Snackbar.LENGTH_LONG){
        Snackbar.make(this, message, duration).show()
    }
}






















