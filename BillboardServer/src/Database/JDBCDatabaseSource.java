package Database;

import java.security.Permission;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.lang.*;

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

   private static final String INSERT_USER = "INSERT INTO users (username, password, passwordSalt) VALUES (?, ?, ?);";

   private static final String DELETE_USER = "DELETE FROM users WHERE username=?;";

   private static final String GET_USER = "SELECT * FROM users WHERE username=?";

   private static final String SET_USER_PASSWORD = "UPDATE users SET password=? WHERE username=?";

   private static final String GET_USERNAMES = "SELECT username FROM users";

   //private static final String GET_ALL_USERS = "SELECT * FROM users";

   private PreparedStatement addUser;

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
                    + "message VARCHAR(100),"
                    + "messageColour VARCHAR(100),"
                    + "pictureData VARCHAR(1000),"
                    + "pictureURL VARCHAR(255),"
                    + "infoMessage VARCHAR(100),"
                    + "infoColour VARCHAR(100)" + ");";

   private static final String INSERT_BILLBOARD = "INSERT INTO billboard ( bName, username, colour, message, messageColour, pictureData, pictureURL, infoMessage, infoColour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

   private static final String GET_ALL_BILLBOARD = "SELECT * FROM billboard";

   private static final String GET_BILLBOARD = "SELECT * FROM billboard WHERE bName=?";

   private static final String GET_BILLBOARD_LIST = "SELECT * FROM billboard";

   private static final String DELETE_BILLBOARD = "DELETE FROM billboard WHERE bName=?";

   private static final String COUNT_ROWS = "SELECT COUNT(*) FROM billboard";

   //private static final String GET_ALL_BILLBOARDS = "SELECT * FROM billboard"; -- Use ALL in placeholder instead

   private PreparedStatement addBillboard;

   private PreparedStatement getAllBillboard;

   private PreparedStatement getBillboard;

   private PreparedStatement deleteBillboard;

   private PreparedStatement rowCount;

   //private PreparedStatement getAllBillboards;

   public static final String CREATE_SCHEDULE_TABLE =
           "CREATE TABLE IF NOT EXISTS schedule ("
                   + "username VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                   + "bName VARCHAR(30),"
                   + "bStartTime VARCHAR(30),"
                   + "bEndTime VARCHAR(30),"
                   + "repeats VARCHAR(30)" + ");";

   private static final String INSERT_SCHEDULE = "INSERT INTO schedule (username, bName, bStartTime, bEndtime, repeats) VALUES (?, ?, ?, ?, ?)";

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



   public JDBCDatabaseSource() {
      connection = Database.DBConnection.getInstance();
      try {

          Statement st = connection.createStatement();

          st.execute(CREATE_USER_TABLE);

          addUser = connection.prepareStatement(INSERT_USER);
          getUser = connection.prepareStatement(GET_USER);
          setUserPassword = connection.prepareStatement(SET_USER_PASSWORD);
          deleteUser = connection.prepareStatement(DELETE_USER);
          getUsernames = connection.prepareStatement(GET_USERNAMES);


          st.execute(CREATE_BILLBOARD_TABLE);

          addBillboard = connection.prepareStatement(INSERT_BILLBOARD);
          getAllBillboard = connection.prepareStatement(GET_ALL_BILLBOARD);
          getBillboard = connection.prepareStatement(GET_BILLBOARD);
          deleteBillboard = connection.prepareStatement(DELETE_BILLBOARD);
          rowCount = connection.prepareStatement(COUNT_ROWS);
          //getAllBillboards = connection.prepareStatement(GET_ALL_BILLBOARDS);

          st.execute(CREATE_SCHEDULE_TABLE);

          addSchedule = connection.prepareStatement(INSERT_SCHEDULE);
          getSchedule = connection.prepareStatement(GET_SCHEDULE);
          deleteSchedule = connection.prepareStatement(DELETE_SCHEDULE);
          getAllSchedules = connection.prepareStatement(GET_ALL_SCHEDULES);

          st.execute(CREATE_PERMISSION_TABLE);

          addPerms = connection.prepareStatement(INSERT_PERMISSIONS);
          setPerms = connection.prepareStatement(SET_USER_PERMISSIONS);
          deletePerms = connection.prepareStatement(DELETE_USER_PERMISSIONS);
          getUserPerms = connection.prepareStatement(GET_USER_PERMISSIONS);

          System.out.println("Tables created successfully");

      } catch (SQLException ex) {
         ex.printStackTrace();
      }
   }

    /**
     * @see DatabaseSource
     */
    public void addUser(User u) {
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
    public User getUser(String name) {
        User u = new User();
        ResultSet rs = null;

        try {
            getUser.setString(1, name);
            rs = getUser.executeQuery();
            rs.next();
            u.setUsername(rs.getString("Username"));
            u.setPassword(rs.getString("Password"));
            u.setPasswordSalt(rs.getString("PasswordSalt"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return u;
    }

   /**
    * @see DatabaseSource#getUsernames();
    */
   public ArrayList<String> getUsernames() {
      ResultSet rs = null;
      ResultSetMetaData rsmd = null;
      ArrayList<String> usernameList;
      try {
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
      } catch (SQLException ex) {
         ex.printStackTrace();
      }
      return null;
   }


   /**
    * @see DatabaseSource#setUserPassword(String, String)
    */
   public void setUserPassword(String name, String newPassword) {
      try {
         setUserPassword.setString(1, name);
         setUserPassword.setString(2, newPassword);
      } catch (SQLException ex) {
         ex.printStackTrace();
      }
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
         addBillboard.setString(2, b.getUsername());
         addBillboard.setString(3, b.getColour());
         addBillboard.setString(4, b.getMessage());
         addBillboard.setString(5, b.getMessageColour());
         addBillboard.setString(6, b.getPictureData());
         addBillboard.setString(7, b.getPictureURL());
         addBillboard.setString(8, b.getInfoMessage());
         addBillboard.setString(9, b.getInfoColour());

         addBillboard.execute();
      } catch (SQLException ex) {
         ex.printStackTrace();
      }
   }

    public ArrayList<Billboard> getAllBillboards() {
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<Billboard> billboardList;
        try {
            rs = getAllBillboard.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @see DatabaseSource#deleteBillboard
     */
    public void deleteBillboard(String billboardName) {
       try {
          deleteBillboard.setString(1, billboardName);
          deleteBillboard.executeUpdate();
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
            rs = getAllBillboard.executeQuery();
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
            b.setbName(rs.getString("name"));
            b.setUsername(rs.getString("username"));
            b.setColour(rs.getString("colour"));
            b.setMessage(rs.getString("message"));
            b.setMessage(rs.getString("messageColour"));
            b.setPictureData(rs.getString("pictureData"));
            b.setPictureURL(rs.getString("pictureUrl"));
            b.setInfoMessage(rs.getString("infoMessage"));
            b.setInfoColour(rs.getString("infoColour"));

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

   /**
    * @see DatabaseSource //  #addUserPerms
    */
   public void addUserPerms(String username, Permissions permission) {
       try {
           addPerms.setString(1, username);
           addPerms.setString(2, permission.getCreateBillboard());
           addPerms.setString(3, permission.getEditAllBillboards());
           addPerms.setString(4, permission.getEditSchedule());
           addPerms.setString(5, permission.getEditUsers());

           addPerms.execute();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
   }

   public void updateUserPerms(String username, Permissions permission){
       try {
           setPerms.setString(1, permission.getCreateBillboard());
           setPerms.setString(2, permission.getEditAllBillboards());
           setPerms.setString(3, permission.getEditSchedule());
           setPerms.setString(4, permission.getEditUsers());
           setPerms.setString(5, username);

           setPerms.execute();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
   }

   /**
    * @see DatabaseSource#deletePerms(String)
    */
   public void deletePerms(String name) {
      try {
         deleteUser.setString(1, name);
         deleteUser.executeUpdate();
      } catch (SQLException ex) {
         ex.printStackTrace();
      }
   }

   /**
    * @see DatabaseSource#getUserPerms(String)
    */
   public Permissions getUserPerms(String username) {
      Permissions p = new Permissions();
      ResultSet rs = null;

      try {
         getUserPerms.setString(1, username);
         rs = getUserPerms.executeQuery();
         rs.next();
         p.setUsername(rs.getString("Username"));
         p.setCreateBillboard(rs.getString("CreateBillboard"));
         p.setEditAllBillboards(rs.getString("EditAllBillboard"));
         p.setEditSchedule(rs.getString("EditSchedule"));
         p.setEditUsers(rs.getString("EditUsers"));
      } catch (SQLException ex) {
         ex.printStackTrace();
      }

      return p;
   }

   /**
    * @see DatabaseSource#addSchedule(Schedule)
    */
   public void addSchedule(Schedule schedule) {
      try {
         addSchedule.setString(1, schedule.getUsername());
         addSchedule.setString(2, schedule.getBillboardName());
         addSchedule.setString(3, schedule.getStartTime());
         addSchedule.setString(4, schedule.getEndTime());
         addSchedule.setString(5, schedule.getRepeat());

         addSchedule.execute();
      } catch (SQLException ex) {
         ex.printStackTrace();
      }
   }

   /**
    * @see DatabaseSource#getSchedule
    */
   public Schedule getSchedule(String billboardName) {
      Schedule s = new Schedule();
      ResultSet rs = null;

      try {
         getSchedule.setString(1, billboardName);
         rs = getSchedule.executeQuery();
         rs.next();
         s.setBillboardName(rs.getString("bName"));
         s.setUsername(rs.getString("username"));
         s.setStartTime(rs.getString("bstartTime"));
         s.setEndTime(rs.getString("endTime"));
         s.setRepeat(rs.getString("repeats"));

      } catch (SQLException ex) {
         ex.printStackTrace();
      }

      return s;
   }

   /**
    * @see DatabaseSource#deleteSchedule(String)
    */
   public void deleteSchedule(Schedule schedule) {
      try {
         deleteSchedule.setString(1, schedule.getBillboardName());
         deleteSchedule.setString(2, schedule.getStartTime());
         deleteSchedule.executeUpdate();
      } catch (SQLException ex) {
         ex.printStackTrace();
      }
   }

   /**
    * @see DatabaseSource#getAllSchedules();
    */
    public ArrayList<Schedule> getAllSchedules() {
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<Schedule> scheduleList;
        try {
            rs = getAllSchedules.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            scheduleList = new ArrayList<>();
            while (rs.next()) {
                int i = 1;
                while (i <= columnCount) {
                    Schedule schedule = new Schedule();
                    schedule.setBillboardName(rs.getString("bName"));
                    schedule.setStartTime(rs.getString("bStartTime"));
                    schedule.setEndTime(rs.getString("bEndTime"));
                    schedule.setRepeat(rs.getString("repeats"));
                    scheduleList.add(schedule);
                }
            }
            return scheduleList;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}