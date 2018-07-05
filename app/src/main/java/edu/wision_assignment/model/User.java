package edu.wision_assignment.model;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import edu.wision_assignment.pojo.Users;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int sNo;

    @Embedded(prefix = "usr")
    Users users;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
