package Database;

import javax.swing.*;
import java.util.ArrayList;

public class DatabaseData {

    DefaultListModel listModel;

    DatabaseSource databaseData;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     */
    public DatabaseData() {
        listModel = new DefaultListModel();
        databaseData = new JDBCDatabaseSource();

        // add the retrieved data to the list model
        for (String name : databaseData.nameSet()) {
            listModel.addElement(name);
        }
    }

    /**
     * Adds a User to the USER database
     *
     * @param u A user to add to the database
     */
    public void UserAdd(User u)  {

        // check to see if the person is already in the table
        // if not add the user to the table and the list model
        if (!listModel.contains(u.getUsername())) {
            listModel.addElement(u.getUsername());
            databaseData.addUser(u);
        }
    }

    /**
     * Adds a Billboard to the table.
     *
     * @param b A Billboard to add to the database.
     */
    public void BillboardAdd(Billboard b) {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(b.getbName())) {
            listModel.addElement(b.getbName());
            databaseData.addBillboard(b);
        }
    }

    /**
     * Retrieves Billboard details from the model.
     *
     * @param key the name to retrieve.
     * @return the Person object related to the name.
     */
    public Billboard get(Object key) {
        return databaseData.getBillboard((String) key);
    }

    /**
     * Accessor for the list model.
     *
     * @return the listModel to display.
     */
    public ListModel getModel() {
        return listModel;
    }

    /**
     * @return the number of billboards in the table.
     */
    public int getSize() {
        return databaseData.getSize();
    }

    /**
     * Adds permissions with a username attached to the table.
     *
     * @param username A User to add perms to.
     * @param permissionList
     */
    public void addUserPerms(String username, ArrayList<String> permissionList) {

            databaseData.addUserPerms(username, permissionList);
    }

    /**
     * Deletes a permissions row from the database.
     *
     * @param name The name of the user with permissions to be deleted to delete from the table.
     */
    public void deletePerms(String name) {};

}

