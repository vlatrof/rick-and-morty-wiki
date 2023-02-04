package com.vlatrof.rickandmorty.data.di

import com.vlatrof.rickandmorty.data.BuildConfig
import com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit.CharacterApi
import com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit.EpisodeApi
import com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit.LocationApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi =
        retrofit.create(CharacterApi::class.java)

    @Provides
    @Singleton
    fun provideLocationApi(retrofit: Retrofit): LocationApi =
        retrofit.create(LocationApi::class.java)

    @Provides
    @Singleton
    fun provideEpisodeApi(retrofit: Retrofit): EpisodeApi =
        retrofit.create(EpisodeApi::class.java)
}
