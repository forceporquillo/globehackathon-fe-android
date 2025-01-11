package dev.forcecodes.auth.domain

import dev.forcecodes.auth.domain.usecase.Message
import kotlinx.coroutines.flow.Flow

interface ConversationsRepository {

   suspend fun sendMessage(index: Int, input: String)

   suspend fun accountNumber(accountNumber: String): Result<TicketDto>

   fun getConversations(): Flow<List<Message>>

    suspend fun addMessage(input: String, isBot: Boolean)
}

data class TicketDto(
    val ticketId: Int,
    val customerId: Int,
    val issueType: String,
    val resolutionStatus: String
)

