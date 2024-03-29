package Database;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.lang.*;

import static Server.Hash.getHash;

/**
 * Class for retrieving data from the XML file holding the billboard list.
 */
public class JDBCDatabaseSource implements DatabaseSource {

   private Connection connection;

   public static final String CREATE_USER_TABLE =
           "CREATE TABLE IF NOT EXISTS users ("
                   + "userID INTEGER PRIMARY KEY NOT NULL /*!40101 AUTO_INCREMENT */ UNIQUE," // from https://stackoverflow.com/a/41028314
                   + "username VARCHAR(30) UNIQUE,"
                   + "password VARCHAR(100),"
                   + "passwordSalt VARCHAR(100)" + ");";

   private static final String COUNT_USER = "SELECT COUNT(*) FROM users;";
   private static final String INSERT_USER = "INSERT INTO users (username, password, passwordSalt) VALUES (?, ?, ?);";

   private static final String DELETE_USER = "DELETE FROM users WHERE username=?;";

   private static final String GET_USER = "SELECT * FROM users WHERE username=?";

   private static final String SET_USER_PASSWORD = "UPDATE users SET password=? WHERE username=?";

   private static final String GET_USERNAMES = "SELECT username FROM users";

   //private static final String GET_ALL_USERS = "SELECT * FROM users";

   private PreparedStatement addUser;

   private PreparedStatement countUsers;

   private PreparedStatement getUser;

   private PreparedStatement setUserPassword;

   private PreparedStatement deleteUser;

   private PreparedStatement getUsernames;

   //private PreparedStatement getAllUsers;


   public static final String CREATE_BILLBOARD_TABLE =
            "CREATE TABLE IF NOT EXISTS billboard ("
                    + "bName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "username VARCHAR(30),"
                    + "colour VARCHAR(100),"
                    + "message VARCHAR(10000),"
                    + "messageColour VARCHAR(100),"
                    + "pictureData VARCHAR(1000),"
                    + "pictureURL VARCHAR(255),"
                    + "infoMessage VARCHAR(10000),"
                    + "infoColour VARCHAR(100)" + ");";

   private static final String INSERT_BILLBOARD = "INSERT INTO billboard ( bName, username, colour, message, messageColour, pictureData, pictureURL, infoMessage, infoColour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

   private static final String UPDATE_BILLBOARD = "UPDATE billboard SET bName = ?, username = ?, colour = ?, message = ?, messageColour = ?, pictureData = ?, pictureURL = ?, infoMessage = ?, infoColour = ? WHERE bName= ?";

   private static final String GET_ALL_BILLBOARD = "SELECT * FROM billboard";

   private static final String GET_BILLBOARD = "SELECT * FROM billboard WHERE bName=?";

   private static final String GET_BILLBOARD_LIST = "SELECT * FROM billboard";

   private static final String DELETE_BILLBOARD = "DELETE FROM billboard WHERE bName=?";

   private static final String COUNT_ROWS = "SELECT COUNT(*) FROM billboard";

   //private static final String GET_ALL_BILLBOARDS = "SELECT * FROM billboard"; -- Use ALL in placeholder instead

   private PreparedStatement addBillboard;

   private PreparedStatement getAllBillboard;

   private PreparedStatement updateBillboard;

   private PreparedStatement getBillboard;

   private PreparedStatement deleteBillboard;

   private PreparedStatement rowCount;

   //private PreparedStatement getAllBillboards;

   public static final String CREATE_SCHEDULE_TABLE =
           "CREATE TABLE IF NOT EXISTS schedule ("
                   + "username VARCHAR(30) NOT NULL,"
                   + "bName VARCHAR(100),"
                   + "bStartTime VARCHAR(30),"
                   + "bEndTime VARCHAR(30),"
                   + "day VARCHAR(30),"
                   + "repeats VARCHAR(30)" + ");";

   private static final String INSERT_SCHEDULE = "INSERT INTO schedule (username, bName, bStartTime, bEndtime, day, repeats) VALUES (?, ?, ?, ?, ?, ?)";

   private static final String GET_SCHEDULE = "SELECT * FROM schedule WHERE bName=?";

   private static final String DELETE_SCHEDULE = "DELETE FROM schedule WHERE bName=? AND bStartTime = ?";

   private static final String GET_ALL_SCHEDULES = "SELECT * FROM schedule";

   private PreparedStatement addSchedule;

   private PreparedStatement getSchedule;

   private PreparedStatement deleteSchedule;

   private PreparedStatement getAllSchedules;

   public static final String CREATE_PERMISSION_TABLE =
           "CREATE TABLE IF NOT EXISTS permissions ("
                   + "username VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                   + "createBillboard VARCHAR(30),"
                   + "editAllBillboards VARCHAR(30),"
                   + "editSchedule VARCHAR(30),"
                   + "editUsers VARCHAR(30)" + ");";

   private static final String INSERT_PERMISSIONS = "INSERT INTO permissions (username, createBillboard, editAllBillboards, editSchedule, editUsers ) VALUES (?, ?, ?, ?, ?)";

   private static final String SET_USER_PERMISSIONS = "UPDATE permissions SET createBillboard = ?, editAllBillboards = ?, editSchedule = ?, editUsers = ? WHERE username=?";

   private static final String DELETE_USER_PERMISSIONS = "DELETE FROM permissions WHERE username=?";

   private static final String GET_USER_PERMISSIONS = "SELECT * FROM permissions WHERE username = ?";



   private PreparedStatement addPerms;

   private PreparedStatement setPerms;

   private PreparedStatement deletePerms;

   private PreparedStatement getUserPerms;


    /**
     * Establishes Connection to database and allows for uses of Database query functions
     *
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
   public JDBCDatabaseSource() throws SQLException, NoSuchAlgorithmException, IOException {
       //Connect to DB
      connection = Database.DBConnection.getInstance();
      System.out.println("Connected to database");
      Statement st = connection.createStatement();

      // Setup User Functions
      st.execute(CREATE_USER_TABLE);

      addUser = connection.prepareStatement(INSERT_USER);
      getUser = connection.prepareStatement(GET_USER);
      setUserPassword = connection.prepareStatement(SET_USER_PASSWORD);
      deleteUser = connection.prepareStatement(DELETE_USER);
      getUsernames = connection.prepareStatement(GET_USERNAMES);
      countUsers = connection.prepareStatement(COUNT_USER);

      //Set up Billboard Functions
      st.execute(CREATE_BILLBOARD_TABLE);

      addBillboard = connection.prepareStatement(INSERT_BILLBOARD);
      updateBillboard = connection.prepareStatement(UPDATE_BILLBOARD);
      getAllBillboard = connection.prepareStatement(GET_ALL_BILLBOARD);
      getBillboard = connection.prepareStatement(GET_BILLBOARD);
      deleteBillboard = connection.prepareStatement(DELETE_BILLBOARD);
      rowCount = connection.prepareStatement(COUNT_ROWS);

      // Setup Schedule Functions
      st.execute(CREATE_SCHEDULE_TABLE);

      addSchedule = connection.prepareStatement(INSERT_SCHEDULE);
      getSchedule = connection.prepareStatement(GET_SCHEDULE);
      deleteSchedule = connection.prepareStatement(DELETE_SCHEDULE);
      getAllSchedules = connection.prepareStatement(GET_ALL_SCHEDULES);

      // Setup Permission Functions
      st.execute(CREATE_PERMISSION_TABLE);

      addPerms = connection.prepareStatement(INSERT_PERMISSIONS);
      setPerms = connection.prepareStatement(SET_USER_PERMISSIONS);
      deletePerms = connection.prepareStatement(DELETE_USER_PERMISSIONS);
      getUserPerms = connection.prepareStatement(GET_USER_PERMISSIONS);

      //Setup Admin User if not already there
      User admin = getUser("admin");
      if(admin.getUsername() == null){
          admin = new User();
          String salt = admin.getPasswordSalt();
          String hPassword = getHash("admin");
          String dbPassword = getHash(hPassword+salt);
          admin.setPassword(dbPassword);
          admin.setUsername("admin");
      }
   }

    /**
     * @see DatabaseSource#addUser(User)
     */
    public void addUser(User u) throws SQLException {
        addUser.setString(1, u.getUsername());
        addUser.setString(2, u.getPassword());
        addUser.setString(3, u.getPasswordSalt());
        addUser.execute();
    }
    /**
     * @see DatabaseSource#getUser(String)
     */
    public User getUser(String name) throws SQLException {
        User u = new User();
        ResultSet rs = null;

        getUser.setString(1, name);
        rs = getUser.executeQuery();
        if(rs.next()){
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setPasswordSalt(rs.getString("passwordSalt"));
        }
        return u;
    }

   /**
    * @see DatabaseSource#getUsernames();
    */
   public ArrayList<String> getUsernames() throws SQLException {
      ResultSet rs = null;
      ResultSetMetaData rsmd = null;
      ArrayList<String> usernameList;
     rs = getUsernames.executeQuery();
     rsmd = rs.getMetaData();
     int columnCount = rsmd.getColumnCount();

     usernameList = new ArrayList<>(columnCount);
     while (rs.next()) {
        int i = 1;
        while (i <= columnCount) {
           usernameList.add(rs.getString(i++));
        }
     }
     return usernameList;

   }


   /**
    * @see DatabaseSource#setUserPassword(String, String)
    */
   public void setUserPassword(String name, String newPassword) throws SQLException {
     setUserPassword.setString(1, newPassword);
     setUserPassword.setString(2, name);
     setUserPassword.executeUpdate();
   }

   /**
     * @see DatabaseSource#deleteUser(String)
     */
    public void deleteUser(String name) throws SQLException {
        deleteUser.setString(1, name);
        deleteUser.executeUpdate();
    }

    /**
    * @see DatabaseSource#addBillboard(Billboard)
    */
   public void addBillboard(Billboard b) throws SQLException {
       Billboard billboard = getBillboard(b.getbName());
       if(billboard.getbName() == null){
           addBillboard.setString(1, b.getbName());
           addBillboard.setString(2, b.getUsername());
           addBillboard.setString(3, b.getColour());
           addBillboard.setString(4, b.getMessage());
           addBillboard.setString(5, b.getMessageColour());
           addBillboard.setString(6, b.getPictureData());
           addBillboard.setString(7, b.getPictureURL());
           addBillboard.setString(8, b.getInfoMessage());
           addBillboard.setString(9, b.getInfoColour());
           addBillboard.execute();
       } else {
           updateBillboard.setString(1, b.getbName());
           updateBillboard.setString(2, b.getUsername());
           updateBillboard.setString(3, b.getColour());
           updateBillboard.setString(4, b.getMessage());
           updateBillboard.setString(5, b.getMessageColour());
           updateBillboard.setString(6, b.getPictureData());
           updateBillboard.setString(7, b.getPictureURL());
           updateBillboard.setString(8, b.getInfoMessage());
           updateBillboard.setString(9, b.getInfoColour());
           updateBillboard.setString(10, b.getbName());
           updateBillboard.execute();
       }
   }

    /**
     * @see DatabaseSource#getAllBillboards()
     */
    public ArrayList<Billboard> getAllBillboards() throws SQLException {
        ResultSet rs = null;
        ArrayList<Billboard> billboardList;
        rs = getAllBillboard.executeQuery();

        billboardList = new ArrayList<>();
        while (rs.next()) {
            Billboard billboard = new Billboard();
            billboard.setbName(rs.getString("bName"));
            billboard.setUsername(rs.getString("username"));
            billboard.setColour(rs.getString("colour"));
            billboard.setMessage(rs.getString("message"));
            billboard.setMessageColour(rs.getString("messageColour"));
            billboard.setPictureData(rs.getString("pictureData"));
            billboard.setPictureURL(rs.getString("pictureURL"));
            billboard.setInfoMessage(rs.getString("infoMessage"));
            billboard.setInfoColour(rs.getString("infoColour"));
            billboardList.add(billboard);
        }
        return billboardList;
    }

    /**
     * @see DatabaseSource#deleteBillboard
     */
    public void deleteBillboard(String billboardName) throws SQLException {
      deleteBillboard.setString(1, billboardName);
      deleteBillboard.executeUpdate();
    }



    /**
     * @see DatabaseSource#nameSet()
     */
    public Set<String> nameSet() throws SQLException {
        Set<String> names = new TreeSet<String>();
        ResultSet rs = null;

        rs = getAllBillboard.executeQuery();
        while (rs.next()) {
            names.add(rs.getString("name"));
        }

        return names;
    }

    /**
     * @see DatabaseSource#getBillboard(String)
     */
    public Billboard getBillboard(String name) throws SQLException {
        Billboard b = new Billboard();
        ResultSet rs = null;

        getBillboard.setString(1, name);
        rs = getBillboard.executeQuery();
        if(rs.next()){
            b.setbName(rs.getString("bname"));
            b.setUsername(rs.getString("username"));
            b.setColour(rs.getString("colour"));
            b.setMessage(rs.getString("message"));
            b.setMessageColour(rs.getString("messageColour"));
            b.setPictureData(rs.getString("pictureData"));
            b.setPictureURL(rs.getString("pictureUrl"));
            b.setInfoMessage(rs.getString("infoMessage"));
            b.setInfoColour(rs.getString("infoColour"));
        }
        return b;
    }

    /**
     * @see DatabaseSource#getSize()
     */
    public int getSize() throws SQLException {
        ResultSet rs = null;
        int rows = 0;

        rs = rowCount.executeQuery();
        rs.next();
        rows = rs.getInt(1);

        return rows;
    }

   /**
    * @see DatabaseSource#addUserPerms(String, Permissions)
    */
   public void addUserPerms(String username, Permissions permission) throws SQLException {
       addPerms.setString(1, username);
       addPerms.setString(2, permission.getCreateBillboard());
       addPerms.setString(3, permission.getEditAllBillboards());
       addPerms.setString(4, permission.getEditSchedule());
       addPerms.setString(5, permission.getEditUsers());

       addPerms.execute();
   }
    /**
     * @see DatabaseSource#updateUserPerms(String,Permissions)
     */
   public void updateUserPerms(String username, Permissions permission) throws SQLException {
       setPerms.setString(1, permission.getCreateBillboard());
       setPerms.setString(2, permission.getEditAllBillboards());
       setPerms.setString(3, permission.getEditSchedule());
       setPerms.setString(4, permission.getEditUsers());
       setPerms.setString(5, username);

       setPerms.execute();
   }

   /**
    * @see DatabaseSource#deletePerms(String)
    */
   public void deletePerms(String name) throws SQLException {
     deleteUser.setString(1, name);
     deleteUser.executeUpdate();
   }

   /**
    * @see DatabaseSource#getUserPerms(String)
    */
   public Permissions getUserPerms(String username) throws SQLException {
      Permissions p = new Permissions();
      ResultSet rs = null;

     getUserPerms.setString(1, username);
     rs = getUserPerms.executeQuery();
     rs.next();
     p.setUsername(rs.getString("Username"));
     p.setCreateBillboard(rs.getString("CreateBillboard"));
     p.setEditAllBillboards(rs.getString("editAllBillboards"));
     p.setEditSchedule(rs.getString("EditSchedule"));
     p.setEditUsers(rs.getString("EditUsers"));

      return p;
   }

   /**
    * @see DatabaseSource#addSchedule(Schedule)
    */
   public void addSchedule(Schedule schedule) throws SQLException {
     addSchedule.setString(1, schedule.getUsername());
     addSchedule.setString(2, schedule.getBillboardName());
     addSchedule.setString(3, schedule.getStartTime());
     addSchedule.setString(4, schedule.getEndTime());
     addSchedule.setString(5, schedule.getDay());
     addSchedule.setString(6, schedule.getRepeat());

     addSchedule.execute();
   }

   /**
    * @see DatabaseSource#getSchedule
    */
   public Schedule getSchedule(String billboardName) throws SQLException {
      Schedule s = new Schedule();
      ResultSet rs = null;

     getSchedule.setString(1, billboardName);
     rs = getSchedule.executeQuery();
     rs.next();
     s.setBillboardName(rs.getString("bName"));
     s.setUsername(rs.getString("username"));
     s.setStartTime(rs.getString("bstartTime"));
     s.setEndTime(rs.getString("endTime"));
     s.setDay(rs.getString("day"));
     s.setRepeat(rs.getString("repeats"));


      return s;
   }

   /**
    * @see DatabaseSource#deleteSchedule(Schedule)
    */
   public void deleteSchedule(Schedule schedule) throws SQLException {
     deleteSchedule.setString(1, schedule.getBillboardName());
     deleteSchedule.setString(2, schedule.getStartTime());
     deleteSchedule.executeUpdate();
   }

   /**
    * @see DatabaseSource#getAllSchedules();
    */
    public ArrayList<Schedule> getAllSchedules() throws SQLException {
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<Schedule> scheduleList;
        rs = getAllSchedules.executeQuery();
        rsmd = rs.getMetaData();
        scheduleList = new ArrayList<>();
        while (rs.next()) {
            Schedule schedule = new Schedule();
            schedule.setBillboardName(rs.getString("bName"));
            schedule.setStartTime(rs.getString("bStartTime"));
            schedule.setEndTime(rs.getString("bEndTime"));
            schedule.setDay(rs.getString("day"));
            schedule.setRepeat(rs.getString("repeats"));
            scheduleList.add(schedule);
        }
        return scheduleList;
    }

}