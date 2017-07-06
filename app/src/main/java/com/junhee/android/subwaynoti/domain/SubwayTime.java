package com.junhee.android.subwaynoti.domain;

/**
 * Created by JunHee on 2017. 7. 6..
 */

public class SubwayTime {

    public String title;
    public String time;
    public String subWayLine;

    public SubwayTime(String title, String time, String subWayLine) {

        this.title = title;
        this.time = time;
        this.subWayLine = subWayLine;
    }

    public SubwayTime() {

    }
}
