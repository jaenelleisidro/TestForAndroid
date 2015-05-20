package com.quipper.exam.test.view.activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.quipper.exam.test.R;
import com.quipper.exam.test.model.businesslayer.MapManager;
import com.quipper.exam.test.other.helper.AndroidUtils;
import com.quipper.exam.test.view.activity.BaseActivity;
import com.quipper.exam.test.view.fragment.AnimatedMapFragment;
import com.quipper.exam.test.view.fragment.CarouselFragment;
import com.quipper.exam.test.view.fragment.MainActivityFragment;
import com.rey.material.widget.SnackBar;

import javax.inject.Inject;

import butterknife.InjectView;


public class MainActivity extends BaseActivity {

    @InjectView(R.id.main_sn)
    SnackBar mSnackBar;
    @Inject
    AndroidUtils androidUtils;
    @Inject
    MapManager mapManager;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public SnackBar getSnackBar(){
        return mSnackBar;
    }

}
