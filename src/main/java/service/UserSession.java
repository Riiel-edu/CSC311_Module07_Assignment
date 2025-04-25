package service;

import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;

public class UserSession {

    private static UserSession instance;

    private static String username;
    private static String password;
    private static String privileges;

    private UserSession(String userName, String password, String privileges) {
        this.username = userName;
        this.password = password;
        this.privileges = privileges;
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("USERNAME",userName);
        userPreferences.put("PASSWORD",password);
        userPreferences.put("PRIVILEGES",privileges);
    }



    public static UserSession getInstance(String userName,String password, String privileges) {
        if(instance == null) {
            instance = new UserSession(userName, password, privileges);
        }
        return instance;
    }

    public static UserSession getInstance(String userName,String password) {
        if(instance == null) {
            instance = new UserSession(userName, password, "NONE");
        }
        return instance;
    }
    public static String getUserName() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getPrivileges() {
        return privileges;
    }

    public static void setCurrUser(String newuser, String newpass) {
        username = newuser;
        password = newpass;
    }

    public static void cleanUserSession() {
        username = "";// or null
        password = "";
        privileges = "";// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + username + '\'' +
                ", privileges=" + privileges +
                '}';
    }
}
