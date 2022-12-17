package com.example.whatsappclone.Adapters;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.whatsappclone.chatSection.ChatsPage;
import com.example.whatsappclone.R;
import com.example.whatsappclone.model.User;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

//    we have just build the recycler view over here
//    which is used to show the contacts in the chats section

    private ArrayList<User> list;
    private Context context;
    private int ACTIVITY_CODE;
    private boolean isSelected = false;
    private ArrayList<User> selected = new ArrayList<>();


    public UserAdapter(ArrayList<User> list, Context context, int ACTIVITY_CODE) {
        this.list = list;
        this.context = context;
        this.ACTIVITY_CODE = ACTIVITY_CODE;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_sample_source, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);
        holder.userName.setText(user.getUserName());
        if (ACTIVITY_CODE == 2) {


            if (user.getPhoneNumber() == null) {
                holder.lastMessage.setVisibility(View.GONE);
                holder.userName.setHeight(66);
                holder.proflieImg.setImageResource(R.drawable.ic_baseline_group_24);

            } else {
                holder.lastMessage.setText(user.getAbout());
                Picasso.get().load(user.getProfliePic()).placeholder(R.drawable.avatar).into(holder.proflieImg);
            }

        } else {
            holder.lastMessage.setText("temp");
            Picasso.get().load(user.getProfliePic()).placeholder(R.drawable.avatar).into(holder.proflieImg);
        }

        //here we have the select feature and the moving to chat activity feature
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isSelected = true;

                if (selected.contains(list.get(holder.getAdapterPosition()))) {
//                   this is for making the selected item unselect
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    selected.remove(list.get(holder.getAdapterPosition()));
                } else {
//                    here we are selecting the item with the long press
                    holder.itemView.setBackgroundResource(R.color.selectedItemColor);
                    selected.add(list.get(holder.getAdapterPosition()));

                }
                if (selected.size() == 0) {
                    isSelected = false;


                }

                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isSelected) {
                    if (selected.contains(list.get(holder.getAdapterPosition()))) {
                        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                        selected.remove(list.get(holder.getAdapterPosition()));
                    } else {
                        holder.itemView.setBackgroundResource(R.color.selectedItemColor);
                        selected.add(list.get(holder.getAdapterPosition()));
                    }
                    if (selected.size() == 0)
                        isSelected = false;
                } else {

                    Intent intent = new Intent(context, ChatsPage.class);
                    intent.putExtra("userId", user.getUserId());
                    intent.putExtra("userName", user.getUserName());
                    intent.putExtra("profilePic", user.getProfliePic());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView proflieImg;
        TextView userName, lastMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            proflieImg = itemView.findViewById(R.id.profliePic);

            userName = itemView.findViewById(R.id.userName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }
}
