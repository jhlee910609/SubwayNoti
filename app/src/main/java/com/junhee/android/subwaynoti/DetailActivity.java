package com.junhee.android.subwaynoti;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.junhee.android.subwaynoti.domain.MySubwayTime;
import com.junhee.android.subwaynoti.domain.MySubwayTimeList;
import com.junhee.android.subwaynoti.domain.StationInfo.Data;
import com.junhee.android.subwaynoti.domain.StationTime.TimeData;
import com.junhee.android.subwaynoti.domain.StationTime.Row;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TaskInterface, View.OnClickListener {

    EditText title;
    TextView txt_lineNumb, txt_station, txt_time;
    Spinner spinner_subLine, spinner_subTime, spinner_subStationName;
    Button btnSave;

    String currentStationCode = "";
    String currentStationTime = "";
    String currentStationName = "";
    String currentSubwayNumb = "";

    List<Row> subwayTimeInfos = new ArrayList<>();
    List<com.junhee.android.subwaynoti.domain.StationInfo.Row> subwayInfos = new ArrayList<>();
    List<String> subwayLineNumb = new ArrayList<>();

    // TODO === Parsing URL
    String setStationTimeUrl = "";
    String setStationInfoUrl = "";

    // String
    static final String URL_STATIONINFO_FIX = "http://openapi.seoul.go.kr:8088/";
    static final String URL_CERT = "56796c71646c706e38315841504774/";
    static final String URL_SATIONINFO_MID = "Json/SearchSTNBySubwayLineService/";

    // TODO ===== 도착 시간 api
    static final String URL_TIME_PREFIX = "http://openAPI.seoul.go.kr:8088/";
    static final String URL_TIME_MID = "Json/SearchArrivalTimeOfLine2SubwayByIDService/";
    static final String URL_WEEK = "1"; // 평일
    static final String URL_IN = "2"; // 내선

    Intent intent;
    int position = -1;

    MySubwayTime mySubwayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setWidget();
        setSubwayNum();
        setLineNumbAdapter();
        setStationNameAdapter();
        setStationTimeAdapter();

        btnSave.setOnClickListener(this);
        intent = getIntent();

        position = intent.getIntExtra("POSITION", -1);

        if (position > -1) {
            mySubwayTime = MySubwayTimeList.list.get(position);
            title.setText(mySubwayTime.title.toString());
            txt_lineNumb.setText(mySubwayTime.subwayNumb.toString());
            txt_station.setText(mySubwayTime.stationName.toString());
            txt_time.setText(mySubwayTime.time.toString());
        }

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
        txt_time = (TextView) findViewById(R.id.txt_subwayTime);
        txt_station =(TextView) findViewById(R.id.txt_stationName);
        txt_lineNumb = (TextView) findViewById(R.id.txt_subwayLine);
    }

    ArrayAdapter<String> lineNumbAdapter;
    ArrayAdapter<String> stationNameAdapter;
    ArrayAdapter<String> stationTimeAdapter;

    List<String> stationCodes = new ArrayList<>();
    List<String> stationNames = new ArrayList<>();
    List<String> stationTimes = new ArrayList<>();

    public void setLineNumbAdapter() {
        lineNumbAdapter = new ArrayAdapter<>(DetailActivity.this, android.R.layout.simple_dropdown_item_1line, subwayLineNumb);
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
                        currentSubwayNumb = index + "";
                        setStationList(index);
                        break;

                    case 2:
                        currentSubwayNumb = index + "";
                        setStationList(index);
                        break;

                    case 3:
                        currentSubwayNumb = index + "";
                        setStationList(index);
                        break;

                    case 4:
                        currentSubwayNumb = index + "";
                        setStationList(index);
                        break;

                    case 5:
                        currentSubwayNumb = index + "";
                        setStationList(index);
                        break;

                    case 6:
                        currentSubwayNumb = index + "";
                        setStationList(index);
                        break;

                    case 7:
                        currentSubwayNumb = index + "";
                        setStationList(index);
                        break;

                    case 8:
                        currentSubwayNumb = index + "";
                        setStationList(index);
                        break;

                    case 9:
                        currentSubwayNumb = index + "";
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

    public void setTimeList(String currentStationCode) {
        // FR_CODE
        setStationTimeUrl(1, 200, currentStationCode);
        getStationTimeInfos(setStationTimeUrl);
    }


    public void setStationNameAdapter() {
        stationNameAdapter = new ArrayAdapter<>(DetailActivity.this, android.R.layout.simple_dropdown_item_1line, stationNames);
        spinner_subStationName.setAdapter(stationNameAdapter);
        spinner_subStationName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {

                int currentStationIndex = adapterView.getSelectedItemPosition();
                currentStationName = adapterView.getSelectedItem().toString();
                currentStationCode = subwayInfos.get(currentStationIndex).getFR_CODE();
                Log.e("Detail", "this is =================" + currentStationName + ", currentStationCode = " + currentStationCode);
                setTimeList(currentStationCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setStationTimeAdapter() {
        stationTimeAdapter = new ArrayAdapter<>(DetailActivity.this, android.R.layout.simple_dropdown_item_1line, stationTimes);
        spinner_subTime.setAdapter(stationTimeAdapter);
        spinner_subTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentStationTime = adapterView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setStationTimeUrl(int start, int end, String frCode) {
        setStationTimeUrl = URL_TIME_PREFIX + URL_CERT + URL_TIME_MID + start + "/" + end + "/" + "0" + frCode + "/" + URL_WEEK + "/" + URL_IN + "/";

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

        com.junhee.android.subwaynoti.domain.StationInfo.Row[] row = data.getSearchSTNBySubwayLineService().getRow();
        Log.e("Detaill", "Detail ====== " + row.toString());

        subwayInfos.clear();
        stationCodes.clear();
        stationNames.clear();

        for (com.junhee.android.subwaynoti.domain.StationInfo.Row tempRow : row) {
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

    public TimeData timeConvertJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, TimeData.class);
    }

    public void addMySubwayTimeList() {
        if(position > -1) {
            mySubwayTime.modifiedFields(title.getText().toString(), currentSubwayNumb, currentStationCode,  currentStationTime, currentStationName);
        } else {
            MySubwayTimeList.list.add(new MySubwayTime(title.getText().toString(), currentSubwayNumb, currentStationCode, currentStationTime, currentStationName));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                addMySubwayTimeList();
                Log.e("Detail", "MySubwayTimeList size()====== " + MySubwayTimeList.list.size());
                finish();
                break;
        }
    }

    // TODO AsycTask 핵심 ==================
    public void getStationTimeInfos(String url) {

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    result = Remote.getData(params[0]);
                    Log.i("NETWORK", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String jsonString) {

                TimeData timeData = timeConvertJson(jsonString);
                Log.e("Detaill", "Detail ====== " + timeData.toString());

                Row[] row = timeData.getSearchArrivalTimeOfLine2SubwayByIDService().getRow();
                Log.e("Detaill", "Detail ====== " + row.toString());

                stationTimes.clear();
                subwayTimeInfos.clear();

                for (Row tempRow : row) {
                    subwayTimeInfos.add(tempRow);
                    stationTimes.add(tempRow.getARRIVETIME());
                    Log.e("Detail", "================= getARRIVETIME(); = " + tempRow.getARRIVETIME());
                }

                Log.e("Detail", "Adapter Refresh =================");
                lineNumbAdapter.notifyDataSetChanged();
                stationNameAdapter.notifyDataSetChanged();
                stationTimeAdapter.notifyDataSetChanged();
            }
        }.execute(url);
    }
}
