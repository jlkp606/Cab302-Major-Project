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


//create user class
public class CreateUser extends JFrame {
    private JPanel SignUp;
    private JCheckBox editAllBillboards;
    private JCheckBox createBillboards;
    private JCheckBox scheduleBillboards;
    private JCheckBox editUsers;
    private JPasswordField EnterPassword;
    private JButton OKButton;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField EnterName;

    //create user constructor
    public CreateUser(String title,String token) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(SignUp);
        this.pack();
        String[] permissions = {"false","false","false","false"};


        // Adding action listeners to the following buttons:

        //1. Clicking on create billboard checkbox, will give the user create permission
        createBillboards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            permissions[0] = "true";
                        }
                    }

                });
            }
        });


        //2. Clicking on edit all billboard checkbox, will give the user Edit all billboard permission
        editAllBillboards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            permissions[1] = "true";
                        }
                    }
                });
            }
        });

        //3. Clicking on schedule billboard checkbox, will give the user schedule billboard permission
        scheduleBillboards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            permissions[2] = "true";
                        }
                    }

                });
            }
        });

        //4. Clicking on edit user checkbox, will give the user Edit user permission
        editUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            permissions[3] = "true";
                        }
                    }
                });
            }
        });


        // Clicking on the Ok button will take all the details provided and will be stored in a db
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String User_Name = EnterName.getText();
                String User_Password = EnterPassword.getText();
                if(User_Name.equals("")||User_Password.equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter a valid username or password ");

                }

                else {
                    try {
                        String Hashed_password = getHash(User_Password);
                        Database.Permissions permission = new Database.Permissions(User_Name, permissions[0], permissions[1], permissions[2], permissions[3]);
                        CreateUser(token, User_Name, Hashed_password, permission);

                    } catch (NoSuchAlgorithmException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        });


    }

    public static void CreateUser(String token,String user, String hashedPassword, Database.Permissions permission ) throws IOException, NoSuchAlgorithmException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("type", "createUser");
        request.put("token", token);
        request.put("username",user);
        request.put("password", hashedPassword);
        request.put("permission", permission);
        sendRequest(socket, request);
        socket.close();

    }

}

