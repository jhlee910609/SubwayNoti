package com.junhee.android.subwaynoti.domain.StationTime;

/**
 * Created by JunHee on 2017. 7. 7..
 */

public class TimeData {

    private SearchArrivalTimeOfLine2SubwayByIDService SearchArrivalTimeOfLine2SubwayByIDService;

    public SearchArrivalTimeOfLine2SubwayByIDService getSearchArrivalTimeOfLine2SubwayByIDService ()
    {
        return SearchArrivalTimeOfLine2SubwayByIDService;
    }

    public void setSearchArrivalTimeOfLine2SubwayByIDService (SearchArrivalTimeOfLine2SubwayByIDService SearchArrivalTimeOfLine2SubwayByIDService)
    {
        this.SearchArrivalTimeOfLine2SubwayByIDService = SearchArrivalTimeOfLine2SubwayByIDService;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SearchArrivalTimeOfLine2SubwayByIDService = "+SearchArrivalTimeOfLine2SubwayByIDService+"]";
    }
}
