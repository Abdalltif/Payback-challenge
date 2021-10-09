package com.abdalltif.paybackchallenge.ui.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.abdalltif.paybackchallenge.R
import com.abdalltif.paybackchallenge.data.models.Photo
import com.abdalltif.paybackchallenge.databinding.PhotoItemBinding
import com.abdalltif.paybackchallenge.ui.fragments.MainFragmentDirections
import com.abdalltif.paybackchallenge.utils.AppDiffUtil
import kotlinx.coroutines.NonCancellable.cancel

class PhotoAdapter(
    private val navController: NavController
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    private var photoList = emptyList<Photo>()

    // inner class of the view holder
    inner class PhotoViewHolder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        // set image
        holder.binding.imageView.load(photoList[position].previewURL) {
            placeholder(R.drawable.ic_image_ph)
            crossfade(true)
            transformations(RoundedCornersTransformation(5f))
        }

        // Set username
        holder.binding.textName.setText(photoList[position].user)

        // Click listener.
        holder.binding.root.setOnClickListener {

            val builder = AlertDialog.Builder(it.rootView.context)
            builder.setMessage(it.rootView.context.getString(R.string.see_more))
                .setPositiveButton(it.rootView.context.getString(R.string.ok),
                    DialogInterface.OnClickListener { dialog, id ->
                        navigateToDetails(photoList[position])
                    })
                .setNegativeButton(it.rootView.context.getString(R.string.cancel),
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create().show()

        }

        // Init tags recyclerview.
        if ( !photoList[position].tags.isEmpty() ) {
            val tagsRecyclerView = holder.binding.recyclerTags
            tagsRecyclerView.apply {
                // Convert tags string to list and trim spaces.
                val str = photoList[position].tags
                val words = str.split("\\,".toRegex()).map {
                    it.trim()
                }
                // Set the adapter.
                adapter = TagsAdapter(words)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun setPhotos(newList: List<Photo>){
        val diffUtil = AppDiffUtil(photoList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        photoList = newList
        diffResults.dispatchUpdatesTo(this)
    }

    private fun navigateToDetails(photo: Photo){
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment( photo )
        navController.navigate(action)
    }
}