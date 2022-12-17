package com.example.whatsappclone.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsappclone.Adapters.UserAdapter;
import com.example.whatsappclone.MainActivity;
import com.example.whatsappclone.databinding.FragmentChatsBinding;
import com.example.whatsappclone.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    private FragmentChatsBinding binding;
    private ArrayList<User> list = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private int CHAT_FRAGMENT = 1;

    public ChatsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentChatsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        UserAdapter adapter = new UserAdapter(list, getContext(), CHAT_FRAGMENT);
        binding.chatRecyclerView.setAdapter(adapter);
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        and after that we have to add the database user elements in the list which will be gone
//        i.e. fetch the data from database and then show it to the user

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
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


        return binding.getRoot();

    }

    @SuppressLint("Range")
    public void addInList(User user) {
        ContentResolver contentResolver = getActivity().getContentResolver();
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