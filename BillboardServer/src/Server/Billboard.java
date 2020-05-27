package Server;

import java.io.Serializable;

/**
 * Stores details for a billboard
 */
public class Billboard implements Comparable<Billboard>, Serializable {

    private String bName;
    private String bRep;
    private String bData;

    /**
     * No args constructor
     */
    public Billboard() {
    }

    /**
     * Constructor to set values for the billboard details
     * @param bName
     * @param bRep
     * @param bData
     */

    public Billboard(String bName, String bRep, String bData) {
        this.bName = bName;
        this.bRep = bRep;
        this.bData = bData;
    }

    /**
     * @return the name of the billboard
     */
    public String getbName() {return bName;}
    /**
     * @param bName the name of the billboard to set
     */
    public void setbName(String bName) {this.bName = bName;}

    /**
     * @return the name of the representative of the billboard (Company or individual)
     */
    public String getbRep() {return bRep;}
    /**
     * @param bRep the name of the billboard representative to set
     */
    public void setbRep(String bRep) {this.bRep = bRep;}

    /**
     * @return the display data of the billboard
     */
    public String getbData() {return bData;}
    /**
     * @param bData the display information about the billboard
     */
    public void setbData(String bData) {this.bName = bData;}

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

    /**
     * @see Object#toString()
     */
    public String toString() {
        return bName + " " + bRep + ", " + bName;
    }


}
