package com.abdalltif.check24.di

import android.content.Context
import com.abdalltif.check24.datasource.api.PixabayApi
import com.abdalltif.paybackchallenge.data.local.PhotoDao
import com.abdalltif.paybackchallenge.data.local.PhotoDatabase
import com.abdalltif.paybackchallenge.repository.MainRepository
import com.abdalltif.paybackchallenge.repository.MainRoomRepository
import com.abdalltif.paybackchallenge.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun providePhotoDB(@ApplicationContext context: Context): PhotoDatabase {
        return PhotoDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun providePhotoDao(photoDatabase: PhotoDatabase): PhotoDao {
        return photoDatabase.photoDao()
    }

    @Singleton
    @Provides
    fun provideRoomRepository(photoDao: PhotoDao): MainRoomRepository {
        return MainRoomRepository(photoDao)
    }

}