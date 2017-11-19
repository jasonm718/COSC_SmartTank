package cosc4342.smarttank;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {
    
    //get notifications from a certain time in the back
    @Query("Select * from Notifications where timestamp > :date")
    List<Notification> fetchNotificationsByTimestamp(String date);
    
    
    @Query("Select * from Notifications where sensorTitle = :title")
    List<Notification> fetchNotificationsBySensorTitle(String title);
    
    @Query("Select * from Notifications where sensorTitle = :title AND timestamp > :date")
    List<Notification> fetchNotificationsByTitleAndTime(String title, String date);
    
}
