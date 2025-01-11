package dev.forcecodes.auth.data

import dev.forcecodes.auth.data.local.Conversation
import dev.forcecodes.auth.data.local.CustomerDao
import dev.forcecodes.auth.data.local.MessageDao
import dev.forcecodes.auth.data.local.TicketDao
import dev.forcecodes.auth.domain.ConversationsRepository
import dev.forcecodes.auth.domain.TicketDto
import dev.forcecodes.auth.domain.usecase.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataMessageRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val customerDao: CustomerDao,
    private val ticketDao: TicketDao
): ConversationsRepository {

    override suspend fun sendMessage(index: Int, input: String) {
        addMessage(input, index == 0)
    }

    override fun getConversations(): Flow<List<Message>> {
        return messageDao.getMessages().map {
            it.map {
                if (it.isBot) {
                    Message.Bot(input = it.input)
                } else {
                    Message.Prompt(input = it.input)
                }
            }
        }
    }

    override suspend fun accountNumber(accountNumber: String): Result<TicketDto> {
        return withContext(Dispatchers.IO) {
            runCatching {
                customerDao.getCustomerByAccountNumber(accountNumber.toLong())
            }.map {
                ticketDao.getTicket(it.customerId)
            }.map {
                TicketDto(
                    ticketId = it.ticketId,
                    customerId = it.customerId,
                    issueType = it.issueType,
                    resolutionStatus = it.resolutionStatus
                )
            }.onSuccess {
                addMessage(accountNumber, false)
            }
        }
    }

    override suspend fun addMessage(input: String, isBot: Boolean) {
        withContext(Dispatchers.IO) {
            messageDao.addMessage(Conversation(UUID.randomUUID().toString(), input, isBot))
        }
    }
}
