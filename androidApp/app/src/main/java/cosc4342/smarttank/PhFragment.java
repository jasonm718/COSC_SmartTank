package cosc4342.smarttank;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
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
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PhFragment extends android.support.v4.app.Fragment implements LifecycleOwner {
    
    static LineChart ph_chart;
    
    SensorModel sensorModel;
    
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.ph_view, container, false);
        ph_chart = (LineChart) root.findViewById(R.id.ph_chart);
        
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    
        sensorModel = ViewModelProviders.of(this).get(SensorModel.class);
        
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
    
    
    public static void setChartData(List<Sensor> sensors) {
        List<Entry> phEntries = new ArrayList<>();
        List<String> timestamps = new ArrayList<>();
        
        for(int index = 0; index < sensors.size(); index++){
            String ph = sensors.get(index).getTemperature();
            String time = sensors.get(index).getTimestamp();
            
            try {
                phEntries.add(new Entry(index, Float.parseFloat(ph)));
                timestamps.add(time);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        LineDataSet ph = new LineDataSet(phEntries, "pH");
        
        ph_chart.setData(new LineData(ph));
        ph_chart.getXAxis().setValueFormatter(new DateAxisFormatter(timestamps));
        
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
    
    
    
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
