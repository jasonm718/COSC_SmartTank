package cosc4342.smarttank;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Notifications")
public class Notification {
    
    private String sensorTitle;
    private String sensorValue;
    
    @PrimaryKey @NonNull
    private String timestamp;
    //either LOW, MEDIUM, HIGH
    private String severity;
    
    
    Notification(){}
    
    Notification(String title, String value, String time, String severityLevel){
        sensorTitle = title;
        sensorValue = value;
        timestamp = time;
        severity = severityLevel;
    }
    
    
    String getSensorTitle(){
        return sensorTitle;
    }
    
    String getSensorValue(){
        return sensorValue;
    }
    
    String getTimestamp(){
        return timestamp;
    }
    
    String getSeverity(){
        return severity;
    }
    
    
    void setSensorTitle(String newTitle){
        sensorTitle = newTitle;
    }
    
    void setSensorValue(String newSensorValue){
        sensorValue = newSensorValue;
    }
    
    void setTimestamp(String date){
        timestamp = date;
    }
    
    void setSeverity(String newLevel){
        severity = newLevel;
    }
    
    
}
