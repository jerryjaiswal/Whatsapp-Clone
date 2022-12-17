package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.R;
import com.example.whatsappclone.model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    private int SENDER_VIEW_HOLDER = 1;
    private int RECEIVER_VIEW_HOLDER = 2;

    private Context context;
    private ArrayList<MessageModel> messageModels;

    public ChatAdapter(Context context, ArrayList<MessageModel> messageModels) {
        this.context = context;
        this.messageModels = messageModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_HOLDER)
            return new SenderViewHolder(LayoutInflater.from(context).inflate(R.layout.sender_sample, parent, false));
        else
            return new ReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.reciever_sample, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);
        if (holder.getClass() == SenderViewHolder.class)
            ((SenderViewHolder) holder).senderText.setText(messageModel.getMessage());
        else
            ((ReceiverViewHolder) holder).receiverText.setText(messageModel.getMessage());

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    @Override
    public int getItemViewType(int position) {

//        this conditoin is used to find weather it is the sender phone or not
        if (messageModels.get(position).getUserID().equals(FirebaseAuth.getInstance().getUid()))
            return SENDER_VIEW_HOLDER;
        else
            return RECEIVER_VIEW_HOLDER;
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderText, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderText = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receiverText, receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverText = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        }
    }
}
