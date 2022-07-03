package com.sdiegas.zkbchallenge.di

import com.sdiegas.zkbchallenge.data.local.RegistrationDataStore
import com.sdiegas.zkbchallenge.domain.usecase.PersistRegistrationData
import com.sdiegas.zkbchallenge.domain.usecase.ValidateBirthday
import com.sdiegas.zkbchallenge.domain.usecase.ValidateEmail
import com.sdiegas.zkbchallenge.domain.usecase.ValidateName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmail =
        ValidateEmail()

    @Provides
    @Singleton
    fun provideValidateNamelUseCase(): ValidateName =
        ValidateName()

    @Provides
    @Singleton
    fun provideValidateBirthDayUseCase(): ValidateBirthday =
        ValidateBirthday()

    @Provides
    @Singleton
    fun providePersistRegistrationData(registrationDataStore: RegistrationDataStore): PersistRegistrationData =
        PersistRegistrationData(registrationDataStore)

}