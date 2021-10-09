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
import com.abdalltif.paybackchallenge.R
import com.abdalltif.paybackchallenge.databinding.FragmentMainBinding
import com.abdalltif.paybackchallenge.ui.adapters.PhotoAdapter
import com.abdalltif.paybackchallenge.utils.HelperMethods
import com.abdalltif.paybackchallenge.ui.SharedViewModel
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
        HelperMethods.initPhotoRecyclerView(requireContext(), binding, photoAdapter)

        if (viewModel.hasInternetConnection()) {

            // Search "fruits" results.
            HelperMethods.searchPhotos(viewModel, getString(R.string.fruits), true)
            // Observe live data.
            observeRemoteData()

        } else {

            // Search cached photos if no internet connection.
            HelperMethods.searchPhotos(viewModel, getString(R.string.fruits), false)
            observeLocalDatabase()

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
                // todo: check internet connection.
                searchPhotos(query, true)
                searchView.clearFocus()
                return false
            }
        })
    }

    private fun searchPhotos(query: String, isRomote: Boolean){
        val searchQuery = "%$query%"
        if (isRomote)
            viewModel.searchPhotos(searchQuery)
        else
            viewModel.searchLocalPhotos(searchQuery)
    }

    private fun observeLocalDatabase(){
        viewModel.photosLocalData.observe(requireActivity(), { photos ->

            if (photos.isEmpty())
                binding.txtNotFound.visibility = VISIBLE
            else
                binding.txtNotFound.visibility = GONE

            binding.progressBar.visibility = GONE

            photoAdapter.setPhotos(photos)

        } )
    }

    private fun observeRemoteData(){
        viewModel.photosData.observe(requireActivity(), { response ->
            if ( response.isSuccessful ) {

                if (response.body()!!.totalHits <= 0)
                    binding.txtNotFound.visibility = VISIBLE
                else
                    binding.txtNotFound.visibility = GONE

                binding.progressBar.visibility = GONE

                val photoList = response.body()!!.hits
                photoAdapter.setPhotos(photoList)

                // Cache data in background
                photoList.forEach { photo ->
                    viewModel.addPhoto(photo)
                }

            } else {

                binding.progressBar.visibility = GONE
                Toast.makeText(requireContext(), "Network Error!", Toast.LENGTH_SHORT).show()

            }
        } )
    }
}






















