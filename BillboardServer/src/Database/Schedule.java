package Database;

import java.io.Serializable;

/**
 * Stores details for a Schedule
 */
public class Schedule implements Serializable {

   private String username;
   private String billboardName;
   private String startTime;
   private String endTime;
   private String repeat;

   /**
    * No args constructor
    */
   public Schedule() {
   }

   /**
    * Constructor to set values for the schedule details
    */

   public Schedule(String username, String billboardName, String startTime, String endTime, String repeat) {
      this.username = username;
      this.billboardName = billboardName;
      this.startTime = startTime;
      this.endTime = endTime;
      this.repeat = repeat;
   }

   /**
    * @return the name of the billboard
    */
   public String getBillboardName() {return billboardName;}
   /**
    * @param billboardName the name of the billboard to set
    */
   public void setBillboardName(String billboardName) {this.billboardName = billboardName;}

   /**
    * @return the name of the representative of the billboard (Company or individual)
    */
   public String getUsername() {return username;}
   /**
    * @param username the name of the billboard creator to set
    */
   public void setUsername(String username) {this.username = username;}

   public String getStartTime() {return startTime;}
   public void setStartTime(String startTime) {this.startTime = startTime;}

   public String getEndTime() {return endTime;}
   public void setEndTime(String endTime) {this.endTime = endTime;}

   public String getRepeat() {return repeat;}
   public void setRepeat(String repeat) {this.repeat = repeat;}

}
