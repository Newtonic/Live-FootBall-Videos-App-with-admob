package com.newtonkarani98gmail.vydeos;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideosViewHolder extends RecyclerView.ViewHolder {
    TextView game,title;
    WebView video;
    public VideosViewHolder(@NonNull View itemView) {
        super(itemView);
        game=(TextView)itemView.findViewById(R.id.game);
        title=(TextView)itemView.findViewById(R.id.title_a);
        video=(WebView)itemView.findViewById(R.id.video);
    }
}
