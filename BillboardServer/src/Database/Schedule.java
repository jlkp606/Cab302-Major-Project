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
   private String day;
   private String repeat;

   /**
    * No args constructor
    */
   public Schedule() {
   }

   /**
    * Constructor to set values for the schedule details
    */

   public Schedule(String username, String billboardName, String startTime, String endTime, String day, String repeat) {
      this.username = username;
      this.billboardName = billboardName;
      this.startTime = startTime;
      this.endTime = endTime;
      this.day = day;
      this.repeat = repeat;
   }

   /**
    * @return the name of the billboard
    */
   public String getBillboardName() {return this.billboardName;}
   /**
    * @param billboardName the name of the billboard to set
    */
   public void setBillboardName(String billboardName) {this.billboardName = billboardName;}

   /**
    * @return the name of the representative of the billboard (Company or individual)
    */
   public String getUsername() {return this.username;}
   /**
    * @param username the name of the billboard creator to set
    */
   public void setUsername(String username) {this.username = username;}

   public String getStartTime() {return this.startTime;}
   public void setStartTime(String startTime) {this.startTime = startTime;}

   public String getEndTime() {return this.endTime;}
   public void setEndTime(String endTime) {this.endTime = endTime;}

   public String getDay() {return this.day;}
   public void setDay(String day) {this.day = day;}

   public String getRepeat() {return this.repeat;}
   public void setRepeat(String repeat) {this.repeat = repeat;}

}
