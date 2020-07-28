package com.newtonkarani98gmail.vydeos;


import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListAdapter extends BaseAdapter {
    Context context; JSONArray jsonArray; int selected;
    private static LayoutInflater inflater=null;
    List<UnifiedNativeAd> adList= new ArrayList<>();
    SparseIntArray adArray=new SparseIntArray(); View view=null;
    int reference=0;

    public ListAdapter(Context context, JSONArray jsonArray, int selected){
        this.jsonArray=jsonArray;
        this.context=context;
        this.selected=selected;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
            ImageView thumbnail=(ImageView)view.findViewById(R.id.thumbnail);
            TextView gamea=(TextView)view.findViewById(R.id.game_a);
            TextView number=(TextView)view.findViewById(R.id.number);
            JSONObject jsonObject=jsonArray.getJSONObject(position);
            RequestOptions options=new RequestOptions().fitCenter().placeholder(context.getDrawable(R.drawable.image));
            Glide.with(parent.getContext()).setDefaultRequestOptions(options).load(jsonObject.getString("thumbnail")).into(thumbnail);
            gamea.setText(jsonObject.getString("title"));
            number.setText(jsonObject.getJSONArray("videos").length()+" Videos");
        } catch (JSONException e) {
           final UnifiedNativeAdView adView = (UnifiedNativeAdView)inflater.inflate(R.layout.adview, parent,false);
           try {
              UnifiedNativeAd unifiedNativeAd= adList.get(adArray.get(position));
               TextView headline=(TextView) adView.findViewById(R.id.ad_headline);
               headline.setText(unifiedNativeAd.getHeadline());
               adView.setHeadlineView(headline);

               TextView advertiser=(TextView)adView.findViewById(R.id.advertiser);
               advertiser.setText(unifiedNativeAd.getAdvertiser());
               adView.setAdvertiserView(advertiser);

               TextView body=(TextView)adView.findViewById(R.id.ad_content);
               body.setText(unifiedNativeAd.getBody());
               adView.setBodyView(body);

               MediaView mediaView=(MediaView)adView.findViewById(R.id.media_view);
               mediaView.setImageScaleType(ImageView.ScaleType.FIT_XY);
               mediaView.setMediaContent(unifiedNativeAd.getMediaContent());
               adView.setMediaView(mediaView);

               Button call=(Button)adView.findViewById(R.id.call_to_action);
               call.setText(unifiedNativeAd.getCallToAction());
               adView.setCallToActionView(call);

               adView.setNativeAd(unifiedNativeAd);

           }catch (NullPointerException | IndexOutOfBoundsException ex){
              // ex.printStackTrace();
               AdLoader adLoader = new AdLoader.Builder(context, "ca-app-pub-3940256099942544~2247696110")
                       .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                           @Override
                           public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                               adList.add(unifiedNativeAd);
                               adArray.put(position,adList.size()-1);
                               TextView headline=(TextView) adView.findViewById(R.id.ad_headline);
                               headline.setText(unifiedNativeAd.getHeadline());
                               adView.setHeadlineView(headline);

                               TextView advertiser=(TextView)adView.findViewById(R.id.advertiser);
                               advertiser.setText(unifiedNativeAd.getAdvertiser());
                               adView.setAdvertiserView(advertiser);

                               TextView body=(TextView)adView.findViewById(R.id.ad_content);
                               body.setText(unifiedNativeAd.getBody());
                               adView.setBodyView(body);

                               MediaView mediaView=(MediaView)adView.findViewById(R.id.media_view);
                               mediaView.setImageScaleType(ImageView.ScaleType.FIT_XY);
                               mediaView.setMediaContent(unifiedNativeAd.getMediaContent());
                               adView.setMediaView(mediaView);

                               Button call=(Button)adView.findViewById(R.id.call_to_action);
                               call.setText(unifiedNativeAd.getCallToAction());
                               adView.setCallToActionView(call);

                               adView.setNativeAd(unifiedNativeAd);
                           }
                       })
                       .withAdListener(new AdListener() {
                           @Override
                           public void onAdFailedToLoad(int errorCode) {
                               // Handle the failure by logging, altering the UI, and so on.
                           }
                       })
                       .withNativeAdOptions(new NativeAdOptions.Builder()
                               // Methods in the NativeAdOptions.Builder class can be
                               // used here to specify individual options settings.
                               .build()).build();
               adLoader.loadAd(new AdRequest.Builder().build());
           }
            view=adView;
        }
        return view;
    }
}
