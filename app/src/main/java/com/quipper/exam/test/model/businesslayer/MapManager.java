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
     *
     * @param minutes
     * @return
     */
    private Date generateDateForMinutesAgo(int minutes) {
        Date dateToShow = new Date(System.currentTimeMillis() - minutes * Constant.CONSTANT_ONEMINUTEINMILLISECONDS);
        return dateToShow;
    }

    /**
     * generate a valid map image url based on api
     *
     * @param date the date of the map image
     * @return
     */
    private String generateMapImageUrlFromDate(Date date) {
        String imageUrl = String.format(Constant.CONSTANT_MAPAPIIMAGE, Constant.IMAGE_TIME_FORMAT.format(date));
        return imageUrl;
    }

    private String generateMapDescriptionFromDate(Date date) {
        return Constant.LABEL_FORMAT.format(date);
    }

    public Map generateLatestMap() {
        return generateMapForMinutesAgo(Constant.CONSTANT_MINUTESDELAYOFMAPAPI);
    }

    public List<Map> generateLatestMaps() {
        return generateLatestMaps(Constant.SLIDERMAP_NUMBEROFMAPS, Constant.SLIDERMAP_MINUTESINTERVAL);
    }

    public List<Map> generateLatestMaps(int numberOfMaps, int minutesInterval) {
        List<Map> maps = new ArrayList<>();
        for (int index = 0; index < numberOfMaps; index++) {
            Date date = generateDateForMinutesAgo(Constant.CONSTANT_MINUTESDELAYOFMAPAPI + (minutesInterval * index));
            maps.add(generateMapFromDate(date));
        }
        return maps;
    }

    public Map generateMapForMinutesAgo(int minutes) {
        Map map = generateMapFromDate(generateDateForMinutesAgo(minutes));
        return map;
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
            Date date = generateDateForMinutesAgo(Constant.CONSTANT_MINUTESDELAYOFMAPAPI + (minutesInterval * index));
            dates.add(date);
        }
        return dates;
    }


}
