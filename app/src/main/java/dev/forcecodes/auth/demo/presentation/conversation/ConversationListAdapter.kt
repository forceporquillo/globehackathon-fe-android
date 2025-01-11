package dev.forcecodes.auth.demo.presentation.conversation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import dev.forcecodes.auth.demo.databinding.ItemMessageViewBotBinding
import dev.forcecodes.auth.demo.databinding.ItemMessageViewUserBinding
import dev.forcecodes.auth.domain.usecase.Message
import java.lang.IllegalStateException

private val DIFF_UTIL_CALLBACK = object: DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.input == newItem.input
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean = oldItem == newItem
}


abstract class ConversationViewHolder(
    binding: ViewBinding
): RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(message: Message)
}

class BotViewHolder(
    private val binding: ItemMessageViewBotBinding
) : ConversationViewHolder(binding) {

    override fun bind(message: Message) {
        binding.message.text = message.input
    }
}

class UserViewHolder(
    private val binding: ItemMessageViewUserBinding
) : ConversationViewHolder(binding) {

    override fun bind(message: Message) {
        binding.textMessage.text = message.input
    }
}

class ConversationListAdapter : ListAdapter<Message, ConversationViewHolder>(DIFF_UTIL_CALLBACK) {

    private var recyclerView: RecyclerView? = null

    init {
        registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                recyclerView?.layoutManager?.scrollToPosition(0)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
       return when (viewType) {
           BOT -> {
               val binding = ItemMessageViewBotBinding.inflate(inflater, parent, false)
               BotViewHolder(binding)
           }
           USER -> {
               val binding = ItemMessageViewUserBinding.inflate(inflater, parent, false)
               UserViewHolder(binding)

           }
           else -> throw IllegalStateException()
       }
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val message = getItem(position)
       message?.let {
           holder.bind(it)
       }
    }

    override fun getItemViewType(position: Int): Int {
        val viewType = getItem(position)
        return if (viewType is Message.Bot) {
            BOT
        } else {
            USER
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    companion object {
        private const val BOT = 0
        private const val USER = 1
    }
}
