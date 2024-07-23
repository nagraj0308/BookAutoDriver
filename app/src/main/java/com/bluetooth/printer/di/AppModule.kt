package com.bluetooth.printer.di

import android.content.Context
import android.content.SharedPreferences
import com.bluetooth.printer.PM
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


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