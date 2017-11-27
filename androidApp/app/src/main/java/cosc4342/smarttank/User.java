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
    
    private String piSerial;
    
    public String name;
    
    
    User(){}
    
    User(String email, String passcode, String name, String serial_number){
        this.email = email;
        this.name = name;
        password = passcode;
        piSerial = serial_number;
    }
    
    
    public void setEmail(String newEmail){
        email = newEmail;
    }
    
    public void setPassword(String newPassword){
        password = newPassword;
    }
    
    public void setPiSerial(String serial_number){
        piSerial = serial_number;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    
    public String getEmail(){
        return email;
    }
    
    public String getPassword(){
        return password;
    }
    
    
    public String getPiSerial(){
        return piSerial;
    }
    
    public String getName(){
        return name;
    }
}




