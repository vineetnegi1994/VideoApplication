package com.example.videoapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videoapplication.R;
import com.example.videoapplication.model.ImageSelectModel;

import java.util.List;

public class ChoosePhotoAdapter extends RecyclerView.Adapter<ChoosePhotoAdapter.MyViewHolder> {
    private static ClickListener clickListener;
    private final ClickListener listener;
    private List<ImageSelectModel> imageList;
    private Context context;

    public void setOnItemClickListener(ClickListener clickListener) {
        ChoosePhotoAdapter.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public ImageView photoimg;
        public RelativeLayout add;

        public MyViewHolder(View view) {
            super(view);
            photoimg = view.findViewById(R.id.photoimg);
            add=view.findViewById(R.id.addlay);
        }


    }


    public ChoosePhotoAdapter(Context context, List<ImageSelectModel> imageList,ClickListener listener) {
        this.listener=listener;
        this.imageList = imageList;
        this.context=context;
    }

    @Override
    public ChoosePhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_media_row, parent, false);

        return new ChoosePhotoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChoosePhotoAdapter.MyViewHolder holder, final int position) {
        Glide.with(context).load("file://" + imageList.get(position).getImageView())
                .skipMemoryCache(false)
                .into(holder.photoimg);
        if(!imageList.get(position).isSelectImage()) {
            holder.photoimg.setAlpha(1.0F);
            holder.add.setVisibility(View.GONE);
            imageList.get(position).setSelectImage(false);
        }
        else{
            holder.photoimg.setAlpha(0.6F);
            holder.add.setVisibility(View.VISIBLE);
            imageList.get(position).setSelectImage(true);
        }
        holder.photoimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageList.get(position).isSelectImage()) {
                    holder.photoimg.setAlpha(1.0F);
                    holder.add.setVisibility(View.GONE);
                    imageList.get(position).setSelectImage(false);
                }
                else{
                    holder.photoimg.setAlpha(0.6F);
                    holder.add.setVisibility(View.VISIBLE);
                    imageList.get(position).setSelectImage(true);
                }
                listener.onItemClick(position);
            }
        });

        }



    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface ClickListener {
        void onItemClick(int position);
    }
}
