import Database.Billboard;
import Database.Permissions;
import Server.Client;
import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import javax.imageio.ImageIO;
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

    public static String DeleteBillboard(String token, String billboardName) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "deleteBillboard");
        request.put("billboardName", billboardName);
        sendRequest(socket, request);
        HashMap<String, Object> res = Client.getResponse(socket);
        String message = (String) res.get("message");
        socket.close();
        return message;
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

    public static Billboard GetCurrentBillboard(String token) throws IOException, ClassNotFoundException {

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
        ArrayList<Database.Billboard> billboards = receiveBillboardList(token);

        int size = billboards.size();
        String[] user_array = new String[size];
        String[] data = new String[size];
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

                curr_billboard = table.getValueAt(row, 1).toString();
                curr_user = table.getValueAt(row, 0).toString();
            }
        });


        Permissions permissions = null;
        try {
            permissions = GetUserPermission(token, user);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

        }

        Permissions finalPermissions = permissions;
        Preview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (curr_billboard == null) {

                    JOptionPane.showMessageDialog(null, "Please choose a billboard ");

                }
                else {
                    JFrame BillboardFrame = new JFrame();
                    JPanel BillboardElements = new JPanel();


                    Billboard billboard = null;
                    try {
                        billboard = GetBillboardInfo(token, curr_billboard);
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        BillboardPreview.Preview(billboard);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (SAXException saxException) {
                        saxException.printStackTrace();
                    } catch (ParserConfigurationException parserConfigurationException) {
                        parserConfigurationException.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        noSuchAlgorithmException.printStackTrace();
                    }
                }
            }
        });




                Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                                               if (curr_billboard == null) {

                                                   JOptionPane.showMessageDialog(null, "Please choose a billboard ");

                                               } else {

                                                   try {
                                                       Billboard billboard = GetBillboardInfo(token, curr_billboard);
                                                       Billboard curr_scheduled_billboard = GetCurrentBillboard(token);

                                                       if ((billboard.getUsername().equals(user)) && !(curr_scheduled_billboard.getbName().equals(curr_billboard))) {
                                                           if ((finalPermissions.getCreateBillboard().equals("true"))) {
                                                               SwingUtilities.invokeLater(new Runnable() {
                                                                   public void run() {
                                                                       JFrame frame = new Edit_billboard("Edit Billboard", token, billboard);
                                                                       frame.setLocation(500, 300);
                                                                       frame.setSize(550, 550);
                                                                       frame.setVisible(true);
                                                                   }
                                                               });
                                                           } else {
                                                               JOptionPane.showMessageDialog(null, "Permission required ");
                                                           }
                                                       } else {
                                                           if ((finalPermissions.getEditAllBillboards().equals("true"))) {
                                                               SwingUtilities.invokeLater(new Runnable() {
                                                                   public void run() {
                                                                       JFrame frame = new Edit_billboard("Edit Billboard", token, billboard);
                                                                       frame.setLocation(500, 300);
                                                                       frame.setSize(550, 550);
                                                                       frame.setVisible(true);
                                                                   }
                                                               });
                                                           } else {
                                                               JOptionPane.showMessageDialog(null, "Permission required ");
                                                           }
                                                       }

                                                   } catch (IOException | ClassNotFoundException ex) {
                                                       ex.printStackTrace();
                                                   }

                                               }
                                           }


        });

        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                                               if (curr_billboard == null) {

                                                   JOptionPane.showMessageDialog(null, "Please choose a billboard ");

                                               } else {
                                                   try {
                                                       Billboard billboard = GetBillboardInfo(token, curr_billboard);
                                                       Billboard curr_scheduled_billboard = GetCurrentBillboard(token);
                                                       if (billboard.getUsername() != null) {
                                                           if ((billboard.getUsername().equals(user)) && !(curr_scheduled_billboard.getbName().equals(curr_billboard))) {
                                                               if (finalPermissions != null) {
                                                                   if ((finalPermissions.getCreateBillboard().equals("true"))) {
                                                                       JOptionPane.showMessageDialog(null, "Confirm to delete " + curr_billboard);
                                                                       try {
                                                                           String response = DeleteBillboard(token, curr_billboard);
                                                                           if (response.equals("Sucess")) {
                                                                               JOptionPane.showMessageDialog(null, response);
                                                                           } else {
                                                                               JOptionPane.showMessageDialog(null, response);

                                                                           }
                                                                       } catch (IOException | ClassNotFoundException ex) {
                                                                           ex.printStackTrace();
                                                                           JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                                                                       }

                                                                   } else {
                                                                       JOptionPane.showMessageDialog(null, "Permission required ");
                                                                   }

                                                               } else {
                                                                   if ((finalPermissions.getEditAllBillboards().equals("true"))) {
                                                                       JOptionPane.showMessageDialog(null, "Confirm to delete " + curr_billboard);
                                                                       try {
                                                                           String response = DeleteBillboard(token, curr_billboard);
                                                                           if (response.equals("Sucess")) {
                                                                               JOptionPane.showMessageDialog(null, response);
                                                                           } else {
                                                                               JOptionPane.showMessageDialog(null, response);

                                                                           }
                                                                       } catch (IOException | ClassNotFoundException ex) {
                                                                           ex.printStackTrace();
                                                                           JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                                                                       }
                                                                   } else {
                                                                       JOptionPane.showMessageDialog(null, "Permission required ");
                                                                   }
                                                               }
                                                           }
                                                       }
                                                   } catch (IOException | ClassNotFoundException ex) {
                                                       ex.printStackTrace();
                                                   }
                                               }
                                           }

        });


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