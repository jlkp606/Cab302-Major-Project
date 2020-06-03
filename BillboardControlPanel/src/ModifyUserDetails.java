import Server.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static Server.Client.getResponse;
import static Server.Client.sendRequest;
import static Server.Hash.getHash;


//Modify user Class
public class ModifyUserDetails extends JFrame {
    private JPanel ModifyDetails;
    private JCheckBox createBillboardsCheckBox;
    private JCheckBox editAllBillboardsCheckBox;
    private JCheckBox scheduleBillboardsCheckBox;
    private JCheckBox editUsersCheckBox;
    private JButton applyChangesButton;
    private JPasswordField passwordField1;
    private JLabel enterUserName;
    private JFormattedTextField formattedTextField1;

    //Modify User Constructor
    public ModifyUserDetails(String title,String token,String admin,String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(ModifyDetails);
        this.pack();
        String[] permissions = {"false","false","false","false"};

        formattedTextField1.setEnabled(false);
        formattedTextField1.setText(user);
        Database.Permissions initialPermision = List_billboard.GetUserPermission(token,admin);

//        Database.Permissions initialPermision = new Database.Permissions("sid","true","false","false","true");
//
        permissions[0] = initialPermision.getCreateBillboard();
        permissions[1] = initialPermision.getEditAllBillboards();
        permissions[2] = initialPermision.getEditSchedule();
        permissions[3] = initialPermision.getEditUsers();

        if(permissions[0] == "true"){
            createBillboardsCheckBox.setSelected(true);;
        }

        if(permissions[1] == "true"){
            editAllBillboardsCheckBox.setSelected(true);;
        }

        if(permissions[2] == "true"){
            scheduleBillboardsCheckBox.setSelected(true);;
        }

        if(permissions[3] == "true"){
            editUsersCheckBox.setSelected(true);;
        }


        // Adding action listeners to the following buttons:

        //1. Clicking on create billboard checkbox, will change the user create permission

        createBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            if(permissions[0] == "true"){
                                permissions[0] = "false";
                            }
                            else{
                                permissions[0] = "true";
                            }
                        }

                    }
                });

        //2. Clicking on edit all billboard checkbox, will change the user Edit all billboard permission

        editAllBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                    if(permissions[1] == "true"){
                        permissions[1] = "false";
                    }
                    else{
                        permissions[1] = "true";
                    }
                }
            }

        });
        //3. Clicking on schedule billboard checkbox, will change the user schedule billboard permission

        scheduleBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                    if(permissions[2] == "true"){
                        permissions[2] = "false";
                    }
                    else{
                        permissions[2] = "true";
                    }
                }
            }
        });

        //4. Clicking on edit user checkbox, will change the user Edit user permission

        editUsersCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(admin.equals(user)){
                    permissions[3] = "true";
                    JOptionPane.showMessageDialog(null, "Admin cannot remove his owm edit user permission");
                    editUsersCheckBox.setSelected(true);
                }

                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                    if(permissions[3] == "true"){
                        permissions[3] = "false";
                    }
                    else{
                        permissions[3] = "true";
                    }
                }

            }
        });
        // Clicking on the Apply changes button will change all the details provided and will be stored in a db

        applyChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String User_Name = formattedTextField1.getText();
                String User_Password = passwordField1.getText();
                if(User_Password.equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter a valid password");
                }

                else {
//                    try {
//                    try {

//                        changePassword.SetUserPassword(token,user,Hashed_password);
//                    } catch (NoSuchAlgorithmException ex) {
//                        ex.printStackTrace();
//                    }
                    Database.Permissions permission = new Database.Permissions(User_Name, permissions[0], permissions[1], permissions[2], permissions[3]);
                    System.out.println(permission.getCreateBillboard());
                    System.out.println(permission.getEditAllBillboards());
                    System.out.println(permission.getEditSchedule());
                    System.out.println(permission.getEditUsers());
                    System.out.println("\n");
//
//                    } catch (NoSuchAlgorithmException | ClassNotFoundException | IOException ex) {
//                        ex.printStackTrace();
//                    }

                }
            }
        });

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String user = "Josh";
        String admin = "Sid";
        String token = "ihfoahfio";
        JFrame frame = new ModifyUserDetails("Modify Details",token,admin,user);
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
    }

//    public static Database.Permissions GetUserPermission(String token, String user) throws IOException, ClassNotFoundException {
//        Socket socket = Client.getClientSocket();
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "getUserPermissions");
//        request.put("username", user);
//        sendRequest(socket, request);
//        HashMap<String, Object> response = getResponse(socket);
//        Database.Permissions permissions = (Database.Permissions) response.get("permissions");
//        socket.close();
//        return permissions;
//    }

    public static void SetUserPermission(String token,String user,Database.Permissions permission) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "setUserPermissions");
        request.put("username", user);
        request.put("permission", permission);
        sendRequest(socket, request);
        socket.close();
    }
}
