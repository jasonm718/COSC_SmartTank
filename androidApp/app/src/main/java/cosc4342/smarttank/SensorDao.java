package cosc4342.smarttank;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SensorDao {
    
    
    //fetch all sensorData based on pi-serial number
    @Query("Select * from Sensors where piserial = :serial_number")
    List<Sensor> fetchAllSensorData(String serial_number);
    
    //get temperature data
    @Query("Select * from Sensors where piserial = :serial_number and timestamp > :timestamp")
    List<Sensor> fetchTemperatureByTimestamp(String serial_number, String timestamp);
    
    //get ph data
    @Query("Select * from Sensors where piserial = :serial_number and timestamp > :timestamp")
    List<Sensor> fetchPhByTimestamp(String serial_number, String timestamp);
    
}
