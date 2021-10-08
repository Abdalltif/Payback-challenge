package com.abdalltif.paybackchallenge.utils

import androidx.recyclerview.widget.DiffUtil
import com.abdalltif.paybackchallenge.data.models.Photo

class AppDiffUtil(
    private val oldList: List<Photo>,
    private val newList: List<Photo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].previewURL != newList[newItemPosition].previewURL -> {
                false
            }
            else -> true
        }
    }
}