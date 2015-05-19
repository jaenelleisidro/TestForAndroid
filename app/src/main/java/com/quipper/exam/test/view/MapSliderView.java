package com.quipper.exam.test.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.quipper.exam.test.R;

/**
 * Created by jaenelleisidro on 5/19/15.
 */
public class MapSliderView extends BaseSliderView {
        public MapSliderView(Context context) {
            super(context);
        }

        @Override
        public View getView() {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.view_mapslider,null);
            ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
            TextView description = (TextView)v.findViewById(R.id.description);
            description.setText(getDescription());
            bindEventAndShow(v, target);
            return v;
        }
}
