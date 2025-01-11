package dev.forcecodes.auth.data

import dev.forcecodes.auth.domain.PromptRepository
import javax.inject.Inject

class DefaultPromptRepository @Inject constructor(): PromptRepository {

    private val prompts = listOf(
        "Hi, can I ask for your customer number?",
        "How can I help you?"
    )

    override fun getPrompt(index: Int): String {
        return prompts[index]
    }
}
