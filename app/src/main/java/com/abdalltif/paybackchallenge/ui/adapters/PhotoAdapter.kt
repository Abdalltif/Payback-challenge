package com.abdalltif.paybackchallenge.ui.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.abdalltif.paybackchallenge.R
import com.abdalltif.paybackchallenge.data.models.Photo
import com.abdalltif.paybackchallenge.databinding.PhotoItemBinding
import com.abdalltif.paybackchallenge.ui.fragments.MainFragmentDirections
import com.abdalltif.paybackchallenge.utils.AppDiffUtil

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
        // pass photo object to view.
        holder.binding.mPhoto = photoList[position]

        // set image with coil
        holder.binding.imageView.load(photoList[position].previewURL) {
            placeholder(R.drawable.ic_image_ph)
            crossfade(true)
            transformations(RoundedCornersTransformation(5f))
        }

        // Click listener.
        holder.binding.root.setOnClickListener {
            showDialog(it, position)
        }

        // Init tags recyclerview.
        if (photoList[position].tags.isNotEmpty()) {

            val tagsRecyclerView = holder.binding.recyclerTags

            tagsRecyclerView.apply {
                // Set the adapter horizontally.
                adapter = TagsAdapter(convertStringToList(photoList[position].tags))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    private fun convertStringToList(str: String): List<String> {
        val words = str.split("\\,".toRegex()).map {
            it.trim()
        }
        return words
    }

    fun setPhotos(newList: List<Photo>){
        val diffUtil = AppDiffUtil(photoList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        photoList = newList
        diffResults.dispatchUpdatesTo(this)
    }

    private fun showDialog(it: View, position: Int) {
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

    private fun navigateToDetails(photo: Photo) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment( photo )
        navController.navigate(action)
    }
}