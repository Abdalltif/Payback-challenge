package com.abdalltif.paybackchallenge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.abdalltif.paybackchallenge.R
import com.abdalltif.paybackchallenge.databinding.FragmentDetailsBinding
import com.abdalltif.paybackchallenge.databinding.FragmentMainBinding
import com.abdalltif.paybackchallenge.viewmodel.SharedViewModel

class DetailsFragment : Fragment(R.layout.fragment_details) {
//    val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Init data binding.
        binding = DataBindingUtil.setContentView<FragmentDetailsBinding>(requireActivity(), R.layout.fragment_details).apply {
            this.mviewmodel = viewModel
            this.lifecycleOwner = requireActivity()
        }

        return view
    }
}