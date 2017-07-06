package com.junhee.android.subwaynoti.domain.StationInfo;

/**
 * Created by JunHee on 2017. 7. 6..
 */

public class Data
{
    public SearchSTNBySubwayLineService SearchSTNBySubwayLineService;

    public SearchSTNBySubwayLineService getSearchSTNBySubwayLineService ()
    {
        return SearchSTNBySubwayLineService;
    }

    public void setSearchSTNBySubwayLineService (SearchSTNBySubwayLineService SearchSTNBySubwayLineService)
    {
        this.SearchSTNBySubwayLineService = SearchSTNBySubwayLineService;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SearchSTNBySubwayLineService = "+SearchSTNBySubwayLineService+"]";
    }
}
