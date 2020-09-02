package com.obiangetfils.kermashop.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.obiangetfils.kermashop.R;

import java.util.ArrayList;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.AddProductViewHolder> {

    private Context context;
    private ArrayList<Uri> mArrayUri;
    private LayoutInflater mInflater;

    public AddProductAdapter(Context context, ArrayList<Uri> mArrayUri) {
        this.context = context;
        this.mArrayUri = mArrayUri;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AddProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.category_image, parent, false);
        return new AddProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AddProductViewHolder holder, int position) {

        Glide.with(context).load(mArrayUri.get(position)).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mArrayUri.size();
    }

    public class AddProductViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public AddProductViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img);

        }
    }
}
