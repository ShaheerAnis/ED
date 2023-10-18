package com.example.enter131212;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SENDER = 1;
    private static final int TYPE_RECEIVER = 2;
    private List<Message> messageList;
    private Context context;
    private String currentUserID;

    public MessageAdapter(List<Message> messageList, Context context, String currentUserID) {
        this.messageList = messageList;
        this.context = context;
        this.currentUserID = currentUserID;
    }
    public void setMessages(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SENDER) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).senderMessage.setText(message.getMessage());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.US);
            ((SenderViewHolder) holder).senderTime.setText(dateFormat.format(message.getTimestamp()));

          //  ((SenderViewHolder) holder).senderTime.setText(String.valueOf(message.getTimestamp()));
        } else if (holder instanceof ReceiverViewHolder) {
            ((ReceiverViewHolder) holder).receiverMessage.setText(message.getMessage());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.US);
        //    holder.messageTime.setText(dateFormat.format(message.getTimestamp()));


            ((ReceiverViewHolder) holder).receiverTime.setText(dateFormat.format(message.getTimestamp()));
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getSenderId().equals(currentUserID)) {
            return TYPE_SENDER;
        } else {
            return TYPE_RECEIVER;
        }
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMessage, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.sender_message_text);
            senderTime = itemView.findViewById(R.id.sender_message_time);
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMessage, receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.receiver_message_text);
            receiverTime = itemView.findViewById(R.id.receiver_message_time);
        }
    }
}