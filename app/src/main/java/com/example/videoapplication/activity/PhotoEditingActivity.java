package com.example.videoapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.divyanshu.colorseekbar.ColorSeekBar;
import com.example.videoapplication.R;
import com.example.videoapplication.adapter.ChoosePhotoAdapter;
import com.example.videoapplication.animation_setting.Techniques;
import com.example.videoapplication.animation_setting.YoYo;
import com.example.videoapplication.interfacePacage.MusicPlayerInterface;
import com.example.videoapplication.model.ImageSelectModel;
import com.example.videoapplication.model.PhotoEditionModel;
import com.example.videoapplication.photoediting.OnPhotoEditorListener;
import com.example.videoapplication.photoediting.PhotoEditor;
import com.example.videoapplication.photoediting.PhotoEditorView;
import com.example.videoapplication.photoediting.TextStyleBuilder;
import com.example.videoapplication.photoediting.ViewType;
import com.example.videoapplication.utils.SpacesItemDecoration;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import nl.bravobit.ffmpeg.FFprobe;
import timber.log.Timber;

import static android.view.View.GONE;

public class PhotoEditingActivity extends AppCompatActivity implements OnPhotoEditorListener {

    LinearLayout dynamicphoto, dynamiceffect, optionsLay, durationLay, textLay, deleteLay, durationSettingLay, replaceLay,optionClickLay,animtionLay,backgroundColorLay;
    ArrayList<String> photolist = new ArrayList<>();
    int Photoposition = 0;
    PhotoEditorView displayImg;
    /////////////////Play Music//////////////////////////
    Button clickToPlay;
    public static final int AUDIO_REQUEST=500;
    public Uri mMusicUri;
    public MusicPlayerInterface mMusicPlayer;
    AnimatorSet set;
   // MediaPlayer mediaPlayer;
    MediaPlayer player;
    /////////////////Play Music//////////////////////////
    HorizontalScrollView effectscroll;//
    TextView durationTxt;
    SeekBar durationbar;
    private ImageView share;
    private YoYo.YoYoString rope;
    private ImageView selectImag;
    private LinearLayout textSettingLay;
    ColorSeekBar color_seek_bar,color_seek_bar_bg;
    int color = -16777216;
    LinearLayout textColor, backColor;
    ArrayList<ImageSelectModel> listOfAllImages = new ArrayList<>();
    RelativeLayout optionRel, durationRel, textRel, deleteRel, replaceRel;
    ArrayList<PhotoEditionModel> photoEditionModelsList = new ArrayList<>();
    PhotoEditionModel currentModel;
    private TextView currentDurationTv;
    private PhotoEditor mPhotoEditor;
    LinearLayout textStyleLay;
    TextView serifFonttv,monoFonttv,normalFonttv,sansFonttv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editing);
        displayImg = findViewById(R.id.displayImg);
        dynamicphoto = findViewById(R.id.dyphotolay);
        dynamiceffect = findViewById(R.id.effects);
        optionsLay = findViewById(R.id.optionsLay);
        durationLay = findViewById(R.id.durationLay);
        textLay = findViewById(R.id.textLay);
        deleteLay = findViewById(R.id.deleteLay);
        effectscroll = findViewById(R.id.effectscroll);
        durationSettingLay = findViewById(R.id.durationSettingLay);
        durationTxt = findViewById(R.id.durationTxt);
        durationbar = findViewById(R.id.durationbar);
        share = findViewById(R.id.share);
        replaceLay = findViewById(R.id.replaceLay);
        textSettingLay = findViewById(R.id.textSettingLay);
        color_seek_bar = findViewById(R.id.color_seek_bar);
        textColor = findViewById(R.id.textColor);
        backColor = findViewById(R.id.backColor);
        optionRel = findViewById(R.id.optionRel);
        textRel = findViewById(R.id.textRel);
        durationRel = findViewById(R.id.durationRel);
        deleteRel = findViewById(R.id.deleteRel);
        replaceRel = findViewById(R.id.replaceLRel);
        textStyleLay=findViewById(R.id.textStyleLay);
        serifFonttv=findViewById(R.id.serifFonttv);
        sansFonttv=findViewById(R.id.sansFonttv);
        monoFonttv=findViewById(R.id.monoFonttv);
        normalFonttv=findViewById(R.id.normalFonttv);
        optionClickLay=findViewById(R.id.optionClickLay);
        animtionLay=findViewById(R.id.animtionLay);
        backgroundColorLay=findViewById(R.id.backgroundColorLay);
        color_seek_bar_bg=findViewById(R.id.color_seek_bar_bg);
        //////////////////////////////////////////Music//////////////////////
        clickToPlay = findViewById(R.id.clickForPlay);

        /*if (FFmpeg.getInstance(this).isSupported()) {
            // ffmpeg is supported
            versionFFmpeg();
            //ffmpegTestTaskQuit();
        } else {
            // ffmpeg is not supported
            Timber.e("ffmpeg not supported!");
        }

        if (FFprobe.getInstance(this).isSupported()) {
            // ffprobe is supported
            versionFFprobe();
        } else {
            // ffprobe is not supported
            Timber.e("ffprobe not supported!");
        }*/

        ////////////////////////////////////////////////////////////////////
        color_seek_bar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                color = i;
            }
        });
        color_seek_bar_bg.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                try {
                    //textStyleLay.setVisibility(View.GONE);
                    currentModel.setBackgroundColor(color);
                    photoEditionModelsList.set(Photoposition, currentModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                displayImg.setBackgroundColor(i);
            }
        });
        Intent i;
        i = getIntent();
        photolist = i.getStringArrayListExtra("key");
        mPhotoEditor = new PhotoEditor.Builder(this, displayImg)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

        mPhotoEditor.setOnPhotoEditorListener(PhotoEditingActivity.this);

        final TextStyleBuilder styleBuilder = new TextStyleBuilder();

        optionsLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionRel.setBackground(getResources().getDrawable(R.drawable.circular_gradient));
                durationRel.setBackground(null);
                textRel.setBackground(null);
                deleteRel.setBackground(null);
                replaceRel.setBackground(null);
                durationSettingLay.setVisibility(GONE);
                optionClickLay.setVisibility(View.VISIBLE);
                effectscroll.setVisibility(GONE);
                color_seek_bar_bg.setVisibility(GONE);
                //effect_arrray(0);
            }
        });
        animtionLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClickLay.setVisibility(GONE);
                effectscroll.setVisibility(View.VISIBLE);
                color_seek_bar_bg.setVisibility(GONE);
                effect_arrray(0);
            }
        });
        backgroundColorLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClickLay.setVisibility(GONE);
                effectscroll.setVisibility(GONE);
                color_seek_bar_bg.setVisibility(View.VISIBLE);

            }
        });
        textColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            textStyleLay.setVisibility(View.VISIBLE);
                color_seek_bar.setVisibility(GONE);

            }
        });
        backColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                  /*  */
                    textStyleLay.setVisibility(GONE);
                    styleBuilder.withTextColor(color);
                    mPhotoEditor.setstyle(styleBuilder);
                    color_seek_bar.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                }
                //
            }
        });

        durationLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionRel.setBackground(null);
                durationRel.setBackground(getResources().getDrawable(R.drawable.circular_gradient));
                textRel.setBackground(null);
                deleteRel.setBackground(null);
                replaceRel.setBackground(null);
                durationSettingLay.setVisibility(View.VISIBLE);
                optionClickLay.setVisibility(GONE);
                effectscroll.setVisibility(GONE);
                color_seek_bar_bg.setVisibility(GONE);
                textSettingLay.setVisibility(GONE);
                effect_arrray(0);
            }
        });
        textLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionRel.setBackground(null);
                durationRel.setBackground(null);
                textRel.setBackground(getResources().getDrawable(R.drawable.circular_gradient));
                deleteRel.setBackground(null);
                replaceRel.setBackground(null);
                durationSettingLay.setVisibility(GONE);
                optionClickLay.setVisibility(GONE);
                effectscroll.setVisibility(GONE);
                color_seek_bar.setVisibility(GONE);
                textSettingLay.setVisibility(View.VISIBLE);
                color_seek_bar_bg.setVisibility(GONE);
                effect_arrray(0);
                mPhotoEditor.addText(styleBuilder);
            }
        });
        deleteLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionRel.setBackground(null);
                durationRel.setBackground(null);
                textRel.setBackground(null);
                deleteRel.setBackground(getResources().getDrawable(R.drawable.circular_gradient));
                replaceRel.setBackground(null);
                photoEditionModelsList.remove(Photoposition);
                Photoposition = 0;
                if (photoEditionModelsList.size() == 0) {
                    finish();
                }
                createDynamic();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  startActivity(new Intent(PhotoEditingActivity.this,ShareActivity.class));
            }
        });
        replaceLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionRel.setBackground(null);
                durationRel.setBackground(null);
                textRel.setBackground(null);
                deleteRel.setBackground(null);
                replaceRel.setBackground(getResources().getDrawable(R.drawable.circular_gradient));
                showDialog("0");
            }
        });
        durationbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                durationTxt.setText(String.valueOf(progress));
                try {
                    currentDurationTv.setText(String.valueOf(progress));
                    currentModel.setDuration(progress);
                    photoEditionModelsList.set(Photoposition, currentModel);
                } catch (Exception e) {
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
        normalFonttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        serifFonttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                styleBuilder.withTextFont(Typeface.SERIF);
                mPhotoEditor.setstyle(styleBuilder);


            }
        });
        sansFonttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                styleBuilder.withTextFont(Typeface.SANS_SERIF);
                mPhotoEditor.setstyle(styleBuilder);

            }
        });
        monoFonttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                styleBuilder.withTextFont(Typeface.MONOSPACE);
                mPhotoEditor.setstyle(styleBuilder);

            }
        });
        setEditionData();
        createDynamic();

    }

    private Bitmap convertToBitmap(String imageFile) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile);
            return myBitmap;
    }

    private void startAni(String tech) {
        if (rope != null) {
            rope.stop(true);
        }
        Techniques technique = (Techniques.valueOf(tech));
        rope = YoYo.with(technique)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (rope != null) {
                            rope.stop(true);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.makeText(PhotoEditingActivity.this, "canceled previous animation", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(displayImg);
    }

    private void setEditionData() {
        for (int i = 0; i < photolist.size(); i++) {
            PhotoEditionModel editionModel = new PhotoEditionModel();
            editionModel.setBackgroundColor(color);
            editionModel.setDuration(3);
            editionModel.setImagePath(photolist.get(i));
            photoEditionModelsList.add(editionModel);
        }
    }

    private void createDynamic() {
        dynamicphoto.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < photoEditionModelsList.size(); i++) {
            View myLayout = inflater.inflate(R.layout.edit_photo_row, dynamicphoto, false);
            RelativeLayout border = myLayout.findViewById(R.id.border);
            ImageView addImg = myLayout.findViewById(R.id.add_image);
            ImageView image = myLayout.findViewById(R.id.photoimg);
            final TextView durationTv = myLayout.findViewById(R.id.durationTv);
            durationTv.setText("" + photoEditionModelsList.get(i).getDuration());
            addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog("1");
                }
            });
            Glide.with(this).load("file://" + photoEditionModelsList.get(i).getImagePath())
                    .skipMemoryCache(false)
                    .into(image);

            final int finalI = i;
            if (i == Photoposition) {
                displayImg.getSource().setImageBitmap(convertToBitmap(photoEditionModelsList.get(i).getImagePath()));

                border.setBackground(getResources().getDrawable(R.drawable.rectangle_gradient_without_border));
                durationbar.setProgress(photoEditionModelsList.get(finalI).getDuration());
                displayImg.setBackgroundColor(photoEditionModelsList.get(finalI).getBackgroundColor());
                currentModel = photoEditionModelsList.get(finalI);
                currentDurationTv = durationTv;

            } else {
                border.setBackground(getResources().getDrawable(R.drawable.page_background));
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            clickToPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   showVideoFromImages();
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(PhotoEditingActivity.this,R.animator.fade_in);
                    set.setTarget(displayImg);
                    set.addListener(new AnimatorListenerAdapter(){
                        public void onAnimationEnd(Animator animator){
                            //do animation

                            currentModel = photoEditionModelsList.get(finalI);
                            currentDurationTv = durationTv;
                            Photoposition = finalI;
                            createDynamic();
                            displayImg.getSource().setImageBitmap(convertToBitmap(photoEditionModelsList.get(finalI).getImagePath()));
                            durationbar.setProgress(photoEditionModelsList.get(finalI).getDuration());
                            displayImg.setBackgroundColor(photoEditionModelsList.get(finalI).getBackgroundColor());

                        }
                    });
                    set.start();

                }
            });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentModel = photoEditionModelsList.get(finalI);
                    currentDurationTv = durationTv;
                    Photoposition = finalI;
                    createDynamic();
                    displayImg.getSource().setImageBitmap(convertToBitmap(photoEditionModelsList.get(finalI).getImagePath()));
                    durationbar.setProgress(photoEditionModelsList.get(finalI).getDuration());
                    displayImg.setBackgroundColor(photoEditionModelsList.get(finalI).getBackgroundColor());

                }

            });
            dynamicphoto.addView(myLayout);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void showVideoFromImages(){
        for (int j = 0; j < photoEditionModelsList.size(); j++) {



        }


    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void effect_arrray(int option) {
        switch (option) {
            case 0:
                createMainAnimationDynamic(getResources().getStringArray(R.array.animation_list));
                break;
            case 1:
                createEffectDynamic(getResources().getStringArray(R.array.main_effects));
                break;
            case 2:
                createEffectDynamic(getResources().getStringArray(R.array.Bounce_entrance_effects));
                break;
            case 3:
                createEffectDynamic(getResources().getStringArray(R.array.Bounce_out_effects));
                break;
            case 4:
                createEffectDynamic(getResources().getStringArray(R.array.Fade_in_effects));
                break;
            case 5:
                createEffectDynamic(getResources().getStringArray(R.array.Fade_out_effects));
                break;
            case 6:
                createEffectDynamic(getResources().getStringArray(R.array.Flipper_effects));
                break;
            case 7:
                createEffectDynamic(getResources().getStringArray(R.array.Rotating_In));
                break;
            case 8:
                createEffectDynamic(getResources().getStringArray(R.array.Rotating_Out));
                break;
            case 9:
                createEffectDynamic(getResources().getStringArray(R.array.Sliding_In));
                break;
            case 10:
                createEffectDynamic(getResources().getStringArray(R.array.Sliding_Out));
                break;
            case 11:
                createEffectDynamic(getResources().getStringArray(R.array.Sliding_In_Out));
                break;
            case 12:
                createEffectDynamic(getResources().getStringArray(R.array.Zoom_in));
                break;
            case 13:
                createEffectDynamic(getResources().getStringArray(R.array.Zoom_Out));
                break;
        }

    }

    private void createMainAnimationDynamic(String[] stringArray) {
        dynamiceffect.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < stringArray.length; i++) {
            View myLayout = inflater.inflate(R.layout.effect_row, dynamicphoto, false);
            final LinearLayout border = myLayout.findViewById(R.id.optionLay);
            final TextView effecttxt = myLayout.findViewById(R.id.effecttxt);
            effecttxt.setText(stringArray[i]);
            final int finalI = i;
            border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI ==0){
                        effect_arrray(1);//Attention
                    }
                    if(finalI ==1){
                        effect_arrray(2);//Bouncing en
                    }
                    if(finalI ==2){
                        effect_arrray(3);//Bouncing out
                    }
                    if(finalI ==3){
                        effect_arrray(4);//Fading in
                    }
                    if(finalI ==4){
                        effect_arrray(5);//Fading out
                    }if(finalI ==5){
                        effect_arrray(6);//Flipper
                    }
                    if(finalI ==6){
                        effect_arrray(7);//Rotating in
                    }
                    if(finalI ==7){
                        effect_arrray(8);// Rotating out
                    }
                    if(finalI ==8){
                       // effect_arrray(9);//Rotate in -out
                    }
                    if(finalI ==9){
                        effect_arrray(9);//Sliding in
                    }if(finalI ==10){
                        effect_arrray(10);//Slidingout
                    }
                    if(finalI ==11){
                        effect_arrray(11);//SLiding in out
                    }
                    if(finalI ==12){
                        effect_arrray(12);//Zoom out
                    }
                    if(finalI ==13){
                        effect_arrray(13);//Zoom out
                    }




                }
            });
            dynamiceffect.addView(myLayout);
    }
    }


    private void createEffectDynamic(final String[] some_array) {
        dynamiceffect.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < some_array.length; i++) {
            View myLayout = inflater.inflate(R.layout.effect_row, dynamicphoto, false);
            final LinearLayout border = myLayout.findViewById(R.id.optionLay);
            final TextView effecttxt = myLayout.findViewById(R.id.effecttxt);
            effecttxt.setText(some_array[i].split("\\|")[1]);

            final int finalI = i;
            border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (effecttxt.getText().toString().equalsIgnoreCase(some_array[finalI].split("\\|")[1])) {
                            startAni(some_array[finalI].split("\\|")[0]);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            dynamiceffect.addView(myLayout);
        }
    }

    private void createBouncingEffectDynamic(String[] some_array) {
        dynamiceffect.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < some_array.length; i++) {
            View myLayout = inflater.inflate(R.layout.effect_row, dynamicphoto, false);
            final LinearLayout border = myLayout.findViewById(R.id.optionLay);
            final TextView effecttxt = myLayout.findViewById(R.id.effecttxt);
            effecttxt.setText(some_array[i]);
            border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade in to centre Bounce")) {
                        startAni("BounceIn");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Down to centre Bounce")) {
                        startAni("BounceInDown");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Up to centre Bounce")) {
                        startAni("Bounce");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Left to centre Bounce")) {
                        startAni("BounceInLeft");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Right to centre Bounce")) {
                        startAni("BounceInRight");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Bounce out Down")) {
                        startAni("BounceInDown");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Bounce out Up")) {
                        startAni("BounceInUp");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Bounce out Right")) {
                        startAni("BounceInRight");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Bounce Fade out centre")) {
                        startAni("RubberBand");
                    }


                }
            });

            dynamiceffect.addView(myLayout);
        }
    }

    private void createFadeEffectDynamic(String[] some_array) {
        dynamiceffect.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < some_array.length; i++) {
            View myLayout = inflater.inflate(R.layout.effect_row, dynamicphoto, false);
            final LinearLayout border = myLayout.findViewById(R.id.optionLay);
            final TextView effecttxt = myLayout.findViewById(R.id.effecttxt);
            effecttxt.setText(some_array[i]);
            border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade out - Down (in frame)")) {
                        startAni("FadeInDown");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade out - Down (out of frame)")) {
                        startAni("FadeOutDown");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade out - Left (in frame)")) {
                        startAni("FadeInLeft");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade out - Left (out of frame)")) {
                        startAni("FadeOutLeft");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade out - right (in frame)")) {
                        startAni("FadeInRight");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade out - right (out of frame)")) {
                        startAni("FadeOutRight");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade out - Up (in frame)")) {
                        startAni("FadeInUp");
                    }
                    if (effecttxt.getText().toString().equalsIgnoreCase("Fade out - up (out of frame)")) {
                        startAni("FadeOutUp");
                    }


                }
            });
            dynamiceffect.addView(myLayout);
        }
    }

    public void showDialog(final String s) {
        final Dialog dialog = new Dialog(this);
        listOfAllImages.clear();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_view);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        int spanCount = 4; // 3 columns
        int spacing = 5; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spanCount, spacing, includeEdge));

        recyclerView.setLayoutManager(gridLayoutManager);
        ChoosePhotoAdapter customAdapter = new ChoosePhotoAdapter(this, getAllShownImagesPath(), new ChoosePhotoAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                if (listOfAllImages.get(position).isSelectImage()) {
                    if (s.equals("1")) {
                        PhotoEditionModel photoEditionModel = new PhotoEditionModel();
                        photoEditionModel.setDuration(3);
                        photoEditionModel.setBackgroundColor(-16777216);
                        photoEditionModel.setImagePath(listOfAllImages.get(position).getImageView());
                        photoEditionModelsList.add(Photoposition+1,photoEditionModel);

                    } else {
                        PhotoEditionModel photoEditionModel = photoEditionModelsList.get(Photoposition);
                        photoEditionModel.setImagePath(listOfAllImages.get(position).getImageView());
                        photoEditionModelsList.set(Photoposition, photoEditionModel);
                    }
                }
                createDynamic();
                dialog.dismiss();
            }
        });
        recyclerView.setAdapter(customAdapter);
        ImageView dialogButton = dialog.findViewById(R.id.back);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private ArrayList<ImageSelectModel> getAllShownImagesPath() {
        listOfAllImages = new ArrayList<>();
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = this.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            ImageSelectModel imageSelectModel = new ImageSelectModel();
            absolutePathOfImage = cursor.getString(column_index_data);

            imageSelectModel.setImageView(absolutePathOfImage);

            imageSelectModel.setSelectImage(false);


            listOfAllImages.add(imageSelectModel);
        }

        return listOfAllImages;
    }

    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {

    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {

    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == AUDIO_REQUEST) {


        }
    }

    private void setMusic(Uri uri) {
        mMusicUri=uri;

    }

    private String getAudioPath(Uri uri) {
        String[] data = {MediaStore.Audio.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /*private void versionFFmpeg() {
        FFmpeg.getInstance(this).execute(new String[]{"-version"}, new ExecuteBinaryResponseHandler() {
            @Override
            public void onSuccess(String message) {
                Timber.d(message);
            }

            @Override
            public void onProgress(String message) {
                Timber.d(message);
            }
        });

    }

    private void versionFFprobe() {
        Timber.d("version ffprobe");
        FFprobe.getInstance(this).execute(new String[]{"-version"}, new ExecuteBinaryResponseHandler() {
            @Override
            public void onSuccess(String message) {
                Timber.d(message);
            }

            @Override
            public void onProgress(String message) {
                Timber.d(message);
            }
        });
    }

    private void ffmpegTestTaskQuit() {
        String[] command = {"-i", "input.mp4", "output.mov"};

        final FFtask task = FFmpeg.getInstance(this).execute(command, new ExecuteBinaryResponseHandler() {
            @Override
            public void onStart() {
                Timber.d( "on start");
            }

            @Override
            public void onFinish() {
                Timber.d("on finish");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Timber.d("RESTART RENDERING");
                        ffmpegTestTaskQuit();
                    }
                }, 5000);
            }

            @Override
            public void onSuccess(String message) {
                Timber.d(message);
            }

            @Override
            public void onProgress(String message) {
                Timber.d(message);
            }

            @Override
            public void onFailure(String message) {
                Timber.d(message);
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Timber.d( "STOPPING THE RENDERING!");
                task.sendQuitSignal();
            }
        }, 8000);
    }*/

    ////////////////////////////////////////////////////////////////////
}
