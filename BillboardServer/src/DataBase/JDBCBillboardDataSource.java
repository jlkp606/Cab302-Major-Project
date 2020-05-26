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
                    + "password VARCHAR(30),"
                    + "passwordSalt VARCHAR(30)" + ");";

    private static final String INSERT_USER = "INSERT INTO users (userID, password, passwordSalt) VALUES (?, ?, ?);";

    private Connection connection;

    private PreparedStatement addUser;

    public JDBCBillboardDataSource() {
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            /* BEGIN MISSING CODE */
            addUser = connection.prepareStatement(INSERT_USER);
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
            addUser.setString(1, p.getUserID());
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
