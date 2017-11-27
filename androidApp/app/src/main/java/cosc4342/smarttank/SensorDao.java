package cosc4342.smarttank;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SensorDao {
    
    
    //fetch all sensorData based on pi-serial number
    @Query("Select * from Sensors")
    List<Sensor> fetchAllSensorData();
    
    //get temperature data
    @Query("Select * from Sensors where piserial = :serial_number and timestamp > :timestamp")
    LiveData<List<Sensor>> fetchTemperatureByTimestamp(String serial_number, String timestamp);
    
    //get ph data
    @Query("Select * from Sensors where piserial = :serial_number and timestamp > :timestamp")
    LiveData<List<Sensor>> fetchPhByTimestamp(String serial_number, String timestamp);
    
    
    @Insert
    void insertSensor(Sensor sensor);
    
    @Insert
    void insertAllSensors(List<Sensor> sensors);
    
}
