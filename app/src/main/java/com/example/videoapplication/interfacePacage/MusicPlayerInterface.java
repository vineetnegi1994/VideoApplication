package com.example.videoapplication.interfacePacage;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.FileDescriptor;

public interface MusicPlayerInterface {

    public void start();
    public void stop();
    public void pause();
    public void release();

    public void setDataSource(String path);
    public void setDataSource(FileDescriptor fileDescriptor);
    public void setDataSource(AssetFileDescriptor assetFileDescriptor);
    public void setDataSource(Context ctx, Uri uri);

    public void setErrorListener(MediaPlayer.OnErrorListener onErrorListener);
    public void setLooping(boolean loop);
    public boolean isPlaying();
    public void seekTo(int msec);

}
