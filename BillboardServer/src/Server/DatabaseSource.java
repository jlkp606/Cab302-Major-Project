package Server;

import java.util.Set;

public interface DatabaseSource {

    /**
     * Adds a billboard to the billboard table, if it is not already in the table
     *
     * @param b  Billboard to add
     */
    void addBillboard(Billboard b);
}



