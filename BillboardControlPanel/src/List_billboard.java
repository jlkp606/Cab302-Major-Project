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

                        JFrame BillboardFrame = new JFrame();
                        JPanel BillboardElements = new JPanel();


                        Billboard billboard = null;
                        try {
                            billboard = GetBillboardInfo(token, curr_billboard);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        JLabel BillboardMessage = new JLabel();
                        JLabel BillboardInformation = new JLabel();

                        BillboardMessage.setText(billboard.getMessage());
                        BillboardInformation.setText(billboard.getInfoMessage());
                        /* ErrorMessage in the case that the class has no background colour input and is invalid*/
                        Color BillboardBackgroundColour = null;
                        try {
                            BillboardBackgroundColour = Color.decode(billboard.getColour());
                            ;
                        } catch (Exception NumberFormatException) {
                            JLabel ErrorMessage = new JLabel("Error: File not found or invalid.");
                            ErrorMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 48));
                            ErrorMessage.setForeground(Color.WHITE);
                            ErrorMessage.setHorizontalAlignment(JLabel.CENTER);
                            BillboardFrame.getContentPane().add(ErrorMessage);
                            BillboardFrame.getContentPane().setBackground(Color.BLUE);
                            BillboardFrame.setUndecorated(true);
                            BillboardFrame.setFocusable(true);
                            BillboardFrame.setVisible(true);
                        }
                        Color BillboardMessageColour = null;
                        Color BillboardInformationColour = null;
                        try {
                            if (!billboard.getMessageColour().equals("")) {
                                BillboardMessageColour = Color.decode(billboard.getMessageColour());
                            }
                        } catch (Exception exp) {
                            System.out.println("not a valid color");
                        }
                        try {
                            if (!billboard.getInfoColour().equals("")) {
                                BillboardInformationColour = Color.decode(billboard.getInfoColour());
                            }
                        } catch (Exception exp) {
                            System.out.println("not a valid color");
                        }
                        BillboardMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 52));
                        BillboardInformation.setFont(new Font("Century Schoolbook", Font.PLAIN, 32));

                        /*setting colours for messages, information and background*/
                        BillboardMessage.setForeground(BillboardMessageColour);
                        BillboardInformation.setForeground(BillboardInformationColour);
                        BillboardElements.setBackground(BillboardBackgroundColour);

                        /*Show all elements present, Base64 image*/
                        if (!billboard.getMessage().equals("") &&
                                !billboard.getInfoMessage().equals("") &&
                                !billboard.getPictureData().equals("")) {
                            byte[] Base64toImage = Base64.getDecoder().decode(billboard.getPictureData());
                            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
                            BufferedImage Base64Image = null;
                            try {
                                Base64Image = ImageIO.read(Base64Stream);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                            /*set Element alignments*/
                            BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                            BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            /*add elements*/
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(BillboardMessage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(ConvertedImage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(BillboardInformation);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show message and Base64 image, no information*/
                        else if (!billboard.getMessage().equals("") && billboard.getInfoMessage().equals("") && !billboard.getPictureData().equals("")) {
                            byte[] Base64toImage = Base64.getDecoder().decode(billboard.getPictureData());
                            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
                            BufferedImage Base64Image = null;
                            try {
                                Base64Image = ImageIO.read(Base64Stream);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                            //set Element alignments
                            BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(BillboardMessage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(ConvertedImage);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show information and Base64 image, no message*/
                        else if (billboard.getMessage().equals("") &&
                                !billboard.getInfoMessage().equals("") &&
                                !billboard.getPictureData().equals("")) {
                            byte[] Base64toImage = Base64.getDecoder().decode(billboard.getPictureData());
                            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
                            BufferedImage Base64Image = null;
                            try {
                                Base64Image = ImageIO.read(Base64Stream);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                            //set Element alignments
                            BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(ConvertedImage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(BillboardInformation);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show all elements present, URL image*/
                        else if (!billboard.getMessage().equals("") &&
                                !billboard.getInfoMessage().equals("") &&
                                !billboard.getPictureURL().equals("")) {
                            java.net.URL BillboardImageURL = null;
                            try {
                                BillboardImageURL = new URL(billboard.getPictureURL());
                            } catch (MalformedURLException ex) {
                                ex.printStackTrace();
                            }
                            BufferedImage URLtoImage = null;
                            try {
                                URLtoImage = ImageIO.read(BillboardImageURL);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                            //set Element alignments
                            BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                            BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(BillboardMessage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(ConvertedImage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(BillboardInformation);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show message and URL Image, no information*/
                        else if (!billboard.getMessage().equals("") &&
                                billboard.getInfoMessage().equals("") &&
                                !billboard.getPictureURL().equals("")) {
                            URL BillboardImageURL = null;
                            try {
                                BillboardImageURL = new URL(billboard.getPictureURL());
                            } catch (MalformedURLException ex) {
                                ex.printStackTrace();
                            }
                            BufferedImage URLtoImage = null;
                            try {
                                URLtoImage = ImageIO.read(BillboardImageURL);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                            //set Element alignments
                            BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(BillboardMessage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(ConvertedImage);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show information and URL Image, no message*/
                        else if (billboard.getMessage().equals("") &&
                                !billboard.getInfoMessage().equals("") &&
                                !billboard.getPictureURL().equals("")) {
                            URL BillboardImageURL = null;
                            try {
                                BillboardImageURL = new URL(billboard.getPictureURL());
                            } catch (MalformedURLException ex) {
                                ex.printStackTrace();
                            }
                            BufferedImage URLtoImage = null;
                            try {
                                URLtoImage = ImageIO.read(BillboardImageURL);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                            //set Element alignments
                            BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(ConvertedImage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(BillboardInformation);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show information and message, no image*/
                        else if (!billboard.getMessage().equals("") && !billboard.getInfoMessage().equals("") &&
                                (billboard.getPictureData().equals("") || billboard.getPictureURL().equals(""))) {

                            //set Element alignments
                            BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                            BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(BillboardMessage);
                            BillboardElements.add(Box.createVerticalGlue());
                            BillboardElements.add(BillboardInformation);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show only message*/
                        else if (!billboard.getMessage().equals("") && billboard.getInfoMessage().equals("") &&
                                (billboard.getPictureData().equals("") || billboard.getPictureURL().equals(""))) {

                            //set Element alignments
                            BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(BillboardMessage);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show only information*/
                        else if (billboard.getMessage().equals("") && !billboard.getInfoMessage().equals("") &&
                                (billboard.getPictureData().equals("") || billboard.getPictureURL().equals(""))) {

                            //set Element alignments
                            BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(BillboardInformation);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show only Base64 Image*/
                        else if (billboard.getMessage().equals("") && billboard.getInfoMessage().equals("") &&
                                !billboard.getPictureData().equals("")) {
                            byte[] Base64toImage = Base64.getDecoder().decode(billboard.getPictureData());
                            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
                            BufferedImage Base64Image = null;
                            try {
                                Base64Image = ImageIO.read(Base64Stream);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                            //set Element alignments
                            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(ConvertedImage);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        }

                        /*Show only URL Image*/
                        else if (billboard.getMessage().equals("") && billboard.getInfoMessage().equals("") &&
                                !billboard.getPictureURL().equals("")) {
                            URL BillboardImageURL = null;
                            try {
                                BillboardImageURL = new URL(billboard.getPictureURL());
                            } catch (MalformedURLException ex) {
                                ex.printStackTrace();
                            }
                            BufferedImage URLtoImage = null;
                            try {
                                assert BillboardImageURL != null;
                                URLtoImage = ImageIO.read(BillboardImageURL);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            assert URLtoImage != null;
                            JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                            //set Element alignments
                            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //add elements
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                            BillboardElements.add(ConvertedImage);
                            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                        } else {
                            JLabel ErrorMessage = new JLabel("Error: File not found or invalid.");
                            ErrorMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 48));
                            ErrorMessage.setForeground(Color.WHITE);
                            ErrorMessage.setHorizontalAlignment(JLabel.CENTER);
                            BillboardFrame.getContentPane().add(ErrorMessage);
                            BillboardFrame.getContentPane().setBackground(Color.BLUE);
                        }
                        /*finally adding all parts to the frame*/

                        BillboardFrame.getContentPane().add(BillboardElements);
                        BillboardFrame.setVisible(true);
                        BillboardFrame.setSize(1000, 1000);



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