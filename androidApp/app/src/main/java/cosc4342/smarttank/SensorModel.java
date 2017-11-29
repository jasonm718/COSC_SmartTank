package cosc4342.smarttank;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.NOTIFICATION_SERVICE;


public class SensorModel extends AndroidViewModel implements LifecycleObserver {
    
    public MutableLiveData<List<Sensor>> all_sensors = new MutableLiveData<>();
    
    private AppDatabase database;
    private Context context;
    
    private HomeFragment home;
    private ListView sensors;
    private ListView notifications;
    
    public SensorModel(Application application) {
        super(application);
        context = application;
        database = AppDatabase.getDB(application.getApplicationContext());
//        LayoutInflater inflater = (LayoutInflater) application.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        notifications = inflater.inflate(R.layout.home_view, getApplication().getApplicationContext()., false);
    }
    
    public void setHomeFragment(Fragment newHome){
        home = (HomeFragment) newHome;
    }
    
    
//
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void getSavedData(){
//            new AsyncTask<Void, Void, List<Sensor>>() {
//            @Override
//            protected List<Sensor> doInBackground(Void...voids) {
//                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
//                String now = formatter.format(new Date());
//                System.out.println(database.sensorModel().fetchSensorsByTimestamp("2017-11-15 12:00:00"));
//                return database.sensorModel().fetchSensorsByTimestamp(now);
//            }
//
//            @Override
//            protected void onPostExecute(List<Sensor> listLiveData) {
//
//                if(listLiveData == null){
//                    updateDatabase("null");
//                    System.out.println("update database is being called ");
//                }
//                else {
////                    System.out.println(listLiveData);
////                    for(int index = 0; index < listLiveData.size(); index++){
////                        System.out.println(listLiveData.get(index).getTimestamp());
////                    }
//                    all_sensors.setValue(listLiveData);
//                    System.out.println("saved app database is set to the view");
//                }
//
//            }
//        }.execute();
        
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
                                
                                if(index >= 6000){
                                    break;
                                }
                                String temp = sensor_data.getJSONObject(index).getString("Temp");
                                String ph = sensor_data.getJSONObject(index).getString("ph");
                                String time = sensor_data.getJSONObject(index).getString("t_stamp");
                                String id = sensor_data.getJSONObject(index).getString("_id");
                                String serial = sensor_data.getJSONObject(index).getString("serial");
                                sensors.add(new Sensor(temp, ph, time, serial, id));

//                                database.sensorModel().insertSensor(sensors.get(index));
//                            database.sensorModel().insertAllSensors(sensors);
                            }
                            dataIsValid(sensors);
                            if(!sensors.isEmpty()) {
                                setSensorColor(sensors.get(0));
                            }
                            if(TemperatureFragment.temp_chart != null && !sensors.isEmpty()) {
                                TemperatureFragment.setChartData(sensors);
                            }
                            if(PhFragment.ph_chart != null && !sensors.isEmpty()) {
                                PhFragment.setChartData(sensors);
                            }
//                            ViewsPager.sensors = sensors;
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                });
        
            }
        }).start();
    }
    
    private void setSensorColor(Sensor sensor) {
//        if(Integer.parseInt(sensor.getTemperature()) > 80.00){
//
//        }else if(Integer.parseInt(sensor.getTemperature()) < 60.00){
//
//        }
//
//        sensors.setAdapter(new ArrayAdapter<>(Arrays.asList("pH", "Temperature"), Arrays.asList("green")));
//        sensors.invalidate();
//
    }
    
    private void populateNotificationList(List<Notification> newNotifications) {

//        if(HomeFragment.notificationAdapter != null) {
//            home.notificationAdapter.populateAdapter(newNotifications);
//            home.notification_list.setAdapter(home.notificationAdapter);
//        }
        
        if(notifications != null){
            HomeFragment.notificationAdapter.populateAdapter(newNotifications);
            notifications.setAdapter(HomeFragment.notificationAdapter);
            notifications.invalidate();
        }
        
//        HomeFragment.notificationAdapter.populateAdapter(newNotifications);
//        System.out.println(HomeFragment.notificationAdapter.getNotifications());
    }
    
    //    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void updateDatabase(String timestamp){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        
        if(timestamp.equals("null")) {
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

//                                database.sensorModel().insertSensor(sensors.get(index));
//                            database.sensorModel().insertAllSensors(sensors);
                            all_sensors.postValue(sensors);
                            System.out.println("update database with null called");
                        }
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
        else {
            RequestParams params = new RequestParams();
            
            params.put("timestamp", timestamp);
            
            SmartTankRestClient.setAsync(false);
            SmartTankRestClient.post("/sensors", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        
                }
    
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        JSONArray sensor_data = new JSONArray(responseString);
                        List<Sensor> sensors = new ArrayList<Sensor>();
                        for(int index = 0; index < sensor_data.length(); index++){
                            String temp = sensor_data.getJSONObject(index).getString("Temp");
                            String ph = sensor_data.getJSONObject(index).getString("ph");
                            String time = sensor_data.getJSONObject(index).getString("t_stamp");
                            String id = sensor_data.getJSONObject(index).getString("_id");
                            String serial = sensor_data.getJSONObject(index).getString("serial");
                            sensors.add(new Sensor(temp, ph, time, serial, id));
                            System.out.println(sensors.get(index).toString());
                            
//                            all_sensors.getValue().addAll(sensors);
                            System.out.println("update database with timestamp result");
                        }
                        
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
//            }
//        }).start();
    }
    
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void dataControl(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(all_sensors.getValue() != null && all_sensors.getValue().size() > 1){
                    int size = all_sensors.getValue().size();
                    String last_timestamp = all_sensors.getValue().get(size - 1).getTimestamp();
                    updateDatabase(last_timestamp);
                    System.out.println("update database with timestamp");
                }else {
                    getSavedData();
                }
            }
        }).start();
    }
    
    
    
    public void dataIsValid(List<Sensor> sensor_data){
        System.out.println(sensor_data.size());
        List<Notification> notification_data = new ArrayList<>();
        for(int index = 0; index < sensor_data.size(); index++){
            if(index >= 5000){
                break;
            }
            try {
                float ph = Float.parseFloat(sensor_data.get(index).getPh());
                float temperature = Float.parseFloat(sensor_data.get(index).getTemperature());
                String time = sensor_data.get(index).getTimestamp();
                
                if(ph > 8.00){
                    ViewsPager.sendNotification("Ph", String.valueOf(ph), time, context, getApplication().getSystemService(NOTIFICATION_SERVICE));
                    notification_data.add(new Notification("Ph", String.valueOf(ph), time, "LOW"));
                }else if( ph > 9.00){
                    ViewsPager.sendNotification("Ph", String.valueOf(ph), time, context, getApplication().getSystemService(NOTIFICATION_SERVICE));
                }else if(ph > 10.00){
                    ViewsPager.sendNotification("Ph", String.valueOf(ph), time, context, getApplication().getSystemService(NOTIFICATION_SERVICE));
                }
                
                
                if(temperature > 75.00){
                    ViewsPager.sendNotification("Temperature", String.valueOf(temperature), time, context, getApplication().getSystemService(NOTIFICATION_SERVICE));
                    notification_data.add(new Notification("Temperature", String.valueOf(temperature), time, "LOW"));
                }else if(temperature > 80.00){
                    ViewsPager.sendNotification("Temperature", String.valueOf(temperature), time, context, getApplication().getSystemService(NOTIFICATION_SERVICE));
                }
                else if( temperature > 85.00){
                    ViewsPager.sendNotification("Temperature", String.valueOf(temperature), time, context, getApplication().getSystemService(NOTIFICATION_SERVICE));
                }
                
                
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
                        
        }
//        System.out.println(notification_data);
        populateNotificationList(notification_data);
    }
    
    
    public void setSensorView(ListView view) {
        sensors = view;
    }
    
    public void setNotificationView(ListView view){
        System.out.println(view);
        notifications = view;
    }
}
