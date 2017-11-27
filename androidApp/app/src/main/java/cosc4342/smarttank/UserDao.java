package cosc4342.smarttank;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface UserDao {
    
    
    @Query("Select * from Users where email = :email and password = :password")
    User getUser(String email, String password);
    
    @Query("Select piserial from Users where email = :email and password = :password")
    String getPiserial(String email, String password);
    
    @Update
    void updateUser(User user);
    
    @Insert
    void insertUser(User user);
    
    @Insert
    void insertAllUsers(List<User> users);
}
