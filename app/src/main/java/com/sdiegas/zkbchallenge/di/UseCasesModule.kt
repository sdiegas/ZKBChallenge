package com.sdiegas.zkbchallenge.di

import com.sdiegas.zkbchallenge.domain.use_cases.ValidateBirthday
import com.sdiegas.zkbchallenge.domain.use_cases.ValidateEmail
import com.sdiegas.zkbchallenge.domain.use_cases.ValidateName
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

}