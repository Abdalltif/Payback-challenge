package com.abdalltif.check24.di

import com.abdalltif.check24.datasource.api.PixabayApi
import com.abdalltif.paybackchallenge.repository.MainRepository
import com.abdalltif.paybackchallenge.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePixabayApi(retrofit: Retrofit): PixabayApi {
        return retrofit.create(PixabayApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: PixabayApi): MainRepository {
        return MainRepository(api)
    }

}