package com.newtonkarani98gmail.vydeos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideosAdapter extends RecyclerView.Adapter<VideosViewHolder> {
    JSONArray videosArray; Context context; String title;

    public VideosAdapter(JSONArray jsonArray, String title){
        this.videosArray=jsonArray;
        this.title=title;

    }
    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.videoview,parent,false);
        context=parent.getContext();
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder holder, int position) {
        try {
            JSONObject jsonObject=videosArray.getJSONObject(position);
            holder.game.setText(title);
            holder.title.setText(jsonObject.getString("title"));
            String embed=jsonObject.getString("embed");
            holder.video.getSettings().setJavaScriptEnabled(true);
            holder.video.loadData(embed, "text/html; charset=utf-8", "UTF-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return videosArray.length();
    }
}
