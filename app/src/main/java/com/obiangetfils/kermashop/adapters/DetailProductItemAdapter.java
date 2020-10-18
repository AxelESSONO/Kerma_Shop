package com.obiangetfils.kermashop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.ImageProductBis;
import com.obiangetfils.kermashop.models.ImagesProducts;

import java.util.List;

public class DetailProductItemAdapter extends RecyclerView.Adapter<DetailProductItemAdapter.DetailProductItemHolder> {

    private Context mContext;
    private List<ImageProductBis> imageProductBisList;
    private List<ImagesProducts> imagesProducts;
    private OnClickOnItemListener onClickOnItemListener;

    public interface OnClickOnItemListener {
       void OnClickOnItem(boolean isClicked, int position);
    }


    public DetailProductItemAdapter(Context mContext, List<ImageProductBis> imageProductBisList, OnClickOnItemListener onClickOnItemListener) {
        this.mContext = mContext;
        this.imageProductBisList = imageProductBisList;
        this.onClickOnItemListener = onClickOnItemListener;
    }

    @NonNull
    @Override
    public DetailProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.image_details, parent, false);

        return new DetailProductItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailProductItemHolder holder, final int position) {

        imagesProducts = imageProductBisList.get(position).getImagesProducts();

        Glide.with(mContext).load(imagesProducts.get(position).getImageUri()).into(holder.imageItem);


        if (imageProductBisList.get(position).isProductClicked()) {
            holder.relativeImageDetail.setBackground(mContext.getDrawable(R.drawable.selected_item));
        } else {
            holder.relativeImageDetail.setBackground(mContext.getDrawable(R.drawable.normal_background));
        }

        holder.cardviewItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                onClickOnItemListener.OnClickOnItem(true, position);
                holder.relativeImageDetail.setBackground(mContext.getDrawable(R.drawable.selected_item));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageProductBisList.size();
    }

    public class DetailProductItemHolder extends RecyclerView.ViewHolder {

        ImageView imageItem;
        CardView cardviewItem;
        RelativeLayout relativeImageDetail;

        public DetailProductItemHolder(@NonNull View itemView) {
            super(itemView);
            imageItem = itemView.findViewById(R.id.imageItem);
            cardviewItem = itemView.findViewById(R.id.cardviewItem);
            relativeImageDetail = itemView.findViewById(R.id.relativeImageDetail);
        }
    }
}
