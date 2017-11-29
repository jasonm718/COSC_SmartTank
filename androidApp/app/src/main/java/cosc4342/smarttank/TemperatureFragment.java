package cosc4342.smarttank;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class TemperatureFragment extends android.support.v4.app.Fragment implements LifecycleOwner{
    
    static LineChart temp_chart;
    
    SensorModel sensorModel;
    
    LifecycleRegistry lifecycleRegistry;
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.temperature_view, container, false);
        temp_chart = (LineChart) root.findViewById(R.id.temperature_chart);
        
        
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        sensorModel = ViewModelProviders.of(this).get(SensorModel.class);
    
        lifecycleRegistry.addObserver(sensorModel);
//
//
//
//        final Observer<List<Sensor>> sensorObserver =  new Observer<List<Sensor>>() {
//            @Override
//            public void onChanged(@Nullable List<Sensor> sensors) {
//                System.out.println(sensors.toString());
////                setChartData(sensors);
//                if(sensors.isEmpty()){
//                    System.out.println("sensors empty received");
//                }else {
//                    if(sensorModel.dataIsValid(sensors)) {
//                        setChartData(sensors);
//                    } else {
////                        setChartData(sensors);
//                        sensorModel.sendNotification();
//                    }
//                }
//            }
//        };
//
//        sensorModel.all_sensors.observe(this, sensorObserver);
//        lifecycleRegistry.addObserver(sensorModel);
//
//        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        
        
        
        
        return root;
    }
    
    private void populateSensors() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SmartTankRestClient.setAsync(false);
                SmartTankRestClient.get("/sensors", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    
                    }
                
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    
                        try {
                            JSONArray sensor_data = new JSONArray(responseString);
                            List<Sensor> sensors = new ArrayList<Sensor>();
                            for(int index = 0; index < sensor_data.length(); index++) {
                                String temp = sensor_data.getJSONObject(index).getString("Temp");
                                String ph = sensor_data.getJSONObject(index).getString("ph");
                                String time = sensor_data.getJSONObject(index).getString("t_stamp");
                                String id = sensor_data.getJSONObject(index).getString("_id");
                                String serial = sensor_data.getJSONObject(index).getString("serial");
                                sensors.add(new Sensor(temp, ph, time, serial, id));
                            
                            }
                            setChartData(sensors);
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                });
            
            }
        }).start();
    }
    
    @Override
    public void onStart(){
        super.onStart();
        lifecycleRegistry.markState(Lifecycle.State.STARTED);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }
    
    
    
    
    @Override
    public void onResume() {
        super.onResume();
        lifecycleRegistry.markState(Lifecycle.State.RESUMED);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }
    
    
    
    @Override
    public void onDestroy(){
        super.onDestroy();
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }
    
    
    
   
    
    public static void setChartData(List<Sensor> sensors) {
        System.out.println("temp set chart is called");
        List<Entry> temperatureEntries = new ArrayList<>();
        List<String> timestamps = new ArrayList<>();
        
        for(int index = 0; index < sensors.size(); index++){
            String temp = sensors.get(index).getTemperature();
            String time = sensors.get(index).getTimestamp();
            
            try {
                temperatureEntries.add(new Entry(index, Float.parseFloat(temp)));
                timestamps.add(time);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        LineDataSet temperature = new LineDataSet(temperatureEntries, "temperature");
        
        temp_chart.setData(new LineData(temperature));
        temp_chart.getXAxis().setValueFormatter(new DateAxisFormatter(timestamps));
        
        temp_chart.getAxisLeft().setAxisMinimum((float) 65.00);
        temp_chart.getAxisLeft().setAxisMaximum((float) 85.00);
    
        temp_chart.getAxisRight().setAxisMinimum((float) 65.00);
        temp_chart.getAxisRight().setAxisMaximum((float) 85.00);
    }
    
    
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
