package com.example.novarand_sns.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.novarand_sns.tabFragments.Fragment_Tab1_AllPosts;
import com.example.novarand_sns.tabFragments.Fragment_Tab2_Replies;
import com.example.novarand_sns.tabFragments.Fragment_Tab3_NFTs;
import com.example.novarand_sns.tabFragments.Fragment_Tab4_Likes;
import com.example.novarand_sns.tabFragments.WalletFragment_Tab1_Activity;
import com.example.novarand_sns.tabFragments.WalletFragment_Tab2_AD;
import com.example.novarand_sns.tabFragments.WalletFragment_Tab3_ActivityHistory;

public class WalletFragmentAdapter extends FragmentStateAdapter {

    String uid;

    public WalletFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String uid) {
        super(fragmentManager, lifecycle);
        this.uid = uid;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            // 2번 탭
            case 1:
                return new WalletFragment_Tab2_AD();
            // 3번 탭
            case 2:
                return new WalletFragment_Tab3_ActivityHistory();

        }
        // 기본 1번 탭
        return new WalletFragment_Tab1_Activity();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}