package com.example.novarand_sns;

import android.content.Intent;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.novarand_sns.controller.MyCommunitiesFeeds_Adapter;
import com.example.novarand_sns.controller.MyCommunitiesList_Adapter;
import com.example.novarand_sns.model.MyCommunitiesFeeds_Item;
import com.example.novarand_sns.model.MyCommunitiesList_Item;

import java.util.ArrayList;
import java.util.List;

public class Community_Home_List extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;

    RecyclerView recyclerView;

    private MyCommunitiesList_Adapter adapter;
    private List<MyCommunitiesList_Item> postsList;

    private Parcelable recyclerViewState;

    SwipeRefreshLayout swipeRefreshLayout;

    Button toFeeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ss_community_home2);

        // 툴바
        toolbar = findViewById(R.id.community_home_toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("커뮤니티 리스트");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toFeeds = findViewById(R.id.community_toFeeds);

        recyclerView = findViewById(R.id.community_home_recyclerView2);
        swipeRefreshLayout = findViewById(R.id.community_home_refresh);

        loadrecycler();

        // 리사이클러뷰 새로고침 인식
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        loadrecycler();
                        Toast.makeText(getApplicationContext(), "TODO 새로고침", Toast.LENGTH_SHORT).show();
                        /* 업데이트가 끝났음을 알림 */
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        toFeeds.setOnClickListener(v -> {
            Intent mIntent = new Intent(getApplicationContext(), Community_Home_Feeds.class);
            startActivity(mIntent);
            finish();
        });
    }

    // 데이터 http 요청
    private void loadrecycler() {
        // 쓰레드 http 요청 & run 데이터 넣기
        fillList();
    }

    // loadrecycler 에서 요청/응답 받은 데이터 채워넣기
    private void fillList() {
        this.postsList = new ArrayList();

        String 임시프사 = "https://image.shutterstock.com/image-vector/example-sign-paper-origami-speech-260nw-1164503347.jpg";


        for (int i = 0; i < 20; i++) {
            // TODO 시간 계산 → String 으로 넣어주기
            this.postsList.add(new MyCommunitiesList_Item(임시프사, "이름" + i, "아이디" + i, "내용", "", 1, 2, 3, "", i + "h"));

        }

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        this.adapter = new MyCommunitiesList_Adapter(getApplicationContext(), this.postsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.adapter);

        //위치 유지
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

        //위치 유지
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

        recyclerView.addOnScrollListener(onScrollListener);
    }

    // 바닥에 도달했을 때...
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
            if (lastVisibleItemPosition == itemTotalCount) {
                //TODO 바닥 작업
//                progressBar.setVisibility(View.VISIBLE);
//                loadMoreData();
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 액티비티 종료 시, 애니메이션 효과 없애기
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}