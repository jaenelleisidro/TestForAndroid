package com.quipper.exam.test.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.quipper.exam.test.R;


public class MapListActivity extends BaseActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maplist);
    }

    public static void start(Context context){
        Intent intent = new Intent(context, MapListActivity.class);
        context.startActivity(intent);
    }
}
