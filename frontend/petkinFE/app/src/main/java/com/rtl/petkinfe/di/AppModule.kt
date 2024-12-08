package com.rtl.petkinfe.di

import android.content.Context
import com.rtl.petkinfe.data.local.TokenDataSource
import com.rtl.petkinfe.data.repository.AuthRepositoryImpl
import com.rtl.petkinfe.domain.repository.AuthRepository
import com.rtl.petkinfe.domain.usecases.AutoLoginUseCase
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
    fun provideTokenDataSource(@ApplicationContext context: Context): TokenDataSource {
        return TokenDataSource.getInstance(context)
    }

    @Provides
    fun provideAutoLoginUseCase(authRepository: AuthRepository): AutoLoginUseCase {
        return AutoLoginUseCase(authRepository)
    }

}
