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

public class DetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TaskInterface {

    EditText title;
    Spinner spinner_subLine, spinner_subTime, spinner_subStationName;
    Button btnSave;


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

    List<String> fr_codes = new ArrayList<>();
    List<String> station_names = new ArrayList<>();

    ArrayAdapter<String> subLineAdapter;
    ArrayAdapter<String> subStationAdapter;

    public void setSpinnerAdapter() {

        subLineAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, subwayLine_list);
        spinner_subLine.setAdapter(subLineAdapter);
        spinner_subLine.setOnItemSelectedListener(this);

        subStationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, station_names);
        spinner_subStationName.setAdapter(subStationAdapter);
        spinner_subStationName.setOnItemSelectedListener(this);
    }


    public void updateTime(String line_num, String stationName) {

        if ("".equals(line_num) || "".equals(stationName)) {
            Toast.makeText(getApplicationContext(), "선택사항을 모두 설정해주세요....", Toast.LENGTH_SHORT).show();
        }


    }

    String stationName;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        switch (parent.getId()) {

            case R.id.spinner_subwayLine:

                switch (parent.getSelectedItemPosition()) {
                    case 0:

                        break;

                    case 1:
                        Toast.makeText(view.getContext(), (i) + "호선 선택!", Toast.LENGTH_SHORT).show();
                        fr_codes.clear();
                        station_names.clear();
                        setStationInfoUrl(1, 101, "1");
                        Remote.newTask(this);

                        for (Row row : station_info) {
                            fr_codes.add(row.FR_CODE);
                            station_names.add(row.getSTATION_NM());
                        }
                        subLineAdapter.notifyDataSetChanged();
                        break;
                }

//            case R.id.spinner_station:
//
//                stationName = parent.getSelectedItem().toString();
//                Log.e("Detail", "============== stationName " + stationName);
//                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
