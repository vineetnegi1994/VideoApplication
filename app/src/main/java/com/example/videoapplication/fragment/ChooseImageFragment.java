package com.example.videoapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.videoapplication.R;
import com.example.videoapplication.activity.PhotoEditingActivity;
import com.example.videoapplication.adapter.ChoosePhotoAdapter;
import com.example.videoapplication.adapter.PhotoAdapter;
import com.example.videoapplication.model.ImageSelectModel;
import com.example.videoapplication.utils.SpacesItemDecoration;

import java.util.ArrayList;

public class ChooseImageFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<ImageSelectModel> listOfAllImages = new ArrayList<ImageSelectModel>();
    LinearLayout photolay;
    ArrayList<String> showImageList=new ArrayList<>();
    ChoosePhotoAdapter customAdapter;
    RelativeLayout addLay;
    public ChooseImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_image, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        photolay    =   view.findViewById(R.id.photolay);
        addLay      =   view.findViewById(R.id.addlay);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        int spanCount = 4; // 3 columns
        int spacing = 5; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spanCount, spacing, includeEdge));

        recyclerView.setLayoutManager(gridLayoutManager);
         customAdapter = new ChoosePhotoAdapter(getActivity(), getAllShownImagesPath(), new ChoosePhotoAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                if(listOfAllImages.get(position).isSelectImage()) {
                    showImageList.add(listOfAllImages.get(position).getImageView());
                }
                else {
                    showImageList.remove(listOfAllImages.get(position).getImageView());
                }
                createDyanmicLAyout();
            }
        });
        recyclerView.setAdapter(customAdapter);
        addLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), PhotoEditingActivity.class);
                intent.putStringArrayListExtra("key", showImageList);
                startActivity(intent);
            }
        });
        return view;
    }

    private ArrayList<ImageSelectModel> getAllShownImagesPath() {

        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getActivity().getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            ImageSelectModel imageSelectModel=new ImageSelectModel();
            absolutePathOfImage = cursor.getString(column_index_data);

            imageSelectModel.setImageView(absolutePathOfImage);
            imageSelectModel.setSelectImage(false);
            listOfAllImages.add(imageSelectModel);
        }

        return listOfAllImages;
    }
    public void createDyanmicLAyout() {
        photolay.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (int i=0;i<showImageList.size();i++ ) {
            //create a view to inflate the laout_item (the xml with the textView created before)
            View view = inflater.inflate(R.layout.choose_image_row, photolay, false);
             ImageView image=view.findViewById(R.id.photoimg);

            final RelativeLayout removelay=view.findViewById(R.id.removelay);
            removelay.setTag(showImageList.get(i));
            Glide.with(this).load("file://" + showImageList.get(i))
                    .skipMemoryCache(false)
                    .into(image);
            final int finalI = i;
            removelay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int j=0;j<listOfAllImages.size();j++){
                        if(listOfAllImages.get(j).getImageView().equals(removelay.getTag().toString())){
                            listOfAllImages.get(j).setSelectImage(false);
                            customAdapter.notifyDataSetChanged();
                            showImageList.remove(finalI);
                            createDyanmicLAyout();
                        }
                    }
                }
            });
            //add the view to the main layout
            photolay.addView(view);


        }

    }

}

