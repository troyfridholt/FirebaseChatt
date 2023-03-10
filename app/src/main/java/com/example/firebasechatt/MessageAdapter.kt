package com.example.firebasechatt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(private val messageList: List<Message>, private val currentUserId: String) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        holder.bind(message, message.userId == currentUserId)
    }

    override fun getItemCount(): Int = messageList.size

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.message_text_view)
        private val timestampTextView: TextView = itemView.findViewById(R.id.timestamp_text_view)

        fun bind(message: Message, isCurrentUser: Boolean) {
            messageTextView.text = message.messageText
            timestampTextView.text = formatTimestamp(message.timestamp)
            messageTextView.setBackgroundResource(if (isCurrentUser) R.drawable.outgoing_message_bg else R.drawable.incoming_message_bg)
        }

        private fun formatTimestamp(timestamp: Long): String {
            val dateFormat = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
            return dateFormat.format(Date(timestamp))
        }
    }
}
