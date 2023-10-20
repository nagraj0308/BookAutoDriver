package com.book.admin.di

import com.book.admin.BuildConfig
import com.book.admin.data.remote.BVApiImp
import com.book.admin.data.remote.BVService
import com.book.admin.domain.BVApi
import com.book.admin.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideBVService(retrofit: Retrofit): BVService {
        return retrofit.create(BVService::class.java)
    }

    @Provides
    @Singleton
    fun provideBVApi(service: BVService): BVApi {
        return BVApiImp(service)
    }

}