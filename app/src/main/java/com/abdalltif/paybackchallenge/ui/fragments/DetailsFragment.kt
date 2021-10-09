package com.abdalltif.paybackchallenge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.abdalltif.paybackchallenge.R
import com.abdalltif.paybackchallenge.data.models.Photo
import com.abdalltif.paybackchallenge.databinding.FragmentDetailsBinding
import com.abdalltif.paybackchallenge.ui.adapters.TagsAdapter
import com.abdalltif.paybackchallenge.ui.SharedViewModel

class DetailsFragment : Fragment(R.layout.fragment_details) {
    val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    lateinit var photo: Photo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        photo = args.photo

        // Init data binding.
        binding = DataBindingUtil.setContentView<FragmentDetailsBinding>(requireActivity(), R.layout.fragment_details).apply {
            this.mPhoto = photo
            this.lifecycleOwner = requireActivity()
        }

        binding.imageViewDetails.load(photo.largeImageURL) {
            placeholder(R.drawable.ic_image_ph)
            crossfade(true)
        }

        // Convert tags string to list and trim spaces.
        val str = photo.tags
        val words = str.split("\\,".toRegex()).map {
            it.trim()
        }
        // Init recycler view
        val tagsRecyclerView = binding.recyclerTagsDetails
        tagsRecyclerView.apply {
            adapter = TagsAdapter( words )
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        return view
    }
}