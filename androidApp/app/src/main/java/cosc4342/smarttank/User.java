package cosc4342.smarttank;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "Users")
public class User{
    
    @PrimaryKey @NonNull
    private String email;
    
    private String password;
    
    public String piSerial;
    
    
    public void setEmail(String newEmail){
        email = newEmail;
    }
    
    public void setPassword(String newPassword){
        password = newPassword;
    }
    
    
    public String getEmail(){
        return email;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void addPiSerial(String serial_number){
        piSerial = serial_number;
    }
    
    public String getPiSerial(){
        return piSerial;
    }
}




