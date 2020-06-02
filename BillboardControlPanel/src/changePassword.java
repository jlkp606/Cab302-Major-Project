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

public class changePassword extends JFrame {

    private JButton confirmButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel mainPanel;
    private JTextField CreateBillboard;
    private JTextField EditAllBillboard;
    private JTextField EditUsers;
    private JTextField ScheduleBillboard;
    String userName;
    String password;
    public changePassword(String title,String token, String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        Database.Permissions permissions = List_billboard.GetUserPermission( token,  user);

        CreateBillboard.setEnabled(false);
        EditAllBillboard.setEnabled(false);
        ScheduleBillboard.setEnabled(false);
        EditUsers.setEnabled(false);

        if (permissions.getCreateBillboard().equals("true")) {
            CreateBillboard.setText("Permitted");
        } else {
            CreateBillboard.setText("Not Permitted");
        }
        if (permissions.getEditAllBillboards().equals("true")) {
            EditAllBillboard.setText("Permitted");
        } else {
            EditAllBillboard.setText("Not Permitted");
        }
        if (permissions.getEditSchedule().equals("true")) {
            ScheduleBillboard.setText("Permitted");
        } else {
            ScheduleBillboard.setText("Not Permitted");
        }
        if (permissions.getEditUsers().equals("true")) {
            EditUsers.setText("Permitted");
        } else {
            EditUsers.setText("Not Permitted");
        }

        textField1.setText(user);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName=textField1.getText();
                try {
                    password = getHash(passwordField1.getText());
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String userName = "Sid";
        String token = "Sid";
        JFrame frame = new changePassword("Change Password",token,userName);
        frame.setLocation(500, 300);
        frame.setSize(350, 350);
        frame.setVisible(true);
    }

//    public static void TestSetUserPassword(String token,String user, String hashedPassword) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
//        //not working
//        Socket socket = Client.getClientSocket();
//
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "setUserPassword");
//        request.put("username", user);
//        request.put("password", hashedPassword);
//        sendRequest(socket, request);
//        socket.close();
//    }
}
