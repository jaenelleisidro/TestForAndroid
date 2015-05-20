package com.quipper.exam.test.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jaenelleisidro on 5/20/15.
 */
public class Map implements Serializable {
    public String imageUrl;
    public String description;
    public Date date;


    @Override
    public boolean equals(Object o) {
        if(o instanceof Map){
            Map map=(Map) o;
            if(map.imageUrl.equals(imageUrl) && map.date.equals(date) && map.description.equals(description)){
                return true;
            }
        }
        return false;
    }
}
