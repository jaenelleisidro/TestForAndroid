package com.quipper.exam.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public interface Callback {
        void load();
    }

    private Button loadButton;
    private ImageView earthImage;
    private TextView dateText;

    private static final SimpleDateFormat IMAGE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHH", Locale.US);
    private static final SimpleDateFormat LABEL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:00", Locale.US);
    static {
        TimeZone jst = TimeZone.getTimeZone("GMT+09:00");
        IMAGE_TIME_FORMAT.setTimeZone(jst);
    }

    public MainActivityFragment() {
    }
    String imageUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        loadButton = (Button) view.findViewById(R.id.load_button);
        earthImage = (ImageView) view.findViewById(R.id.earth_image);
        dateText = (TextView) view.findViewById(R.id.date_text);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dateToShow = new Date(new Date().getTime() - 30 * 60 * 1000);
                imageUrl = String.format("http://www.jma.go.jp/jp/gms/imgs/5/infrared/1/%s00-00.png",
                        IMAGE_TIME_FORMAT.format(dateToShow));
                loadImage(imageUrl);
                dateText.setText(LABEL_FORMAT.format(dateToShow));
            }
        });

        return view;
    }

    private void loadImage(String imageUrl){
        Picasso.with(getActivity())
                .load(imageUrl)
                .into(earthImage);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey("imageUrl")){
                imageUrl=savedInstanceState.getString("imageUrl");
                loadImage(imageUrl);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imageUrl",imageUrl);
    }
}
