package dev.forcecodes.auth.domain

interface PromptRepository {

    fun getPrompt(index: Int): String
}
