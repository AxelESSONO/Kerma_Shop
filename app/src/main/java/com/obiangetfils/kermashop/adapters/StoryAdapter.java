package com.obiangetfils.kermashop.adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.StoryOBJ;

import java.util.ArrayList;
import java.util.List;

import xute.storyview.StoryModel;
import xute.storyview.StoryPlayer;
import xute.storyview.StoryView;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {

    private AppCompatActivity mContext;
    private List<StoryOBJ> storyOBJList;
    private LayoutInflater mInflater;

    public StoryAdapter(AppCompatActivity mContext, List<StoryOBJ> storyOBJList) {
        this.mContext = mContext;
        this.storyOBJList = storyOBJList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.story_item, parent, false);
        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryHolder holder, int position) {

        holder.status.setText(storyOBJList.get(position).getStoryNameSender());
        holder.date.setText(storyOBJList.get(position).getStoryDate());

        // Story View
        holder.storyView.resetStoryVisits(); // reset the storyview
        holder.storyView.setActivityContext((AppCompatActivity) mContext);

        List<StoryModel> modelList = new ArrayList<>();
        for (int i = 0; i < storyOBJList.get(position).getImageUri().size(); i++) {
            StoryModel storyModel = new StoryModel(storyOBJList.get(position).getImageUri().get(i),
                    storyOBJList.get(position).getStoryNameSender(),
                    storyOBJList.get(position).getStoryDate());
            modelList.add(storyModel);
        }
        holder.storyView.setImageUris((ArrayList<StoryModel>) modelList);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotionEvent event = MotionEvent.obtain(1, 1, MotionEvent.ACTION_UP, -5, -5, 1);
                holder.storyView.onTouchEvent(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyOBJList.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder {

        StoryView storyView;
        TextView status, date;
        LinearLayout linearLayout;

        public StoryHolder(@NonNull View itemView) {
            super(itemView);
            storyView = (StoryView) itemView.findViewById(R.id.storyView);
            status = (TextView) itemView.findViewById(R.id.status);
            date = (TextView) itemView.findViewById(R.id.date);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_story);

            StoryPlayer storyPlayer;

        }
    }
}