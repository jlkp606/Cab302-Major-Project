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
                        String serverResponse = SetUserPassword(token,userName,password);
                        if(serverResponse.equals("Success")){
                            CloseJframe();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, serverResponse);
                        }

                    } catch (NoSuchAlgorithmException | ClassNotFoundException | IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, " Failed to connect to server ");

                    }
                }

                else{

                    JOptionPane.showMessageDialog(null, " Password Mismatch ");

                }

            }
        });
    }


    public static String SetUserPassword(String token, String userName, String hashedPassword) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        //not working
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "setUserPassword");
        request.put("username", userName);
        request.put("password", hashedPassword);
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
