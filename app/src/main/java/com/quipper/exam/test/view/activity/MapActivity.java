package com.quipper.exam.test.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quipper.exam.test.R;
import com.quipper.exam.test.domain.Map;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;


public class MapActivity extends BaseActivity {
    public static final String KEY_MAP="KEY_MAP";



    @InjectView(R.id.imgParallax)
    protected ImageView imgParallax;
    @InjectView(R.id.tvDetails)
    protected TextView tvDetails;
    @InjectView(R.id.tvPlayAnimatedMap)
    com.rey.material.widget.Button tvPlayAnimatedMap;
    private Map map;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        map = (Map) getIntent().getSerializableExtra(KEY_MAP);
        Picasso.with(this).load(map.imageUrl).into(imgParallax);
        String html="Description : "+map.description +"<br/>"
                    +"Link : <a href=\""+ map.imageUrl+"\">"+map.imageUrl+"</a><br/>";
        tvDetails.setText(Html.fromHtml(html));
        tvPlayAnimatedMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AnimatedMapActivity.start(MapActivity.this);
            }
        });

    }

    public static void start(Context context,Map map){
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(KEY_MAP, map);
        context.startActivity(intent);
    }
}
