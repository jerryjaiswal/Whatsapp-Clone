package com.example.whatsappclone.chatSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.whatsappclone.Adapters.ChatAdapter;
import com.example.whatsappclone.MainActivity;
import com.example.whatsappclone.R;
import com.example.whatsappclone.databinding.ActivityChatsPageBinding;
import com.example.whatsappclone.model.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatsPage extends AppCompatActivity {

    private ActivityChatsPageBinding binding;
    private ArrayList<MessageModel> messageModels = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityChatsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();

        String receiverId = getIntent().getStringExtra("userId");
        String name = getIntent().getStringExtra("userName");
        String profliePic = getIntent().getStringExtra("profilePic");

        binding.name.setText(name);
        Picasso.get().load(profliePic).placeholder(R.drawable.avatar).into(binding.profliePic);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatsPage.this, MainActivity.class));
            }
        });

        ChatAdapter adapter = new ChatAdapter(ChatsPage.this, messageModels);
        binding.chatSectionRecyclerView.setAdapter(adapter);
        binding.chatSectionRecyclerView.setLayoutManager(new LinearLayoutManager(this));


//        we do this thing to make the database for chating with two people by doing this we have to build only two key in the chat section in the database
        final String senderRoom = senderId + receiverId;
        final String receiverRoom = receiverId + senderId;
        Log.d("receiverRoom", receiverRoom);

        database.getReference().child("chat").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    messageModels.add(model);

                }
//                Use the notifyDataSetChanged() every time the list is updated. To call it on the
//                UI-Thread, use the runOnUiThread() of Activity. Then, notifyDataSetChanged() will work.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.sendButton.setOnClickListener(new View.OnClickListener() {

            //            here we have set the message in the database
            @Override
            public void onClick(View view) {
                String message = binding.input.getText().toString().trim();
                binding.input.setText("");

                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());
                database.getReference().child("chat").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("senderRoomComplition", "success");
                        database.getReference().child("chat").child(receiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("success", "achieved");
                            }
                        });
                    }
                });

            }


        });


    }
}