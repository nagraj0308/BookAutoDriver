package com.rent.house.di

import android.content.Context
import android.content.SharedPreferences
import com.rent.house.BuildConfig
import com.rent.house.PM
import com.rent.house.data.remote.ApiImp
import com.rent.house.data.remote.Service
import com.rent.house.domain.Api
import com.rent.house.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideBVService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }

    @Provides
    @Singleton
    fun provideBVApi(service: Service): Api {
        return ApiImp(service)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PM.PM_FILE, Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun providePM(preferences: SharedPreferences): PM {
        return PM(preferences)
    }

}