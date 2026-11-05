package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.database.User;

import java.util.List;

public class ConversationPartnerAdapter extends RecyclerView.Adapter<ConversationPartnerAdapter.PartnerViewHolder> {

    private List<User> partners;
    private OnPartnerClickListener listener;

    public interface OnPartnerClickListener {
        void onPartnerClick(User partner);
    }

    public ConversationPartnerAdapter(List<User> partners, OnPartnerClickListener listener) {
        this.partners = partners;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PartnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        return new PartnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartnerViewHolder holder, int position) {
        User partner = partners.get(position);
        holder.usernameText.setText(partner.getUsername());
        holder.itemView.setOnClickListener(v -> listener.onPartnerClick(partner));
    }

    @Override
    public int getItemCount() {
        return partners.size();
    }

    static class PartnerViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;

        public PartnerViewHolder(View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.partner_username_text);
        }
    }
}