package cosc4342.smarttank;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment implements LifecycleOwner {
    
    ListView sensors_list;
    
    public ListView notification_list;
    
    SensorModel sensorModel;
    
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    
    public static NotificationAdapter notificationAdapter;
    
    
    public static List<Notification> notificationsCached;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.home_view, container, false);
        sensors_list = (ListView) root.findViewById(R.id.sensor_list);
        notification_list = (ListView) root.findViewById(R.id.notification_list);
        
        sensorModel = ViewModelProviders.of(this).get(SensorModel.class);
        lifecycleRegistry.addObserver(sensorModel);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        sensorModel.setSensorView((ListView) root.findViewById(R.id.sensor_list));
        sensorModel.setNotificationView((ListView) root.findViewById(R.id.notification_list));
        if(notificationAdapter == null) {
            notificationAdapter = new NotificationAdapter();
            notification_list.setAdapter(notificationAdapter);
            
            
        } //else {
//            notification_list.setAdapter(notificationAdapter);
//        }
        sensors_list.setAdapter(new SensorAdapter(new ArrayList<>(Arrays.asList("pH", "Temperature"))));
        
        
        return root;
    }
    @Override
    public void onStart(){
        super.onStart();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }
    
    
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
    
    
    class SensorHolder{
        TextView panel;
        
        SensorHolder(View row){
            panel = (TextView) row.findViewById(R.id.sensor_panel);
        }
    }
    
    class SensorAdapter extends BaseAdapter{
        List<String> sensors;
        
        SensorAdapter(){}
        
        SensorAdapter(List<String> sensors){
            this.sensors = sensors;
        }
    
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
    
    public static void populateNotificationList(ListView listView){
        listView.invalidate();
        notificationAdapter.notifyDataSetChanged();
        notificationAdapter.notifyDataSetInvalidated();
    }
    
    
    
    class NotificationAdapter extends BaseAdapter{
        
        List<Notification> notifications = new ArrayList<>();
        
        
        NotificationAdapter(){
        }
        
        List<Notification> getNotifications(){
            return notifications;
        }
        
        
        void populateAdapter(List<Notification> notifications){
            this.notifications = notifications;
        }
    
        @Override
        public int getCount() {
            return notifications.size();
        }
    
        @Override
        public Notification getItem(int position) {
            return notifications.get(position);
        }
    
        @Override
        public long getItemId(int id) {
            return id;
        }
    
        @Override
        public View getView(int position, View row, ViewGroup viewGroup) {
            
            if(row == null){
                row = getActivity().getLayoutInflater().inflate(R.layout.notification_row, viewGroup, false);
                ((TextView) row.findViewById(R.id.notification_title)).setText(getItem(position).getSensorTitle());
                ((TextView) row.findViewById(R.id.notification_paragraph)).setText(getItem(position).getSensorValue() + " Date: " + getItem(position).getTimestamp());
            }
            
            return row;
        }
    }
    
}
