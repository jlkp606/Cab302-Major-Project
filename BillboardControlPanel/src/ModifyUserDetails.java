import Database.Permissions;
import Server.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

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
        Database.Permissions initialPermision = List_billboard.GetUserPermission(token,user);

        if(initialPermision.getCreateBillboard().equals("true")){
            createBillboardsCheckBox.setSelected(true);
        }

        if(initialPermision.getEditAllBillboards().equals("true")){
            editAllBillboardsCheckBox.setSelected(true);
        }

        if(initialPermision.getEditSchedule().equals("true")){
            scheduleBillboardsCheckBox.setSelected(true);
        }

        if(initialPermision.getEditUsers().equals("true")){
            editUsersCheckBox.setSelected(true);
        }


        // Adding action listeners to the following buttons:

        //1. Clicking on create billboard checkbox, will change the user create permission

        createBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            if(initialPermision.getCreateBillboard().equals("true")){
                                initialPermision.setCreateBillboard("false");
                            }
                            else{
                                initialPermision.setCreateBillboard("true");
                            }
                        }

                    }
                });

        //2. Clicking on edit all billboard checkbox, will change the user Edit all billboard permission

        editAllBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                    if(initialPermision.getEditAllBillboards().equals("true")){
                        initialPermision.setEditAllBillboards("false");
                    }
                    else{
                        initialPermision.setEditAllBillboards("true");
                    }
                }
            }

        });
        //3. Clicking on schedule billboard checkbox, will change the user schedule billboard permission

        scheduleBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                    if(initialPermision.getEditSchedule().equals("true")){
                        initialPermision.setEditSchedule("false");
                    }
                    else{
                        initialPermision.setEditSchedule("true");
                    }
                }
            }
        });

        //4. Clicking on edit user checkbox, will change the user Edit user permission

        editUsersCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(admin.equals(user)){
                    initialPermision.getEditUsers().equals("true");
                    JOptionPane.showMessageDialog(null, "Admin cannot remove his owm edit user permission");
                    editUsersCheckBox.setSelected(true);
                }

                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                    if(initialPermision.getEditUsers().equals("true")){
                        initialPermision.setEditUsers("false");
                    }
                    else{
                        initialPermision.setEditUsers("true");
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

                    try {
                        String Hashed_password = getHash(User_Password);
                        Database.Permissions permission = new Database.Permissions(User_Name, initialPermision.getCreateBillboard(), initialPermision.getEditAllBillboards(), initialPermision.getEditSchedule(), initialPermision.getEditUsers());
                        String response = SetUserPermission( token, user, permission);

                        String serverResponse = changePassword.SetUserPassword(token,user,Hashed_password);
                        if((serverResponse.equals("Success") &&(response.equals("Success")))) {
                            CloseJframe();
                        }
                        else {
                            if(!serverResponse.equals("Success")){
                            JOptionPane.showMessageDialog(null, serverResponse);
                            }
                            else if(!response.equals("Success")) {

                                JOptionPane.showMessageDialog(null, response);
                            }
                        }
                    } catch (NoSuchAlgorithmException | ClassNotFoundException | IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Please enter a valid password");

                    }

                }
            }
        });

    }


    public static String SetUserPermission(String token, String user, Permissions permission) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "setUserPermissions");
        request.put("username", user);
        request.put("permission", permission);
        sendRequest(socket, request);
        HashMap<String , Object> res = Client.getResponse(socket);
        String message = (String) res.get("message");
        socket.close();
        return message;
    }

    public void CloseJframe(){
        super.dispose();
    }
}
