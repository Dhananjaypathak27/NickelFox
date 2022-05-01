package com.inxparticle.nickelfox.di

import com.inxparticle.nickelfox.data.NickelFoxApi
import com.inxparticle.nickelfox.main.DefaultMainRepository
import com.inxparticle.nickelfox.main.MainRepository
import com.inxparticle.nickelfox.utils.ApplicationConstant.Companion.BASE_URL
import com.inxparticle.nickelfox.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideCurrencyApi() : NickelFoxApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NickelFoxApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: NickelFoxApi): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object :  DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() =  Dispatchers.IO
        override val default: CoroutineDispatcher
            get() =  Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }



}