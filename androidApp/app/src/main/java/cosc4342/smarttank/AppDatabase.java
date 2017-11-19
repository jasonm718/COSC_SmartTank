package cosc4342.smarttank;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class, Sensor.class, Notification.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract SensorDao sensorDao();
    public abstract NotificationDao notificationDao();
}
