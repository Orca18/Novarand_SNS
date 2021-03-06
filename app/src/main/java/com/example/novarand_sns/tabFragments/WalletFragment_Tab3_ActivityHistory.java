package com.example.novarand_sns.tabFragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.novarand_sns.MM_Profile;
import com.example.novarand_sns.MM_Wallet;
import com.example.novarand_sns.R;
import com.example.novarand_sns.controller.Profile_Tab1_Adapter;
import com.example.novarand_sns.model.Fragment_Tab1_Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WalletFragment_Tab3_ActivityHistory extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    View v;
    private RecyclerView myrecyclerview;
    private List<Fragment_Tab1_Item> postsItem;

    String uid;

    public WalletFragment_Tab3_ActivityHistory() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WalletFragment_Tab3_ActivityHistory newInstance(String param1, String param2) {
        WalletFragment_Tab3_ActivityHistory fragment = new WalletFragment_Tab3_ActivityHistory();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uid = ((MM_Wallet)getActivity()).getUid();

        postsItem = new ArrayList<>();
        fillList();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // ????????????
        v = inflater.inflate(R.layout.fragment_profile_tab1, container, false);

//        swipeRefreshLayout = v.findViewById(R.id.refresh_notice);
//
//        swipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        fillList();
//                        /* ??????????????? ???????????? ?????? */
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
        return v;
    }







    private void fillList() {

        // HttpUrlConnection
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String page = "http://146.56.188.188/app/notice_list.php";
                    // URL ?????? ??????
                    URL url = new URL(page);
                    // ?????? ?????? ??????
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    // Post ????????????
//                    String params = "ccode="+code+"&start="+start+"&list="+list;
                    String params = "";
                    // ????????? ?????? ?????????
                    final StringBuilder sb = new StringBuilder();

                    // ????????????
                    if (conn != null) {
                        Log.i("tag", "conn ??????");
                        // ?????? ???????????? ??????
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setConnectTimeout(10000);
                        // POST ????????????
                        conn.setRequestMethod("POST");
                        // ????????? ???????????? ??????
                        conn.getOutputStream().write(params.getBytes("utf-8"));


                        // url??? ?????? ???????????? (200)
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                            // ?????? ??? ???????????? ??????
                            BufferedReader br = new BufferedReader(new InputStreamReader(
                                    conn.getInputStream(), "utf-8"
                            ));
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }
                            // ???????????? ??????
                            br.close();
                        }
                        // ?????? ??????
                        conn.disconnect();
                    }

                    //??????????????? ?????????????????? ??????????????? ?????? ??? ??? ??????
                    // runOnUiThread(?????? ???????????????)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fillList2(sb.toString());

                            Log.i("testt", "testt :" + sb.toString());

                        }
                    });
                } catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            }
        });
        th.start();
    }

    private void fillList2(String memberinfo) {
        JsonParser Parser = new JsonParser();
        JsonObject jsonObj = (JsonObject) Parser.parse(memberinfo);
        JsonArray info = (JsonArray) jsonObj.get("result");

        postsItem = new ArrayList<>();

        for (int i = 0; i < info.size(); i++) {
            JsonObject object = (JsonObject) info.get(i);
            // String imgurl = "http://146.56.188.188/app/images/";
            postsItem.add(new Fragment_Tab1_Item("?????? ?????????"));
        }

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        myrecyclerview = v.findViewById(R.id.fragment_profile_tab1_recyclerview);
        Profile_Tab1_Adapter noticesAdapter = new Profile_Tab1_Adapter(getContext(), postsItem);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(noticesAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        fillList();
    }
}
