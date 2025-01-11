package dev.forcecodes.auth.demo.presentation.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.forcecodes.auth.domain.ConversationsRepository
import dev.forcecodes.auth.domain.usecase.GetConversationInteractor
import dev.forcecodes.auth.domain.usecase.GetPromptUseCase
import dev.forcecodes.auth.domain.usecase.Message
import dev.forcecodes.auth.domain.usecase.SendInputUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    getConversationInteractor: GetConversationInteractor,
    private val conversationsRepository: ConversationsRepository,
    private val getPromptUseCase: GetPromptUseCase,
    private val sendInputUseCase: SendInputUseCase
) : ViewModel() {

    val conversations: StateFlow<List<Message>> = getConversationInteractor
        .execute()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    fun composeMessage(input: String) {
        viewModelScope.launch {
            sendInputUseCase.execute(input, 0)
        }
    }

    init {
        // initial
        viewModelScope.launch {
            conversationsRepository.addMessage(
                getPromptUseCase.execute(0).input,
                true
            )
        }
    }
}
