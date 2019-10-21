package com.example.videoapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videoapplication.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private List<String> imageList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView photoimg;

        public MyViewHolder(View view) {
            super(view);
            photoimg = view.findViewById(R.id.photoimg);

        }
    }


    public PhotoAdapter(Context context, List<String> imageList) {
        this.imageList = imageList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load("file://" + imageList.get(position))
                .skipMemoryCache(false)
                .into(holder.photoimg);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
