package cosc4342.smarttank;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class, Sensor.class, Notification.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase{
    
    private static AppDatabase INSTANCE;
    public abstract UserDao userModel();
    public abstract SensorDao sensorModel();
    public abstract NotificationDao notificationModel();
    
    public static AppDatabase getDB(Context context){
        
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "smart_tank").build();
        }
        return INSTANCE;
    }
    
    
    
    
    public static void destroyDB(){
        INSTANCE = null;
    }
}
