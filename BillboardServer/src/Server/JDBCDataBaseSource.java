package Server;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;


/**
 * Class for retrieving data from the XML file holding the address list.
 */
public class JDBCDataBaseSource implements DatabaseSource{

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
