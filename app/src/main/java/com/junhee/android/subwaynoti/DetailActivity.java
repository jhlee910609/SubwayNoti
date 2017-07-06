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

    String currentStationCodes = "";
    String currentSubLineNumb = "";


    List<Row> subwayInfos = new ArrayList<>();
    List<String> subwayLineNumb = new ArrayList<>();

    // TODO === 라인넘버

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
        setLineNumbAdapter();
        setStationNameAdapter();
        setStationTimeAdapter();
    }

    public void setSubwayNum() {
        subwayLineNumb.add("선택하시오.....!");
        for (int i = 1; i <= 9; i++) {
            subwayLineNumb.add(i + " 호선");
        }
    }

    public void setWidget() {
        title = (EditText) findViewById(R.id.detail_editTitle);
        spinner_subStationName = (Spinner) findViewById(R.id.spinner_station);
        spinner_subLine = (Spinner) findViewById(R.id.spinner_subwayLine);
        spinner_subTime = (Spinner) findViewById(R.id.spinner_subwayTime);
        btnSave = (Button) findViewById(R.id.btnSave);
    }

    ArrayAdapter<String> lineNumbAdapter;
    ArrayAdapter<String> stationNameAdapter;
    ArrayAdapter<String> stationTimeAdapter;

    List<String> stationCodes = new ArrayList<>();
    List<String> stationNames = new ArrayList<>();
    List<String> stationTimes = new ArrayList<>();

    public void setLineNumbAdapter() {
        lineNumbAdapter = new ArrayAdapter<String>(DetailActivity.this, android.R.layout.simple_dropdown_item_1line, subwayLineNumb);
        spinner_subLine.setPrompt("호선을 선택하시오..");
        spinner_subLine.setAdapter(lineNumbAdapter);
        spinner_subLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                switch (adapterView.getSelectedItemPosition()) {

                    case 0:
                        Toast.makeText(getApplicationContext(), "지하철 호선 선택!!", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        setStationList(index);
                        break;

                    case 2:
                        setStationList(index);
                        break;

                    case 3:
                        setStationList(index);
                        break;

                    case 4:
                        setStationList(index);
                        break;

                    case 5:
                        setStationList(index);
                        break;

                    case 6:
                        setStationList(index);
                        break;

                    case 7:
                        setStationList(index);
                        break;

                    case 8:
                        setStationList(index);
                        break;

                    case 9:
                        setStationList(index);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setStationList(int index) {
        setStationInfoUrl(1, 200, index + "");
        Remote.newTask(DetailActivity.this);
    }

    public void setStationNameAdapter() {
        stationNameAdapter = new ArrayAdapter<String>(DetailActivity.this, android.R.layout.simple_dropdown_item_1line, stationNames);
        spinner_subStationName.setAdapter(stationNameAdapter);
        spinner_subStationName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {

                int currentStationIndex = adapterView.getSelectedItemPosition();
                String current_station = adapterView.getSelectedItem().toString();
                currentStationCodes = subwayInfos.get(currentStationIndex).getFR_CODE();
                Log.e("Detail", "this is =================" + current_station + ", currentStationCodes = " + currentStationCodes);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setStationTimeAdapter() {

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

        subwayInfos.clear();
        stationCodes.clear();
        stationNames.clear();

        for (Row tempRow : row) {
            subwayInfos.add(tempRow);
            stationCodes.add(tempRow.getFR_CODE());
            stationNames.add(tempRow.getSTATION_NM());
            Log.e("Detail", "================= size" + subwayInfos.size());
        }

        Log.e("Detail", "Adapter Refresh =================");
        lineNumbAdapter.notifyDataSetChanged();
        stationNameAdapter.notifyDataSetChanged();
    }

    // json 스트링을 Data 오브젝트로 변환
    public Data convertJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Data.class);
    }

}
