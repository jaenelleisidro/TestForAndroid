package com.quipper.exam.test.model.businesslayer;

import com.quipper.exam.test.domain.Map;
import com.quipper.exam.test.other.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jaenelleisidro on 5/20/15.
 */
public class MapManager {
    /**
     * Generate a date object from current time minus minutes ago
     * @param minutes
     * @return
     */
    private Date generateDateForMinutesAgo(int minutes) {
        Date dateToShow = new Date(getCurrentMillis() - (minutes * Constant.ONEMINUTEINMILLISECONDS));
        return dateToShow;
    }

    private String generateMapImageUrlFromDate(Date date) {
        String imageUrl = String.format(Constant.MAPAPIIMAGE, Constant.IMAGE_TIME_FORMAT.format(date));
        return imageUrl;
    }

    private String generateMapDescriptionFromDate(Date date) {
        return Constant.LABEL_FORMAT.format(date);
    }

    /**
     * Generate the latest map available on api
     * @return the latest map
     */

    public Map generateLatestMap() {
        return generateMapForMinutesAgo(Constant.MINUTESDELAYOFMAPAPI);
    }


    /**
     * Generate a series of maps starting from the latest
     * @return
     */
    public ArrayList<Map> generateLatestMaps() {
        return generateLatestMaps(Constant.SLIDERMAP_NUMBEROFMAPS, Constant.SLIDERMAP_MINUTESINTERVAL);
    }

    public ArrayList<Map> generateLatestMaps(int numberOfMaps, int minutesInterval) {
        ArrayList<Map> maps = new ArrayList<>();
        for (int index = 0; index < numberOfMaps; index++) {
            Date date = generateDateForMinutesAgo(Constant.MINUTESDELAYOFMAPAPI + (minutesInterval * index));
            maps.add(generateMapFromDate(date));
        }
        return maps;
    }

    /**
     * generate maps based on current time with minutes removed
     * @param minutes minutes to be removed on current time
     * @return
     */
    public Map generateMapForMinutesAgo(int minutes) {
        Map map = generateMapFromDate(generateDateForMinutesAgo(minutes));
        return map;
    }

    public ArrayList<Map> getMaps(int index,int numberOfMaps){
        ArrayList<Map> maps=new ArrayList<>();
        for(int ctr=0;ctr<numberOfMaps;ctr++){
            maps.add(getMap(ctr));
        }
        return maps;
    }

    public Map getMap(int index){
        return generateMapForMinutesAgo(Constant.MINUTESDELAYOFMAPAPI+(Constant.SLIDERMAP_MINUTESINTERVAL*index));
    }

    public Map generateMapFromDate(Date date) {
        Map map = new Map();
        map.date = date;
        map.description = generateMapDescriptionFromDate(map.date);
        map.imageUrl = generateMapImageUrlFromDate(map.date);
        return map;
    }

    private List<Date> generateMapDates(int numberOfmaps, int minutesInterval) {
        List<Date> dates = new ArrayList<Date>();
        for (int index = 0; index < numberOfmaps; index++) {
            Date date = generateDateForMinutesAgo(Constant.MINUTESDELAYOFMAPAPI + (minutesInterval * index));
            dates.add(date);
        }
        return dates;
    }


    public long getCurrentMillis(){
        return System.currentTimeMillis();
    }

}
