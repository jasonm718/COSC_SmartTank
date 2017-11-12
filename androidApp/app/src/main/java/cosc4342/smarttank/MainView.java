package cosc4342.smarttank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainView extends AppCompatActivity {
    
    SensorAdapter sensors;
    ListView sensors_list;
    String api_url = "https://smarttank.herokuapp.com/";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
    
        sensors_list = (ListView) findViewById(R.id.notification_list);
        sensors = new SensorAdapter();
        populate(sensors);
        sensors_list.setAdapter(sensors);
        
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
    
        new Thread(new Runnable() {
            @Override
            public void run() {
    
                String inputLine;
                StringBuilder response = new StringBuilder();
                try {
        
                    URL api = new URL(api_url += "sensors");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) api.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
        
                                if(httpURLConnection.getResponseCode() == 200){
                                    System.out.println("connection successful");
                                }
        
                    //part that listens to server's response
                    BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
        
                    httpURLConnection.disconnect();
        
                } catch (IOException e) {
                    System.out.println("something wrong with the url");
                    System.out.println(e.getMessage());
                }
                
                System.out.println(response.toString());
    
            }
        }).start();
    }
}
