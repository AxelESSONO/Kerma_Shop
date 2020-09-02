package com.obiangetfils.kermashop.Spinner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obiangetfils.kermashop.models.CategoryOBJ;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private Context mContext;
    private List<CategoryOBJ> categoryOBJArrayList;

    public CategoryListAdapter(Context mContext, List<CategoryOBJ> categoryOBJArrayList) {
        this.mContext = mContext;
        this.categoryOBJArrayList = categoryOBJArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categoryOBJArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
