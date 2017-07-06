package com.junhee.android.subwaynoti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.junhee.android.subwaynoti.domain.StationInfo.Row;
import com.junhee.android.subwaynoti.domain.StationInfo.Data;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TaskInterface {

    EditText title;
    Spinner spinner_subLine, spinner_subTime, spinner_subStationName;
    Button btnSave;

    String current_station_codes = "";
    String current_subLineNumb = "";


    List<Row> station_info = new ArrayList<>();
    List<String> subwayLine_list = new ArrayList<>();

    // TODO === 라인넘버
    String line_num = "";

    // TODO === Parsing URL
    String setStationInfoUrl = "";
    // String
    static final String URL_STATIONINFO_FIX = "http://openapi.seoul.go.kr:8088/";
    static final String URL_CERT = "56796c71646c706e38315841504774/";
    static final String URL_SATIONINFO_MID = "Json/SearchSTNBySubwayLineService/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setWidget();
        setSubwayNum();
        setSpinnerAdapter();

    }

    public void setSubwayNum() {
        subwayLine_list.add("선택하시오");
        subwayLine_list.add("1호선");
        subwayLine_list.add("2호선");
    }

    public void setWidget() {
        title = (EditText) findViewById(R.id.detail_editTitle);
        spinner_subStationName = (Spinner) findViewById(R.id.spinner_station);
        spinner_subLine = (Spinner) findViewById(R.id.spinner_subwayLine);
        spinner_subTime = (Spinner) findViewById(R.id.spinner_subwayTime);
        btnSave = (Button) findViewById(R.id.btnSave);
    }

    List<String> stationCodes = new ArrayList<>();
    List<String> stationNames = new ArrayList<>();
    List<String> stationTimes = new ArrayList<>();

    ArrayAdapter<String> lineNumbAdapter;
    ArrayAdapter<String> stationNameAdapter;
    ArrayAdapter<String> stationTimeAdapter;


    public void setLineNumbAdapter() {
        lineNumbAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, subwayLine_list);
        spinner_subLine.setAdapter(lineNumbAdapter);
        spinner_subLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                switch (index) {
                    case 0:
                        break;

                    case 1:
                        current_subLineNumb = 1 + "";
                        stationCodes.clear();
                        stationNames.clear();
                        setStationInfoUrl(1, 101, current_subLineNumb);
                        Remote.newTask(DetailActivity.this);

                        for (Row row : station_info) {
                            stationCodes.add(row.FR_CODE);
                            stationNames.add(row.getSTATION_NM());
                        }
                        lineNumbAdapter.notifyDataSetChanged();
                        stationNameAdapter.notifyDataSetChanged();
                        break;

                    case 2:
                        current_subLineNumb = 2 + "";
                        stationCodes.clear();
                        stationNames.clear();
                        setStationInfoUrl(1, 101, current_subLineNumb);
                        Remote.newTask(DetailActivity.this);

                        for (Row row : station_info) {
                            stationCodes.add(row.FR_CODE);
                            stationNames.add(row.getSTATION_NM());
                        }
                        lineNumbAdapter.notifyDataSetChanged();
                        stationNameAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setStationNameAdapter() {
        stationNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stationNames);
        spinner_subStationName.setAdapter(stationNameAdapter);
        Log.e("Detail", "======================spinner_subStationName");
        spinner_subStationName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                switch (index) {
                    case 0:
                        Toast.makeText(DetailActivity.this, (index + 1) + "선택", Toast.LENGTH_SHORT).show();
                        Log.e("Detail", "========== current_station_codes : " + current_station_codes);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setStationTimeAdapter() {
        stationTimeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stationTimes);
        spinner_subTime.setAdapter(lineNumbAdapter);
        spinner_subTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void setSpinnerAdapter() {
        setLineNumbAdapter();
        setStationNameAdapter();
        setStationTimeAdapter();
    }

    private void setStationInfoUrl(int start, int end, String line_num) {
        setStationInfoUrl = URL_STATIONINFO_FIX + URL_CERT + URL_SATIONINFO_MID + start + "/" + end + "/" + line_num;

    }

    @Override
    public String getUrl() {
        return setStationInfoUrl;
    }

    @Override
    public void postExecute(String jsonString) {

        Data data = convertJson(jsonString);
        Log.e("Detaill", "Detail ====== " + data.toString());

        Row[] row = data.getSearchSTNBySubwayLineService().getRow();
        Log.e("Detaill", "Detail ====== " + row.toString());

        station_info.clear();

        for (Row tempRow : row) {
            station_info.add(tempRow);
            Log.e("Detail", "================= size" + station_info.size());
        }
    }

    // json 스트링을 Data 오브젝트로 변환
    public Data convertJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Data.class);
    }

}
