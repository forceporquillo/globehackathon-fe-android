package dev.forcecodes.auth.domain.usecase

import dev.forcecodes.auth.domain.ConversationsRepository
import dev.forcecodes.auth.domain.TicketDto
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendInputUseCase @Inject constructor(
    private val conversationsRepository: ConversationsRepository
) {

    suspend fun execute(message: String, category: Int): Result<TicketDto> {
        delay(1000)
        if (category == 0) {
            conversationsRepository.accountNumber(message)
        }
        return Result.failure(Exception(""))
    }
}
