package Database;

public class Permissions {

   private String username;
   private String createBillboard;
   private String editAllBillboards;
   private String editSchedule;
   private String editUsers;

   /**
    * No args constructor
    */
   public Permissions() {
   }

   /**
    * Constructor to set values for the schedule details
    */

   public Permissions(String username, String createBillboard, String editAllBillboards, String editSchedule, String editUsers) {
      this.username = username;
      this.createBillboard = createBillboard;
      this.editAllBillboards = editAllBillboards;
      this.editSchedule = editSchedule;
      this.editUsers = editUsers;
   }

   /**
    * @return the name of the billboard
    */
   public String getCreateBillboard() {return createBillboard;}
   /**
    * @param createBillboard Whether the user can create billboards or not. Format: 'TRUE' or 'FALSE'
    */
   public void setCreateBillboard(String createBillboard) {this.createBillboard = createBillboard;}

   /**
    * @return the name of the representative of the billboard (Company or individual)
    */
   public String getUsername() {return username;}
   /**
    * @param username the name of the billboard creator to set
    */
   public void setUsername(String username) {this.username = username;}

   public String getEditAllBillboards() {return editAllBillboards;}
   public void setEditAllBillboards(String editAllBillboards) {this.editAllBillboards = editAllBillboards;}

   public String getEditSchedule() {return editSchedule;}
   public void setEditSchedule(String editSchedule) {this.editSchedule = editSchedule;}

   public String getEditUsers() {return editUsers;}
   public void setEditUsers(String editUsers) {this.editUsers = editUsers;}

}
