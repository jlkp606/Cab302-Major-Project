package Database;

import java.util.Set;

public interface DatabaseSource {

    /**
     * Adds a billboard to the billboard table, if it is not already in the table
     *
     * @param u  Billboard to add
     */
    void addUser(DataBase.User u);

    /**
     * Extracts all the details of a Billboard from the table based on the
     * name passed in.
     *
     * @param username The name of the billboard as a String to search for.
     * @return all details in a Person object for the name
     */
    DataBase.User getUser(String username);

    /**
     * Deletes a USER from the database.
     *
     * @param name The name to delete from the table.
     */
    void deleteUser(String name);

    /**
     * Adds a billboard to the billboard table, if it is not already in the table
     *
     * @param b  Billboard to add
     */
    void addBillboard(Billboard b);

    /**
     * Extracts all the details of a Billboard from the table based on the
     * name passed in.
     *
     * @param bName The name of the billboard as a String to search for.
     * @return all details in a Person object for the name
     */
    Billboard getBillboard(String bName);

    /**
     * Gets the number of addresses in the address book.
     *
     * @return size of address book.
     */
    int getSize();

    /**
     * Retrieves a set of names of billboards from the data source that are used in
     * the name list.
     *
     * @return set of names.
     */
    Set<String> nameSet();
}



