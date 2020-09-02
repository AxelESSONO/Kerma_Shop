package com.obiangetfils.kermashop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.CategoryOBJ;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    int layoutID;
    boolean isIconView;
    Context context;
    List<CategoryOBJ> categoriesList;
    clickListener listener;

    public CategoryAdapter(Context context, int layoutID, List<CategoryOBJ> categoriesList, boolean isIconView) {
        this.context = context;
        this.layoutID = layoutID;
        this.isIconView = isIconView;
        this.categoriesList = categoriesList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        final CategoryOBJ categoryDetails = categoriesList.get(position);
        if (isIconView) {
            Glide.with(context)
                    .load(categoriesList.get(position).getImage())
                    .into(holder.category_image);
        } else {
            Glide.with(context)
                    .load(categoriesList.get(position).getImage())
                    .into(holder.category_image);
        }

        holder.category_title.setText(categoryDetails.getName());
        holder.category_products.setText(categoriesList.get(position).getCount() + " " + context.getString(R.string.products));

        holder.category_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setListener(clickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout category_card;
        ImageView category_image;
        TextView category_title, category_products;

        public MyViewHolder(final View itemView) {
            super(itemView);
            category_card = itemView.findViewById(R.id.category_card);
            category_image = itemView.findViewById(R.id.category_image);
            category_title = itemView.findViewById(R.id.category_title);
            category_products = itemView.findViewById(R.id.category_products);
        }
    }

    public interface clickListener {
        void onitemClicked(boolean hasSubCat, int subCatAt);
    }
}
