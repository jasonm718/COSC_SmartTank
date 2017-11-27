package cosc4342.smarttank;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class SensorModel extends AndroidViewModel implements LifecycleObserver {
    
    public MutableLiveData<List<Sensor>> all_sensors;
    
    private AppDatabase database;
    
    public SensorModel(Application application) {
        super(application);
        
        database = AppDatabase.getDB(application.getApplicationContext());
        
    }
    
    
    public MutableLiveData<List<Sensor>> fetchSensorData(){
        if(all_sensors == null) {
            all_sensors = new MutableLiveData<>();
            loadSensors();
        }
        return all_sensors;
        
        
        
    }
    
    
    
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void loadSensors(){
            new AsyncTask<Void, Void, List<Sensor>>() {
            @Override
            protected List<Sensor> doInBackground(Void...voids) {
                return database.sensorModel().fetchAllSensorData();
            }
            
            @Override
            protected void onPostExecute(List<Sensor> listLiveData) {
                all_sensors.postValue(listLiveData);
            }
        }.execute();
    }
    
    
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void updateDatabase(){
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
                            for(int index = 0; index < sensor_data.length(); index++ ){
                                String temp = sensor_data.getJSONObject(index).getString("Temp");
                                String ph = sensor_data.getJSONObject(index).getString("ph");
                                String time = sensor_data.getJSONObject(index).getString("t_stamp");
                                String id = sensor_data.getJSONObject(index).getString("_id");
                                String serial = sensor_data.getJSONObject(index).getString("serial");
                                sensors.add(new Sensor(temp, ph, time, serial, String.valueOf(index)));
                                
//                                database.sensorModel().insertSensor(sensors.get(index));
                                all_sensors.postValue(sensors);
                            }
                        }catch(JSONException e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }
    
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void getSavedData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                all_sensors.postValue(database.sensorModel().fetchAllSensorData());
                System.out.println(all_sensors.getValue());
            }
        }).start();
    }
}
