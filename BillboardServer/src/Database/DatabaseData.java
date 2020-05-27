package Database;

import javax.swing.*;

public class DatabaseData {

    DefaultListModel listModel;

    DatabaseSource billboardData;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     */
    public DatabaseData() {
        listModel = new DefaultListModel();
        billboardData = new JDBCDatabaseSource();

        // add the retrieved data to the list model
        for (String name : billboardData.nameSet()) {
            listModel.addElement(name);
        }
    }

    /**
     * Adds a Billboard to the table.
     *
     * @param b A Billboard to add to the database.
     */
    public void add(Billboard b) {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(b.getbName())) {
            listModel.addElement(b.getbName());
            billboardData.addBillboard(b);
        }
    }

    /**
     * Retrieves Person details from the model.
     *
     * @param key the name to retrieve.
     * @return the Person object related to the name.
     */
    public Billboard get(Object key) {
        return billboardData.getBillboard((String) key);
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
     * @return the number of names in the Address Book.
     */
    public int getSize() {
        return billboardData.getSize();
    }
}

