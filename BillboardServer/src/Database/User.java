package DataBase;

import java.io.Serializable;
import java.util.HashMap;

import static Token.Token.generateRandomString;

public class User implements Serializable {
    private String username;
    private String password;
    private String passwordSalt;

    private HashMap<String, Boolean> permission;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.passwordSalt = generateRandomString();
    }


    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getPasswordSalt() {
        return passwordSalt;
    }

}
