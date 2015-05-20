package com.quipper.exam.test.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.quipper.exam.test.R;
import com.quipper.exam.test.domain.Map;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;


public class AnimatedMapActivity extends BaseActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animatedmap);
    }

    public static void start(Context context){
        Intent intent = new Intent(context, AnimatedMapActivity.class);
        context.startActivity(intent);
    }
}
