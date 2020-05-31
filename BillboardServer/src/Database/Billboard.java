package Database;

import java.io.Serializable;

/**
 * Stores details for a billboard
 */
public class Billboard implements Comparable<Billboard>, Serializable {

    private String bName;
    private String username;
    private String colour;
    private String message;
    private String messageColour;
    private String pictureData;
    private String pictureURL;
    private String infoMessage;
    private String infoColour;

    /**
     * No args constructor
     */
    public Billboard() {
    }

    /**
     * Constructor to set values for the billboard details
     * @param bName The name of the billboard
     * @param username The name of the user who created the billboard
     */

    public Billboard(String bName, String username, String colour, String message,String messageColour, String pictureData, String pictureURL, String infoMessage, String infoColour) {
        this.bName = bName;
        this.username = username;
        this.colour = colour;
        this.message = message;
        this.messageColour = messageColour;
        this.pictureData = pictureData;
        this.pictureURL = pictureURL;
        this.infoMessage = infoMessage;
        this.infoColour = infoColour;
    }

    /**
     * @return the name of the billboard
     */
    public String getbName() {return this.bName;}
    /**
     * @param bName the name of the billboard to set
     */
    public void setbName(String bName) {this.bName = bName;}

    /**
     * @return the name of the representative of the billboard (Company or individual)
     */
    public String getUsername() {return this.username;}
    /**
     * @param username the name of the billboard creator to set
     */
    public void setUsername(String username) {this.username = username;}

    public String getColour() {return this.colour;}
    public void setColour(String colour) {this.colour = colour;}

    public String getMessage() {return this.message;}
    public void setMessage(String message) {this.message = message;}

    public String getMessageColour() {return this.messageColour;}
    public void setMessageColour(String messageColour) {this.messageColour = messageColour;}

    public String getPictureData() {return this.pictureData;}
    public void setPictureData(String pictureData) {this.pictureData = pictureData;}

    public String getPictureURL() {return this.pictureURL;}
    public void setPictureURL(String PictureURL) {this.pictureURL = PictureURL;}

    public String getInfoMessage() {return this.infoMessage;}
    public void setInfoMessage(String infoMessage) {this.infoMessage = infoMessage;}

    public String getInfoColour() {return this.infoColour;}
    public void setInfoColour(String infoColour) {this.infoColour = infoColour;}


    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     *
     * @param other The other Billboard object to compare against.
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it from
     *            being compared to this object.
     */
    public int compareTo(Billboard other) {
        return this.bName.compareTo(other.bName);
    }


}
