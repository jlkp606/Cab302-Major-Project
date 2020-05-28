package DataBase;

import java.io.Serializable;
import java.util.HashMap;

import static Token.Token.generateRandomString;

public class User implements Comparable<User>, Serializable {

    private String username;
    private String password;
    private String passwordSalt;

    private HashMap<String, Boolean> permission;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.passwordSalt = generateRandomString();
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String bName) {this.username = username;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String bName) {this.password = password;}

    public String getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     *
     * @param other The other Billboard object to compare against.
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it from
     *            being compared to this object.
     */
    public int compareTo(User other) {
        return this.username.compareTo(other.username);
    }

    public String toString() {
        return username + " " + password + ", " + passwordSalt;
    }

}
