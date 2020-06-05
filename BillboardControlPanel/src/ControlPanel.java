import Database.Permissions;
import Server.Client;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static Server.Client.sendRequest;

// Control Panel class

public class ControlPanel extends JFrame{
    //Adding following panel, buttons to the GUI
    private JPanel ControlPanel;
    private JButton createNewBillboardButton;
    private JButton scheduleBillboardButton;
    private JButton editUserButton;
    private JButton modifyExistingBillboardButton;
    private JButton logoutButton;
    private JButton changePassword;
    private JButton viewPermissionsButton;

    //Constructor
    public ControlPanel(String title,String token,String user) throws IOException, ClassNotFoundException {
//        JFrame frame = new ControlPanel("Control Panel",token,user);
//        frame.setLocation(500, 300);
//        frame.setSize(550, 550);
//        frame.setVisible(true);
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // GUI window will close after Exist button on the top is pressed
        this.setContentPane(ControlPanel);
        this.pack(); // will pack the GUI buttons etc.

        // Adding action listeners to the following buttons:



//        Permissions permissions = List_billboard.GetUserPermission(token, user);

        Permissions permissions = new Permissions("Sid","true","true","true","true");


        //1. Clicking on create new billboard button, will take you to the create billboard GUI
        createNewBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (permissions.getCreateBillboard().equals("true")) {

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {

                                JFrame frame = new Createbillboard("Create Billboard", token, user);
                                frame.setLocation(500, 300);
                                frame.setSize(550, 550);
                                frame.setVisible(true);
                            } catch (IOException exc) {
                                exc.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                            }
                        }
                    });
                }

                else {
                    JOptionPane.showMessageDialog(null, "Permission required ");
                }


            }
        });


        //2. Clicking on edit User button, will take you to the create user GUI
        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (permissions.getEditUsers().equals("true")) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                new listUsers(" Users", token, user);
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                            }
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Permission required ");
                }

            }
        });

        //3. Clicking on Schedule Billboard button, will take you to the Schedule billboard GUI
        scheduleBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (permissions.getEditSchedule().equals("true")) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                JFrame frame = new viewer_billboard_time(" Users", token, user);
                                frame.setLocation(500, 300);
                                frame.setSize(850, 650);
                                frame.setVisible(true);
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                            }
                        }
                    });
                }
                else{
                    JOptionPane.showMessageDialog(null, "Permission required ");
                }
            }
        });

        //4. Clicking on modifying existing billboard, will take you to Edit Billboard GUI
        modifyExistingBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (permissions.getEditAllBillboards().equals("true")) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                new List_billboard(token, user);
                            } catch (IOException | ParserConfigurationException | ClassNotFoundException | SAXException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                            }
                        }
                    });
                }
                else{
                    JOptionPane.showMessageDialog(null, "Permission required ");
                }

            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LogOut( token );
                    JOptionPane.showMessageDialog(null, "Logged out");
                    MyGui myGuo = new MyGui();
                    CloseJframe();
                }
                catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                }

            }
        });

        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            JFrame frame = new changePassword("Change Password", token, user);
                            frame.setLocation(500, 300);
                            frame.setSize(350, 350);
                            frame.setVisible(true);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                        }
                    }
                });
            }
        });
        viewPermissionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            JFrame frame = new viewPermissions(user + " Permissions", token, user);
                            frame.setLocation(500, 300);
                            frame.setSize(350, 350);
                            frame.setVisible(true);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to Connect to server ");
                        }
                    }
                });
            }
        });
    }

    public void CloseJframe(){
        super.dispose();
    }


    public static void LogOut(String token) throws IOException, ClassNotFoundException{
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "logOut");
        sendRequest(socket, request);
        socket.close();
    }
}
