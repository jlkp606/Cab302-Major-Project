package Database;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {

    /**
     * The singleton instance of the database connection.
     */
    private static Connection instance = null;

    /**
     * Singleton instance of statement
     */
    private static Statement statement = null;

    /**
     * Constructor intializes the connection.
     */

    private DBConnection() throws SQLException, IOException {

        Properties props = new Properties();
        FileInputStream in = null;
        in = new FileInputStream("./BillboardServer/db.props");
        props.load(in);
        in.close();

        // specify the data source, username and password
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        String schema = props.getProperty("jdbc.schema");

        //Create the database if not already exists
        Connection conn = DriverManager.getConnection(url+ "/", username,
                password);

        statement = conn.createStatement();
        String sql = "CREATE DATABASE IF NOT EXISTS " + schema;

        statement.executeUpdate(sql);

        // get a connection
        instance = DriverManager.getConnection(url + "/" + schema, username,
                password);
}

    /**
     * Provides global access to the singleton instance of the UrlSet.
     *
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() throws IOException, SQLException {
        if (instance == null) {
            new DBConnection();
        }
        return instance;
    }
}
