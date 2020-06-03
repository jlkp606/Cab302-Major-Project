package Database;

import java.io.Serializable;
import java.util.HashMap;

import static Token.Token.generateRandomString;

public class User implements Comparable<User>, Serializable {

    private String username;
    private String password;
    private String passwordSalt;

    public User() {
        this.passwordSalt = generateRandomString();
    }

    public void setPassword(String password){ this.password = password; }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {
        return this.password;
    }

    public void setPasswordSalt(String salt) {this.passwordSalt = salt;}
    public String getPasswordSalt() {
        return this.passwordSalt;
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
}
