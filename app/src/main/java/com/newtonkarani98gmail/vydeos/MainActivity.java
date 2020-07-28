package com.newtonkarani98gmail.vydeos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    RecyclerView videosView;
    ListView videosList;
    JSONArray videosArray=new JSONArray();
    ListAdapter adapter;
    int selected=0;
    View previous=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=(LinearLayout)findViewById(R.id.loader);
        videosView=(RecyclerView)findViewById(R.id.videos);
        videosList=(ListView)findViewById(R.id.lista);
     ////initialize ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://www.scorebat.com/video-api/v1/";

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                JSONObject jsonObject=null;

                for (int y=0;y<response.length();y++){
                    if (y==0||(y>4&&y%5==0)){
                        try { jsonObject=new JSONObject("{\"name\":\"Ronaldo\"}");videosArray.put(jsonObject);
                            videosArray.put(response.get(y));
                        } catch (JSONException e) { }
                    } else {
                        try { videosArray.put(response.get(y));
                        } catch (JSONException e) { }
                    }
                }

                 adapter=new ListAdapter(MainActivity.this,videosArray,selected);
                videosList.setAdapter(adapter);
                showVideos(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

        videosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeColor(view,position);
                showVideos(position);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.about){
            Intent intent=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void changeColor(View view, int position){
        selected=position;
        view.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
        if (previous==null){}else {
            previous.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
        }
        previous=view;
    }

    public void showVideos(int position){

        JSONArray videos=new JSONArray();
        String title="";
        try {
            JSONObject jsonObject=videosArray.getJSONObject(position);
            JSONArray games=jsonObject.getJSONArray("videos");
            title=jsonObject.getString("title");
            for (int i=0;i<games.length();i++){
                videos.put(games.get(i));
            }
            VideosAdapter videosAdapter=new VideosAdapter(videos,title);
            videosView.setItemAnimator(new DefaultItemAnimator());
            videosView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            videosView.setAdapter(videosAdapter);
            for (int i=0;i<videos.length();i++){
                videosAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
           // e.printStackTrace();
        }
    }}