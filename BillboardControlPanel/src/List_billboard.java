import Database.Billboard;
import Server.Client;
import org.xml.sax.SAXException;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import javax.swing.table.*;
import javax.xml.parsers.ParserConfigurationException;

import static Server.Client.getResponse;
import static Server.Client.sendRequest;

class List_billboard extends JFrame {

    private JTable table;
    private String[] columnNames;

    private String curr_billboard;


    int width = 2;
    int height = 3;
    //int[][] array = new int[height][width];


    String[][] dataValues = new String[height][width];

    public static ArrayList<Billboard> receiveBillboardList(String token) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();

        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "listBillboard");
        sendRequest(socket, request);

        HashMap<String, Object> response = getResponse(socket);
        ArrayList<Database.Billboard> billboards = (ArrayList<Database.Billboard>) response.get("billboardList");
//        System.out.println(billboards.get(0).getbName());

        socket.close();
        return billboards;
    }

    public List_billboard(String Token) throws IOException, SAXException, ParserConfigurationException {
        //Send Server a valid session token
        //Server response name arr billboards
        Database.Billboard billboard = new Database.Billboard("Sidd's Billboard", "Sid", "Blue", "hard work", "10", null, "hdgjfhgdlfaldgsfl", "jhefhwldjfggfgwiuefgif", "Black");
        Database.Billboard billboard2 = new Database.Billboard("Jack's Billboard", "Jack", "Blue", "hard work", "10", null, "hdgjfhgdlfaldgsfl", "jhefhwldjfggfgwiuefgif", "Black");
        Database.Billboard billboard3 = new Database.Billboard("John's Billboard", "John", "Blue", "hard work", "10", null, "hdgjfhgdlfaldgsfl", "jhefhwldjfggfgwiuefgif", "Black");

        String user[] = new String[3];

        user[0] = billboard.getUsername();
        user[1] = billboard2.getUsername();
        user[2] = billboard3.getUsername();

        String data[] = new String[3];

        data[0] = billboard.getbName();
        data[1] = billboard2.getbName();
        data[2] = billboard3.getbName();


        columnNames = new String[]{" User ", " Billboard "};
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 0) {
                    dataValues[i][j] = user[i];
                }

                if (j == 1) {
                    dataValues[i][j] = data[i];
                }
            }
        }


        TableModel model = new myTableModel();

        table = new JTable();

        table.setRowHeight(50);

        table.setModel(model);

        JButton Delete = new JButton();
        Delete.setText("Delete");

        JButton Edit = new JButton();
        Edit.setText("Edit");

        JButton Preview = new JButton();
        Preview.setText("Preview");

        table.addMouseListener(new java.awt.event.MouseAdapter() {

                                   public void mouseClicked(java.awt.event.MouseEvent e) {

                                       int row = table.rowAtPoint(e.getPoint());


                                       int col = table.columnAtPoint(e.getPoint());

                                       curr_billboard = table.getValueAt(row, col).toString();
                                       System.out.println(curr_billboard);

                                       Delete.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
//                                              if the user has permission to delete
                                               JOptionPane.showMessageDialog(null, "Confirm to delete " + curr_billboard);
                                               ((DefaultTableModel) table.getModel()).removeRow(row);
//                                             Send server billboard_name and token
                                           }
                                       });

                                       Edit.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
//                                      Send server billboard_name and token
//                                      response will be a billboard

                                               String token = Token;
                                               JFrame frame = new Edit_billboard("Edit Billboard",token,billboard);
                                               frame.setLocation(500,300);
                                               frame.setSize(550,550);
                                               frame.setVisible(true);
                                           }
                                       });


                                   }

                               }

        );


        JLabel label = new JLabel("Choose any billboard");
        JPanel panel = new JPanel();
        panel.add(label);

        JPanel panel_2 = new JPanel();
        panel_2.add(Edit, BorderLayout.LINE_START);
        panel_2.add(Preview, BorderLayout.CENTER);
        panel_2.add(Delete, BorderLayout.LINE_END);

        JFrame frame = new JFrame();
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panel_2, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


    }

    public class myTableModel extends DefaultTableModel {

        myTableModel() {

            super(dataValues, columnNames);


        }

        public boolean isCellEditable(int row, int cols) {

            return false;

        }

    }

    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {

        String token= "1342141";
        new List_billboard(token);

    }

}