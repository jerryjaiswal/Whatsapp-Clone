package com.example.whatsappclone.Adapters;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsappclone.Fragments.CallsFragments;
import com.example.whatsappclone.Fragments.ChatsFragment;
import com.example.whatsappclone.Fragments.StatusFragments;


//this Adapter is used for that scrolling function which happens when we move to chats to status to calls in whatsapp
public class FragmentsAdapter extends FragmentPagerAdapter {


    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new ChatsFragment( );
        else if (position == 1)
            return new StatusFragments();
        else if (position == 2)
            return new CallsFragments();
        else
            return new ChatsFragment( );
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
            title = "Chats";
        else if (position == 1)
            title = "Status";
        else if (position == 2)
            title = "Calls";
        return title;
    }
}
