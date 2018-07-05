package edu.wision_assignment.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.wision_assignment.model.User;

@Dao
public interface UserDao {

    @Insert
    void insertOnlySingleRecord(User university);

    @Query("SELECT * FROM User")
    List<User> fetchAllData();

    @Query("SELECT * FROM User WHERE usremail =:user_email")
    User getSingleRecord(String user_email);


}