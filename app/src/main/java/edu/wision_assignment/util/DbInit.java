package edu.wision_assignment.util;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import edu.wision_assignment.dao.UserDao;
import edu.wision_assignment.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class DbInit extends RoomDatabase {
    public abstract UserDao userDao();
}