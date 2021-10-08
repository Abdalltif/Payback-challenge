package com.abdalltif.paybackchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdalltif.paybackchallenge.data.models.PixabayResponse
import com.abdalltif.paybackchallenge.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SharedViewModel
@Inject constructor (
    private val repository: MainRepository
) : ViewModel() {

    private val _photosData: MutableLiveData<Response<PixabayResponse>> = MutableLiveData()
    val photosData: LiveData<Response<PixabayResponse>> = _photosData

    fun searchPhotos( q: String ) {
        viewModelScope.launch {
            _photosData.value = repository.searchPhotos(q)
        }
    }

    fun getPhotoById( id: Int ) {
        viewModelScope.launch {
//            pixabayData.value = repository.getPhotoById(id)
        }
    }

}