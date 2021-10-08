package com.abdalltif.paybackchallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdalltif.paybackchallenge.databinding.TagItemBinding

class TagsAdapter(private var tagList: List<String>) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    // inner class of the view holder
    inner class TagViewHolder(val binding: TagItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(TagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.binding.tagTxt.setText(tagList[position])
    }

    override fun getItemCount(): Int {
        return tagList.size
    }
}