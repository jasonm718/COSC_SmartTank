package cosc4342.smarttank;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainView extends AppCompatActivity {
    
    GridView sensorsGrid;
    SensorAdapter sensors;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        
        sensorsGrid = (GridView) findViewById(R.id.sensors_grid);
        sensors = new SensorAdapter();
        populate(sensors);
        sensorsGrid.setAdapter(sensors);
    }
    
    
    public void populate(SensorAdapter list){
        
        List<String> sensors = new ArrayList<>();
        sensors.add("pH");
        sensors.add("Temperature");
        sensors.add("Ammonia");
        sensors.add("Salinity");
        sensors.add("Nitrates");
        
        list.setSensors(sensors);
    }
    
    
    class SensorHolder{
        TextView panel;
        
        SensorHolder(View row){
            panel = (TextView) row.findViewById(R.id.sensor_panel);
        }
    }
    
    private class SensorAdapter extends BaseAdapter{
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
                
                row = getLayoutInflater().inflate(R.layout.sensor, viewGroup, false);
                holder = new SensorHolder(row);
                row.setTag(holder);
            }else{
                holder = (SensorHolder) row.getTag();
            }
            
            holder.panel.setText(sensors.get(position));
            return row;
        }
    }
}
