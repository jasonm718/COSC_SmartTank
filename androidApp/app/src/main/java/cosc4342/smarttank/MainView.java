package cosc4342.smarttank;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.util.ArrayList;
import java.util.List;
import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;


public class MainView extends AppCompatActivity {
    
    SensorAdapter sensors;
    ListView sensors_list;
    String api_url = "https://smarttank.herokuapp.com/";
    LineChart chart;
//    ChartAdapter chartAdapter;
    ChartDataUpdate model;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        model = new ChartDataUpdate();
        sensors_list = (ListView) findViewById(R.id.notification_list);
        sensors = new SensorAdapter();
        populate(sensors);
        sensors_list.setAdapter(sensors);
        
        chart = (LineChart) findViewById(R.id.chart);
        
        getDataFromServer();
    }
    
    
    public void populate(SensorAdapter list){
        
        List<String> sensors = new ArrayList<>();
        sensors.add("pH");
        sensors.add("Temperature");
        sensors.add("Ammonia");
        sensors.add("Salinity");
        sensors.add("Nitrates");
        sensors.add("Water Level");
        
        list.setSensors(sensors);
    }
    
    
    
    
    class ChartDataUpdate {
        
        JSONArray sensor_data;
        
        public void setSensorData(JSONArray data) throws JSONException{
            sensor_data = data;
            List<JSONObject> entries = new ArrayList<>();
            
           
            for(int index = 0; index < data.length(); index++) {
                entries.add(data.getJSONObject(index));
            }
            
            updateChart(entries);
        }
        
        public void updateChart(List<JSONObject> entries) throws JSONException{
           List<Entry> temperatureEntries = new ArrayList<>();
           List<Entry> phEntries = new ArrayList<>();
           
           for(int index = 0; index < entries.size(); index++) {
               String temp = entries.get(index).getString("Temp");
               String ph = entries.get(index).getString("ph");
               String[] time = entries.get(index).getString("t_stamp").split("\\w+-\\w+-\\w+", 2);
    
               System.out.println(time[1]);
               temperatureEntries.add(new Entry(index, Float.parseFloat(temp)));
               phEntries.add(new Entry(index, Float.parseFloat(ph)));
           }
           
           LineDataSet temperature = new LineDataSet(temperatureEntries, "temperature");
           LineDataSet ph = new LineDataSet(phEntries, "pH");
            
           chart.setData(new LineData(temperature, ph));
        }
    }
    
    
    class SensorHolder{
        TextView panel;
        
        SensorHolder(View row){
            panel = (TextView) row.findViewById(R.id.sensor_panel);
        }
    }
    
    class SensorAdapter extends BaseAdapter{
        List<String> sensors;
    
        public void setSensors(List<String> sensors) {
            this.sensors = sensors;
        }
    
        @Override
        public int getCount() {
            return sensors.size();
        }
    
        @Override
        public Object getItem(int i) {
            return sensors.get(i);
        }
    
        @Override
        public long getItemId(int i) {
            return i;
        }
    
        @Override
        public View getView(int position, View row, ViewGroup viewGroup) {
            SensorHolder holder;
            if(row == null){
                
                row = getLayoutInflater().inflate(R.layout.sensor_row, viewGroup, false);
                holder = new SensorHolder(row);
                row.setTag(holder);
            }else{
                holder = (SensorHolder) row.getTag();
            }
            
            holder.panel.setText(sensors.get(position));
            return row;
        }
        
        
    }
    
    
    
    public void getDataFromServer(){
        
        
        SmartTankRestClient.get("/sensors", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(responseString);
                
            }
    
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString)  {
                
                try {
//                    JSONArray sensor_data = new JSONArray(responseString);
//                    for(int index = 0; index < sensor_data.length(); index++){
//                        System.out.println(sensor_data.getJSONObject(index).toString());
//                    }
                    
                    model.setSensorData(new JSONArray(responseString));
                }catch (JSONException e){
                    System.out.println(e);
                    System.out.println("passing data to JSON array didn't work");
                }
            }
        });
    }
}
