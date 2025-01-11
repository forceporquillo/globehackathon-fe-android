package dev.forcecodes.auth.data.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val sharedMoshi = Moshi.Builder().build()

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ) {
        Retrofit.Builder()
            .baseUrl("http://localhost:3000")
            .addCallAdapterFactory(okHttpClient.)
            .addConverterFactory(MoshiConverterFactory.create(sharedMoshi))
            .build()
    }

    @Provides
    fun providesOkHttpInterceptor(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

}
