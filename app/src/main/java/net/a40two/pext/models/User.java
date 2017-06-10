package net.a40two.pext.models;

public class User {
    String username;
    String userApiKey;

    public User(String name, String key) {
        this.username = name;
        this.userApiKey = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserApiKey() {
        return userApiKey;
    }

    public void setUserApiKey(String userApiKey) {
        this.userApiKey = userApiKey;
    }


}
