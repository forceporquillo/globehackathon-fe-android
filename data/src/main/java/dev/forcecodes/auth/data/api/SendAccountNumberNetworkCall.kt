package dev.forcecodes.auth.data.api

import javax.inject.Inject

class SendAccountNumberNetworkCall @Inject constructor(
    private val chatPromptUserService: ChatPromptUserService
) {

    fun execute(account: String): Result<String> {
        return runCatching {
           val response = chatPromptUserService.activeTicket(account)
            if (response.isSuccessful) {
                response.message()
            }
            response.raw().body()?.string().toString()
        }
    }
}
