package com.example.chatapp.adapters;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chatapp.R;
import com.example.chatapp.database.Message;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.view.LayoutInflater;


import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.senderText.setText("User " + message.getSenderId());
        holder.messageText.setText(message.getContent());
        holder.timestampText.setText(String.valueOf(message.getTimestamp()));
        if (message.getImageUri() != null && !message.getImageUri().isEmpty()) {
            holder.messageImage.setVisibility(View.VISIBLE);
            holder.messageImage.setImageURI(Uri.parse(message.getImageUri()));
        } else {
            holder.messageImage.setVisibility(View.GONE);
        }
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderText;
        TextView messageText;
        TextView timestampText;
        ImageView messageImage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.sender_text);
            messageText = itemView.findViewById(R.id.message_text);
            timestampText = itemView.findViewById(R.id.timestamp_text);
            messageImage = itemView.findViewById(R.id.message_image);
        }
    }
}