package dev.forcecodes.auth.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.forcecodes.auth.data.DataMessageRepository
import dev.forcecodes.auth.data.DefaultPromptRepository
import dev.forcecodes.auth.domain.ConversationsRepository
import dev.forcecodes.auth.domain.PromptRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsConversationRepository(conversationsRepository: DataMessageRepository): ConversationsRepository

    @Binds
    @Singleton
    abstract fun bindsPromptRepository(promptRepository: DefaultPromptRepository): PromptRepository


}
