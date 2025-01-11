package dev.forcecodes.auth.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.forcecodes.auth.data.pref.KeyStoreTokenStorage
import dev.forcecodes.auth.data.pref.TokenStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenStorageModule {

    @Binds
    @Singleton
    abstract fun bindsTokenStorage(tokenStorage: KeyStoreTokenStorage): TokenStorage
}
