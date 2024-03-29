package Database;

import java.security.Permission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public interface DatabaseSource {

    /**
     * Adds a billboard to the billboard table, if it is not already in the table
     *
     * @param u  Billboard to add
     */
    void addUser(User u) throws SQLException;

    /**
     * Extracts all the details of a Billboard from the table based on the
     * name passed in.
     *
     * @param username The name of the billboard as a String to search for.
     * @return all details in a Person object for the name
     */
    User getUser(String username) throws SQLException;

    /**
     * Updates a USER's password
     *
     * @param name The user of the new password.
     * @param newPassword The new password to replace the old one.
     */
    void setUserPassword(String name, String newPassword) throws SQLException;

    /**
     * Deletes a USER from the database.
     *
     * @param name The name to delete from the table.
     */
    void deleteUser(String name) throws SQLException;

    /**
     * Adds a billboard to the billboard table, if it is not already in the table
     *
     * @param b  Billboard to add
     */
    void addBillboard(Billboard b) throws SQLException;

    /**
     * Gets all the billboards
     *
     * @return ArrayList<Billboard>
     * @throws SQLException
     */
    ArrayList<Billboard> getAllBillboards() throws SQLException;

    /**
     * Extracts all the details of a Billboard from the table based on the
     * name passed in.
     *
     * @param bName The name of the billboard as a String to search for.
     * @return all details in a Person object for the name
     */
    Billboard getBillboard(String bName) throws SQLException;

   /**
    * Deletes a billboard from the database
    *
    * @param billboardName The name of the billboard to delete
    */
    void deleteBillboard(String billboardName) throws SQLException;

   /**
     * Gets the number of addresses in the address book.
     *
     * @return size of address book.
     */
    int getSize() throws SQLException;

    /**
     * Retrieves a set of names of billboards from the data source that are used in
     * the name list.
     *
     * @return set of names.
     */
    Set<String> nameSet() throws SQLException;

    /**
     * Adds permissions to a specific user
     *
     * @param username User to add permissions to.
     * @param permissions Permissions Object.
     */
    void addUserPerms(String username, Permissions permissions) throws SQLException;

    /**
     * updates User permissions
     *
     * @param username Uer to change permissions to
     * @param permission PermissionObject
     * */
    void updateUserPerms(String username, Permissions permission) throws SQLException;

    /**
     * Deletes a permissions row from the database.
     *
     * @param username The name of the user with permissions to be deleted to delete from the table.
     */
    void deletePerms(String username) throws SQLException;

    /**
     * Grabs permission object with username
     *
     * @param username The name of the user with permissions to get
     */
    Permissions getUserPerms(String username) throws SQLException;

    /**
     * Returns all usernames in the table USERS
     */
    ArrayList<String> getUsernames() throws SQLException;

   /**
     * Adds a schedule to a billboard
     *
     * @param schedule Schedule Object;
     */
    void addSchedule(Schedule schedule) throws SQLException;

    /**
     * Deletes a schedule from the database
     *
     * @param schedule The Schedule Object that would be deleted.
     */
    void deleteSchedule(Schedule schedule) throws SQLException;

    /**
     * Extracts all the details of a Schedule from the table based on the
     * billboard name passed in.
     *
     * @param billboardName The name of the billboard as a String to search for.
     * @return all details in a Schedule object for the name
     */
    Schedule getSchedule(String billboardName) throws SQLException;

   /**
    * Returns all schedules in the table SCHEDULES
    */
    ArrayList<Schedule> getAllSchedules() throws SQLException;
}



