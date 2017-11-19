package cosc4342.smarttank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    
    SensorAdapter sensors;
    ListView sensors_list;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.home_view, container, false);
        sensors_list = (ListView) root.findViewById(R.id.sensor_list);
        sensors = new SensorAdapter();
        populate(sensors);
        sensors_list.setAdapter(sensors);
        
        
        return root;
    }
    
    
    public void populate(SensorAdapter list){
        
        List<String> sensors = new ArrayList<>();
        sensors.add("pH");
        sensors.add("Temperature");
        
        list.setSensors(sensors);
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
                
                row = getActivity().getLayoutInflater().inflate(R.layout.sensor_row, viewGroup, false);
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
