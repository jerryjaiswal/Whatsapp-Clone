package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.example.whatsappclone.Adapters.UserAdapter;
import com.example.whatsappclone.databinding.ActivityChatAndGrpCreationBinding;
import com.example.whatsappclone.databinding.ActivityChatsPageBinding;
import com.example.whatsappclone.databinding.FragmentChatsBinding;
import com.example.whatsappclone.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatAndGrpCreation extends AppCompatActivity {

    private ActivityChatAndGrpCreationBinding binding;
    private ArrayList<User> list = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private UserAdapter adapter;
    private final int CHAT_AND_GRP_CREATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatAndGrpCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        adapter = new UserAdapter(list, ChatAndGrpCreation.this, CHAT_AND_GRP_CREATION);
        binding.chatAndGrpRecyclerView.setAdapter(adapter);
        binding.chatAndGrpRecyclerView.setLayoutManager(new LinearLayoutManager(ChatAndGrpCreation.this));


        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                list.add(new User(null, "New group"));
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    user.setUserId(dataSnapshot.getKey());
                    Log.d("data", dataSnapshot.getKey());

                    if (user.getUserId().equals(auth.getUid())) {
                        Log.d("currentUserId", auth.getUid());
                    } else {
                        addInList(user);

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addInList(User user) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        Log.d("NumberofContactRecieved", Integer.toString(cursor.getCount()));
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                if (user.getPhoneNumber().equals(number) || ("+91" + user.getPhoneNumber()).equals(number)) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    user.setUserName(name);
                    list.add(user);
                    return;


                }

            }
        }


    }






}