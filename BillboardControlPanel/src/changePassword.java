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

public class changePassword extends JFrame {

    private JButton confirmButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel mainPanel;
    private JPasswordField passwordField2;
    String userName;
    String password;
    String password1;
    public changePassword(String title,String token, String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();


        textField1.setText(user);
        textField1.setEnabled(false);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName=textField1.getText();
                password = passwordField1.getText();
                password1 = passwordField2.getText();

                if(password.equals(password1)){
                    try {
                        password = getHash(passwordField1.getText());
                        SetUserPassword(token,userName,password);
                        //Testing without Server
//                    System.out.println("Sent to Server");
                    } catch (NoSuchAlgorithmException | ClassNotFoundException | IOException ex) {
                        ex.printStackTrace();
                    }
                }

                else{

                    JOptionPane.showMessageDialog(null, " Password Mismatch ");

                }

            }
        });
    }

//    Testing without Server
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String userName = "Sid";
//        String token = "Sid";
//        JFrame frame = new changePassword("Change Password",token,userName);
//        frame.setLocation(500, 300);
//        frame.setSize(350, 350);
//        frame.setVisible(true);
//    }

    public static void SetUserPassword(String token,String userName,String hashedPassword) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        //not working
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "setUserPassword");
        request.put("username", userName);
        request.put("password", hashedPassword);
        sendRequest(socket, request);
        socket.close();
    }
}
