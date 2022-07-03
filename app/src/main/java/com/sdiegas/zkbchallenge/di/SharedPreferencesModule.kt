package com.sdiegas.zkbchallenge.di

import android.content.Context
import com.sdiegas.zkbchallenge.data.local.RegistrationDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideRegistrationDataStore(@ApplicationContext appContext: Context): RegistrationDataStore =
        RegistrationDataStore(appContext)

}