package cosc4342.smarttank;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TemperatureFragment extends android.support.v4.app.Fragment {
    
    LineChart temp_chart;
//    LineChart ph_chart;
    ChartDataUpdate model;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.temperature_view, container, false);
        model = new ChartDataUpdate();
        temp_chart = (LineChart) root.findViewById(R.id.temperature_chart);
//        ph_chart = (LineChart) root.findViewById(R.id.ph_chart);
        getDataFromServer();
        return root;
    }
    
    
    class ChartDataUpdate {
    
        JSONArray sensor_data;
        
        public void setSensorData(JSONArray data) throws JSONException {
            sensor_data = data;
            List<JSONObject> entries = new ArrayList<>();
            
            
            for(int index = 0; index < data.length(); index++) {
                entries.add(data.getJSONObject(index));
            }
            
            updateChart(entries);
        }
        
        public void updateChart(List<JSONObject> entries) throws JSONException{
            List<Entry> temperatureEntries = new ArrayList<>();
//            List<Entry> phEntries = new ArrayList<>();
            List<String> timestamps = new ArrayList<>();
            
            for(int index = 0; index < entries.size(); index++){
                String temp = entries.get(index).getString("Temp");
                String ph = entries.get(index).getString("ph");
//               String[] time = entries.get(index).getString("t_stamp").split("\\w+-\\w+-\\w+", 2);
                timestamps.add(entries.get(index).getString("t_stamp"));
//               System.out.println(time[1]);
                try {
                    temperatureEntries.add(new Entry(index, Float.parseFloat(temp)));
//                    phEntries.add(new Entry(index, Float.parseFloat(ph)));
                }catch(Exception e){ // sanity checks the data
                    System.out.println(e.getMessage());
                }
            }
            
            LineDataSet temperature = new LineDataSet(temperatureEntries, "temperature");
//            LineDataSet ph = new LineDataSet(phEntries, "pH");
            
            temp_chart.setData(new LineData(temperature));
//            ph_chart.setData(new LineData(ph));
            
            temp_chart.getXAxis().setValueFormatter(new DateAxisFormatter(timestamps));
//            ph_chart.getXAxis().setValueFormatter(new DateAxisFormatter(timestamps));
        }
    }
    
    
    public void getDataFromServer(){
        
        SmartTankRestClient.setAsync(true);
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
