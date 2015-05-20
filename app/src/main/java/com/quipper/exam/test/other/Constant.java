package com.quipper.exam.test.other;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by jaenelleisidro on 5/20/15.
 */
public class Constant {
    public static final int ONESECONDINMILLISECONDS = 1000;
    public static final int ONEMINUTEINMILLISECONDS = ONESECONDINMILLISECONDS * 60;

    public static final int MINUTESDELAYOFMAPAPI = 30;
    public static final int SLIDERMAP_NUMBEROFMAPS = 5;
    public static final int SLIDERMAP_MINUTESINTERVAL = 60;

    public static final String MAPAPIWEBSITE = "http://www.jma.go.jp";
    public static final String MAPAPIIMAGE = MAPAPIWEBSITE + "/jp/gms/imgs/5/infrared/1/%s00-00.png";

    public static final SimpleDateFormat IMAGE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHH", Locale.US);
    public static final SimpleDateFormat LABEL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:00", Locale.US);
    public static TimeZone jst = TimeZone.getTimeZone("GMT+09:00");

    static {
        TimeZone jst = TimeZone.getTimeZone("GMT+09:00");
        IMAGE_TIME_FORMAT.setTimeZone(jst);
    }
}
