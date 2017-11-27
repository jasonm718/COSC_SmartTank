package cosc4342.smarttank;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Sensors")
public class Sensor {
    
    public String timestamp;
    public String ph;
    public String temperature;
    public String piserial;
    @PrimaryKey
    public String id;
    
    Sensor(){}
    
    Sensor(String temp, String ph, String time, String piserial, String id){
        temperature = temp;
        this.ph = ph;
        timestamp = time;
        this.piserial = piserial;
        this.id = id;
    }
    
    public String getTimestamp(){
        return timestamp;
    }
    
    public String getPh(){
        return ph;
    }
    
    public String getTemperature(){
        return temperature;
    }
}
