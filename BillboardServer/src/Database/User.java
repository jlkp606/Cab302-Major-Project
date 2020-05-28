package DataBase;

import java.io.Serializable;
import java.util.HashMap;

import static Token.Token.generateRandomString;

public class User implements Serializable {
    private String username;
    private String password;
    private String passwordSalt;

    private HashMap<String, Boolean> permission;

    public User(String username) {
        this.username = username;
        this.passwordSalt = generateRandomString();
    }

    public void setPassword(String password){ this.password = password; }

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
