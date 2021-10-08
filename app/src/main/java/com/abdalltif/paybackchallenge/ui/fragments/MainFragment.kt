package com.abdalltif.paybackchallenge.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdalltif.paybackchallenge.R
import com.abdalltif.paybackchallenge.databinding.FragmentMainBinding
import com.abdalltif.paybackchallenge.ui.adapters.PhotoAdapter
import com.abdalltif.paybackchallenge.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: SharedViewModel by activityViewModels()
    // Pass navController to adapter to use it from there.
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
        initPhotoRecyclerView()

        // Get search fruits results.
        viewModel.searchPhotos( getString(R.string.fruits) )

        // Observe live data.
        viewModel.photosData.observe(requireActivity(), { response ->
            if ( response.isSuccessful ) {

                if (response.body()!!.totalHits <= 0)
                    binding.txtNotFound.visibility = VISIBLE
                else
                    binding.txtNotFound.visibility = GONE

                binding.progressBar.visibility = GONE
                photoAdapter.setPhotos(response.body()!!.hits)

            } else {

                binding.progressBar.visibility = GONE
                Toast.makeText(requireContext(), "Network Error!", Toast.LENGTH_SHORT).show()

            }
        } )

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
                viewModel.searchPhotos(query)
                searchView.clearFocus()
                return false
            }
        })
    }

    private fun initPhotoRecyclerView(){
        val recyclerView = binding.recyclerPhotos
        recyclerView.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}






















