package dev.forcecodes.auth.domain.usecase

import dev.forcecodes.auth.domain.ConversationsRepository
import dev.forcecodes.auth.domain.PromptRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetConversationInteractor @Inject constructor(
    private val conversationsRepository: ConversationsRepository
) {

    fun execute(): Flow<List<Message>> = conversationsRepository.getConversations()
}

@Singleton
class GetPromptUseCase @Inject constructor(
    private val promptRepository: PromptRepository
) {

    fun execute(promptNumber: Int): Message {
        // simple mock
        return Message.Bot(promptRepository.getPrompt(promptNumber))
    }
}

sealed class Message {

    open val input: String = ""

    data class Bot(override val input: String): Message()
    data class Prompt(override val input: String): Message()
}
