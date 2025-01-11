package dev.forcecodes.auth.demo.presentation.conversation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dev.forcecodes.auth.demo.R
import dev.forcecodes.auth.demo.databinding.FragmentConversationBinding
import dev.forcecodes.auth.domain.usecase.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConversationFragment : Fragment(R.layout.fragment_conversation) {

    private val viewModel by viewModels<ConversationViewModel>()

    private val conversationAdapter = ConversationListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentConversationBinding.bind(view)

        binding.listView.apply {
            adapter = conversationAdapter
            layoutManager = ConversationLayoutManager(requireContext())
        }

        observeOnLifecycleStarted {
            viewModel.conversations.collect {
                conversationAdapter.submitList(
                    listOf(
                        Message.Bot("Globe morning! I'm your AIyyyy bot and here to help.Ccan I ask for your customer number?"),
                        Message.Prompt("Here's my account # 10000004"),
                        Message.Bot("Hi Juan. We apologize for inconvenience but your issue about Billing Query is still pending."),
                        Message.Bot("This will be resolved by January 15, 2024"),
                    ).reversed()
                )
            }
        }

        binding.sendButton.setOnClickListener {
            binding.messageInput.apply {
                viewModel.composeMessage(text.toString())
                text.clear()
            }
        }

       observeOnLifecycleStarted {
           binding.messageInput.textChanges().collect {
               binding.sendButton.isEnabled = it.isNotEmpty()
           }
       }

    }

}

fun Fragment.observeOnLifecycleStarted(
    activeState: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend () -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(activeState) {
            block()
        }
    }
}

fun Fragment.observeOnLifecycleStartedParallel(
    activeState: Lifecycle.State = Lifecycle.State.STARTED,
    block: CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(activeState) {
            block()
        }
    }
}

fun EditText.textChanges(): Flow<String> = callbackFlow {
    val textWatcher = addTextChangedListener { text ->
        trySend(text.toString())
    }
    awaitClose { removeTextChangedListener(textWatcher) }
}

fun EditText.composeMessage(viewModel: ConversationViewModel) {
    viewModel.viewModelScope.launch {
        textChanges()
            .debounce(200)
            .collect {
                viewModel.composeMessage(it)
            }
    }
    setOnFocusChangeListener { v, hasFocus ->
       // viewModel.sendTyping(hasFocus && (v as EditText).text.isNotEmpty())
    }
}
