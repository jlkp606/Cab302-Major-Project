package DataBase;

import java.io.Serializable;
import java.util.HashMap;

import static Token.Token.generateRandomString;

public class User implements Serializable {
    private String userID;
    private String password;
    private String passwordSalt;

    private HashMap<String, Boolean> permission;

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.passwordSalt = generateRandomString();
    }


    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public String getPasswordSalt() {
        return passwordSalt;
    }

}
