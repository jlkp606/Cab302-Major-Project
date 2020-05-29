package DataBase;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class for retrieving data from the XML file holding the billboard list.
 */
public class JDBCDatabaseSource implements DatabaseSource {


   public static final String CREATE_USER_TABLE =
           "CREATE TABLE IF NOT EXISTS users ("
                   + "userID INTEGER PRIMARY KEY NOT NULL /*!40101 AUTO_INCREMENT */ UNIQUE," // from https://stackoverflow.com/a/41028314
                   + "username VARCHAR(30) UNIQUE,"
                   + "password VARCHAR(30),"
                   + "passwordSalt VARCHAR(30)" + ");";

   private static final String INSERT_USER = "INSERT INTO users (username, password, passwordSalt) VALUES (?, ?, ?);";

   private static final String DELETE_USER = "DELETE FROM users WHERE username=?;";

   private static final String GET_USER = "SELECT username FROM users WHERE username=?";

   private static final String SET_USER_PASSWORD = "UPDATE users SET password=? WHERE username=?";

   private PreparedStatement addUser;

   private PreparedStatement getUser;

   private PreparedStatement setUserPassword;

   private PreparedStatement deleteUser;

   public static final String CREATE_BILLBOARD_TABLE =
            "CREATE TABLE IF NOT EXISTS billboard ("
                    + "bID INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "bName VARCHAR(30),"
                    + "bRep VARCHAR(30),"
                    + "bData VARCHAR(30)" + ");";

   private static final String INSERT_BILLBOARD = "INSERT INTO billboard (bID, bName, bRep, bData) VALUES (?, ?, ?, ?);";

   private static final String GET_BILLBOARD_NAME = "SELECT bName FROM billboard";

   private static final String GET_BILLBOARD = "SELECT * FROM billboard WHERE BName=?";

   private static final String DELETE_BILLBOARD = "DELETE FROM billboard WHERE BName=?";

   private static final String COUNT_ROWS = "SELECT COUNT(*) FROM billboard";

   private Connection connection;

   private PreparedStatement addBillboard;

   private PreparedStatement getNameList;

   private PreparedStatement getBillboard;

   private PreparedStatement deleteBillboard;

   private PreparedStatement rowCount;

   public JDBCDatabaseSource() {
      connection = DataBase.DBConnection.getInstance();
      try {

          Statement st = connection.createStatement();

          st.execute(CREATE_USER_TABLE);

          addUser = connection.prepareStatement(INSERT_USER);
          getUser = connection.prepareStatement(GET_USER);
          setUserPassword = connection.prepareStatement(SET_USER_PASSWORD);
          deleteUser = connection.prepareStatement(DELETE_USER);

          st.execute(CREATE_BILLBOARD_TABLE);

          addBillboard = connection.prepareStatement(INSERT_BILLBOARD);
          getNameList = connection.prepareStatement(GET_BILLBOARD_NAME);
          getBillboard = connection.prepareStatement(GET_BILLBOARD);
          deleteBillboard = connection.prepareStatement(DELETE_BILLBOARD);
          rowCount = connection.prepareStatement(COUNT_ROWS);

          System.out.println("Tables created successfully");

      } catch (SQLException ex) {
         ex.printStackTrace();
      }
   }

    /**
     * @see DatabaseSource
     */
    public void addUser(DataBase.User u) {
        try {
            addUser.setString(1, u.getUsername());
            addUser.setString(2, u.getPassword());
            addUser.setString(3, u.getPasswordSalt());
            addUser.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * @see DatabaseSource#getUser(String)
     */
    public DataBase.User getUser(String name) {
        DataBase.User u = new DataBase.User();
        ResultSet rs = null;

        try {
            getUser.setString(1, name);
            rs = getUser.executeQuery();
            rs.next();
            u.setUsername(rs.getString("Username"));
            u.setPassword(rs.getString("Password"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return u;
    }

    /**
     * @see DatabaseSource#deleteUser(String)
     */
    public void deleteUser(String name) {
        try {
            deleteUser.setString(1, name);
            deleteUser.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
    * @see DatabaseSource#addBillboard(Billboard)
    */
   public void addBillboard(Billboard b) {
      try {
         addBillboard.setString(1, b.getbName());
         addBillboard.setString(2, b.getbRep());
         addBillboard.setString(3, b.getbData());
         addBillboard.execute();
      } catch (SQLException ex) {
         ex.printStackTrace();
      }
   }
    /**
     * @see DatabaseSource#nameSet()
     */
    public Set<String> nameSet() {
        Set<String> names = new TreeSet<String>();
        ResultSet rs = null;

        try {
            rs = getNameList.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return names;
    }

    /**
     * @see DatabaseSource#getBillboard(String)
     */
    public Billboard getBillboard(String name) {
        Billboard b = new Billboard();
        ResultSet rs = null;

        try {
            getBillboard.setString(1, name);
            rs = getBillboard.executeQuery();
            rs.next();
            b.setbName(rs.getString("Name"));
            b.setbRep(rs.getString("Representative"));
            b.setbData(rs.getString("Billboard Data"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return b;
    }

    /**
     * @see DatabaseSource#getSize()
     */
    public int getSize() {
        ResultSet rs = null;
        int rows = 0;

        try {
            rs = rowCount.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rows;
    }
}
