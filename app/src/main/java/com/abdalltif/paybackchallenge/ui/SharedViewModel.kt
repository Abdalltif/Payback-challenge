package com.abdalltif.paybackchallenge.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.abdalltif.paybackchallenge.App
import com.abdalltif.paybackchallenge.data.models.Photo
import com.abdalltif.paybackchallenge.data.models.PixabayResponse
import com.abdalltif.paybackchallenge.repository.MainRepository
import com.abdalltif.paybackchallenge.repository.MainRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SharedViewModel
@Inject constructor (
    app: Application,
    private val repository: MainRepository,
    private val roomRepository: MainRoomRepository
) : AndroidViewModel(app) {

    // API data
    private val _photosData: MutableLiveData<Response<PixabayResponse>> = MutableLiveData()
    val photosData: LiveData<Response<PixabayResponse>> = _photosData

    // Room data
    private val _photosLocalData: MutableLiveData<List<Photo>> = MutableLiveData()
    val photosLocalData: LiveData<List<Photo>> = _photosLocalData

    // search from remote datasource
    fun searchPhotos( query: String ) {
        viewModelScope.launch {
            _photosData.value = repository.searchPhotos(query)
        }
    }

    // Add photo to local database
    fun addPhoto(photo: Photo){
        viewModelScope.launch {
            roomRepository.addLocalPhoto(photo)
        }
    }

    // read photos from local datasource
    fun readAllLocalPhotos() {
        viewModelScope.launch {
            _photosLocalData.value = roomRepository.readAllLocalPhotos()
        }
    }

    // search from local datasource
    fun searchLocalPhotos( query: String ) {
        viewModelScope.launch {
            _photosLocalData.value = roomRepository.searchLocalPhotos(query)
        }
    }

    fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<App>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        return false
    }
}












