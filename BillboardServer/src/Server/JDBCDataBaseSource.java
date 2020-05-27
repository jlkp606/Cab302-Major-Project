package Server;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;


/**
 * Class for retrieving data from the XML file holding the address list.
 */
public class JDBCDataBaseSource {

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

   public JDBCDataBaseSource() {
      connection = DBConnection.getInstance();
      try {
         Statement st = connection.createStatement();
         st.execute(CREATE_BILLBOARD_TABLE);

         addBillboard = connection.prepareStatement(INSERT_BILLBOARD);
         getNameList = connection.prepareStatement(GET_BILLBOARD_NAME);
         getBillboard = connection.prepareStatement(GET_BILLBOARD);
         deleteBillboard = connection.prepareStatement(DELETE_BILLBOARD);
         rowCount = connection.prepareStatement(COUNT_ROWS);

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
}
