package cosc4342.smarttank;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {
    
    //get notifications from a certain time in the back
    @Query("Select * from Notifications where timestamp > :date")
    LiveData<List<Notification>> fetchNotificationsByTimestamp(String date);
    
    
    @Query("Select * from Notifications where sensorTitle = :title")
    LiveData<List<Notification>> fetchNotificationsBySensorTitle(String title);
    
    @Query("Select * from Notifications where sensorTitle = :title AND timestamp > :date")
    LiveData<List<Notification>> fetchNotificationsByTitleAndTime(String title, String date);
    
    @Insert
    void insertNotification(Notification warning);
    
    @Insert
    void insertAllNotifications(List<Notification> warnings);
}
