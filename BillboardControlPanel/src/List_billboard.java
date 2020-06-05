import Database.Billboard;
import Database.Permissions;
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
    private String curr_user;
    int width = 2;
    int height = 300;
    String[][] dataValues = new String[height][width];

    public static ArrayList<Billboard> receiveBillboardList(String token) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "listBillboard");
        sendRequest(socket, request);
        HashMap<String, Object> response = getResponse(socket);
        ArrayList<Database.Billboard> billboards = (ArrayList<Database.Billboard>) response.get("billboardList");
        socket.close();
        return billboards;
    }


    public static Permissions GetUserPermission(String token, String user) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "getUserPermissions");
        request.put("username", user);
        sendRequest(socket, request);
        HashMap<String, Object> response = getResponse(socket);
        Database.Permissions permissions = (Database.Permissions) response.get("permissions");
        socket.close();
        return permissions;
    }

    public static void DeleteBillboard(String token, String billboardName) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "deleteBillboard");
        request.put("billboardName", billboardName);
        sendRequest(socket, request);
        socket.close();
    }

    public static Billboard GetBillboardInfo(String token, String billboardName) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "getBillboardInfo");
        request.put("billboardName", billboardName);
        sendRequest(socket, request);
        HashMap<String, Object> response = getResponse(socket);
        Billboard billboard = (Billboard) response.get("billboard");
        socket.close();
        return billboard;
    }

    public static Billboard GetCurrentBillboard(String token) throws IOException, ClassNotFoundException{

        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "getCurrentBillboard");
        sendRequest(socket, request);
        HashMap<String, Object> response = getResponse(socket);
        Billboard billboard = (Billboard) response.get("billboard");
        socket.close();
        return billboard;
    }

    public List_billboard(String token, String user) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException {
//      Uncomment the lines for server testing
      ArrayList<Database.Billboard> billboards = receiveBillboardList(token);

//      comment the lines for server testing
//        Billboard billboard1 = new Billboard("itsmeMario8Billboard", "itsmeMario8", "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                "more information.", "#00FFFF");
//
//        Billboard billboard2 = new Billboard("SidBillboard", "Sid", "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                "more information.", "#00FFFF");
//
//        Billboard billboard3 = new Billboard("JoshBillboard", "Josh", "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                "more information.", "#00FFFF");
//
//        Billboard billboard4 = new Billboard("LiamBillboard", "Liam", "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                "more information.", "#00FFFF");
//        ArrayList<Database.Billboard> billboards = new ArrayList<Database.Billboard>();
//        billboards.add(billboard1);
//        billboards.add(billboard2);
//        billboards.add(billboard3);
//        billboards.add(billboard4);

        int size = billboards.size();
        String user_array[] = new String[size];
        String data[] = new String[size];
        for (int i = 0; i < size; i++) {
            data[i] = billboards.get(i).getbName();
            user_array[i] = billboards.get(i).getUsername();
        }


        columnNames = new String[]{" User ", " Billboard "};
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 0) {
                    dataValues[i][j] = user_array[i];
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
//               int col = table.columnAtPoint(e.getPoint());

               curr_billboard = table.getValueAt(row, 1).toString();
               curr_user= table.getValueAt(row, 0).toString();
               System.out.println(curr_billboard);
               Permissions permissions = null;
               try {
                   permissions = GetUserPermission(token, user);
               } catch (IOException | ClassNotFoundException ex) {
                   ex.printStackTrace();
               }
//               Database.Permissions permissions = new Permissions(user,"true","true","true","true");
               Permissions finalPermissions = permissions;
               Delete.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       try {
                           Billboard billboard = GetBillboardInfo(token, curr_billboard);
                           Billboard curr_scheduled_billboard = GetCurrentBillboard(token);
                           if ((billboard.getUsername().equals(user)) &&  !(curr_scheduled_billboard.getbName().equals(curr_billboard)) ) {
                               if ((finalPermissions.getCreateBillboard().equals("true"))) {
                                   JOptionPane.showMessageDialog(null, "Confirm to delete " + curr_billboard);
                                   try {
                                       DeleteBillboard(token, curr_billboard);
                                       ((DefaultTableModel) table.getModel()).removeRow(row);
                                   } catch (IOException | ClassNotFoundException ex) {
                                       ex.printStackTrace();
                                   }

                               }
                               else {
                                   JOptionPane.showMessageDialog(null, "Permission required ");
                               }
                           }
                           else{
                               if ((finalPermissions.getEditAllBillboards().equals("true"))) {
                                   JOptionPane.showMessageDialog(null, "Confirm to delete " + curr_billboard);
                                   try {
                                       DeleteBillboard(token, curr_billboard);
                                   } catch (IOException | ClassNotFoundException ex) {
                                       ex.printStackTrace();
                                   }
                               }
                               else {
                                   JOptionPane.showMessageDialog(null, "Permission required ");
                               }
                           }
                       }
                       catch (IOException | ClassNotFoundException ex) {
                           ex.printStackTrace();
                       }

                   }
               });

               Permissions finalPermissions1 = permissions;
               Edit.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       try {
                       Billboard billboard = GetBillboardInfo(token, curr_billboard);
//                       Billboard billboard = new Billboard(curr_billboard, "Sid" , "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                               "more information.", "#00FFFF");
                           Billboard curr_scheduled_billboard = GetCurrentBillboard(token);

//                       Billboard curr_scheduled_billboard = new Billboard(curr_billboard, "Siddgfdghd" , "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                               "more information.", "#00FFFF");
                           if ((billboard.getUsername().equals(user)) &&  !(curr_scheduled_billboard.getbName().equals(curr_billboard)) ) {
                               if ((finalPermissions1.getCreateBillboard().equals("true"))) {
                                   SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {
                                           JFrame frame = new Edit_billboard("Edit Billboard", token, billboard);
                                           frame.setLocation(500, 300);
                                           frame.setSize(550, 550);
                                           frame.setVisible(true);
                                       }
                                   });
                               }
                               else {
                                   JOptionPane.showMessageDialog(null, "Permission required ");
                               }
                           }
                           else{
                               if ((finalPermissions1.getEditAllBillboards().equals("true"))) {
                                   SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {
                                           JFrame frame = new Edit_billboard("Edit Billboard", token, billboard);
                                           frame.setLocation(500, 300);
                                           frame.setSize(550, 550);
                                           frame.setVisible(true);
                                       }
                                   });
                               }
                               else {
                                   JOptionPane.showMessageDialog(null, "Permission required ");
                               }
                           }

                       }

                       catch (IOException | ClassNotFoundException ex) {
                           ex.printStackTrace();
                       }

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
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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


}