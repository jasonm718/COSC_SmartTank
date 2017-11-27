package cosc4342.smarttank;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    
        
        sensorModel = ViewModelProviders.of(this).get(SensorModel.class);

        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);

        lifecycleRegistry.addObserver(sensorModel);


        final Observer<List<Sensor>> sensorObserver =  new Observer<List<Sensor>>() {
            @Override
            public void onChanged(@Nullable List<Sensor> sensors) {
                setChartData(sensors);
            }
        };

        sensorModel.fetchSensorData().observe(this, sensorObserver);
        
        
        return root;
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
    }
    
    
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
