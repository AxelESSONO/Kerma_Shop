package com.obiangetfils.kermashop.Story;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.obiangetfils.kermashop.Buyer.BuyerHomeActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.StoryAdapter;
import com.obiangetfils.kermashop.models.StoryOBJ;

import java.util.ArrayList;
import java.util.List;

import xute.storyview.StoryModel;

public class StoryHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private TextView toolbarTitle;
    private ImageView homePage;

    List<StoryModel> storyModelList;
    LinearLayoutManager layoutManager;
    StoryAdapter mAdapter;
    DividerItemDecoration mDividerItemDecoration;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_home);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_story);
        homePage = (ImageView) findViewById(R.id.ret);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Kerma Pub");
        homePage.setImageResource(R.drawable.ic_home_24dp);

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoryHomeActivity.this, BuyerHomeActivity.class));
            }
        });

        fetchData();
    }

    private void fetchData() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Story").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> storyKey = new ArrayList<>();
                for (DataSnapshot dataKeyStory : dataSnapshot.getChildren()){
                    storyKey.add(dataKeyStory.getKey());
                    Toast.makeText(StoryHomeActivity.this, dataKeyStory.getKey(), Toast.LENGTH_SHORT).show();
                }

                List<StoryOBJ> storyOBJList = new ArrayList<>();

                for (int i = 0; i < storyKey.size(); i++){

                    final String storyId = dataSnapshot.child(storyKey.get(i)).child("storyId").getValue(String.class);
                    final String storyNameSender = dataSnapshot.child(storyKey.get(i)).child("storyNameSender").getValue(String.class);
                    final String storyDate = dataSnapshot.child(storyKey.get(i)).child("storyDate").getValue(String.class);
                    final String storyTime = dataSnapshot.child(storyKey.get(i)).child("storyTime").getValue(String.class);

                    List<String> imageKeyList = new ArrayList<>();
                    for (DataSnapshot dataImageKey :  dataSnapshot.child(storyKey.get(i)).child("ImagesProducts").getChildren()){
                        imageKeyList.add(dataImageKey.child("StoryLink").getValue(String.class));
                    }
                    StoryOBJ storyOBJ = new StoryOBJ(storyId, storyNameSender, storyDate, storyTime, imageKeyList);
                    storyOBJList.add(storyOBJ);
                }
                initRecyclerView(storyOBJList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void initRecyclerView(List<StoryOBJ> storyOBJ) {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_story);
        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new StoryAdapter(StoryHomeActivity.this, storyOBJ);
        recyclerView.setAdapter(mAdapter);
        mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }
}