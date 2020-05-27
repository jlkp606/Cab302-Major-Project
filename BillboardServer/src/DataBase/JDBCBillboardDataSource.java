package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;


/**
 * Class for retrieving data from the XML file holding the address list.
 */
public class JDBCBillboardDataSource{

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS users ("
                    + "userID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "username VARCHAR(30) UNIQUE,"
                    + "password VARCHAR(30),"
                    + "passwordSalt VARCHAR(30)" + ");";

    private static final String INSERT_USER = "INSERT INTO users (username, password, passwordSalt) VALUES (?, ?, ?);";

    private static final String DELETE_USER = "DELETE FROM users WHERE userID=?;";

    private Connection connection;

    private PreparedStatement addUser;
    private PreparedStatement deleteUser;

    public JDBCBillboardDataSource() {
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            /* BEGIN MISSING CODE */
            addUser = connection.prepareStatement(INSERT_USER);
            deleteUser = connection.prepareStatement(DELETE_USER);
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @see dataExercise.AddressBookDataSource#addPerson(dataExercise.Person)
     */
    public void addUser(User p) {
        try {
            addUser.setString(1, p.getUsername());
            addUser.setString(2, p.getPassword());
            addUser.setString(3, p.getPasswordSalt());
            addUser.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * @see dataExercise.AddressBookDataSource#close()
     */
    public void close() {
        /* BEGIN MISSING CODE */
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
    }
}
