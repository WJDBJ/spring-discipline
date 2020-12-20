package com.nf.entity;

/**
 * @author XJ
 */
public class UserInfoEntity {
    int id;
    String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
