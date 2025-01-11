package dev.forcecodes.auth.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.forcecodes.auth.data.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "demo.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesTicketDao(database: AppDatabase) = database.ticketDao()

    @Provides
    fun providesCustomerDao(database: AppDatabase) = database.customerDao()

    @Provides
    fun providesFaqDao(database: AppDatabase) = database.faqDao()

    @Provides
    fun providesMessageDao(database: AppDatabase) = database.conversations()
}
