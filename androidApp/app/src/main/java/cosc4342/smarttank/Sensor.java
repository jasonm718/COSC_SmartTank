package cosc4342.smarttank;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Sensors")
public class Sensor {
    
    public String timestamp;
    public String ph;
    public String temperature;
    
    @PrimaryKey @NonNull
    public String piserial =  "-1";
    
    
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
