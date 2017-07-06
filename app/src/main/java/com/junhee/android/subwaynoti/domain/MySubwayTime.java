package com.junhee.android.subwaynoti.domain;

import com.junhee.android.subwaynoti.domain.StationInfo.Row;

/**
 * Created by JunHee on 2017. 7. 6..
 */

public class MySubwayTime {

    public String title;
    public String time;
    public String stationCode;
    public String stationName;
    public String subwayNumb;
    public Row row;
    public boolean isTurnOn = false;

    public MySubwayTime(String title, String subwayNumb, String code, String time, String stationName) {
        this.title = title;
        this.time = time;
        this.stationCode = code;
        this.stationName = stationName;
        this.subwayNumb = subwayNumb;
    }

    public MySubwayTime() {

    }

    public void modifiedFields(String title, String subwayNumb, String code, String time, String stationName){
        this.title = title;
        this.time = time;
        this.stationCode = code;
        this.stationName = stationName;
        this.subwayNumb = subwayNumb;
    }
}
