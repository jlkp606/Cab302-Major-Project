import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import Server.Client;


public class CreateUser extends JFrame {
    private JPanel SignUp;
    private JCheckBox editAllBillboards;
    private JCheckBox createBillboards;
    private JCheckBox scheduleBillboards;
    private JCheckBox editUsers;
    private JPasswordField EnterPassword;
    private JButton OKButton;
    private JFormattedTextField EnterName;

    public CreateUser(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(SignUp);
        this.pack();
        String[] permissions = {"No","No","No","No"};



        createBillboards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            permissions[0] = "Yes";
                        }
//


                    }

                });
            }

        });

        editAllBillboards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//

                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            permissions[1] = "Yes";
                        }


                    }

                });

            }
        });

        scheduleBillboards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//

                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            permissions[2] = "Yes";
                        }
//


                    }

                });
            }
        });

        editUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//
                        if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                            permissions[3] = "Yes";
                        }


                    }

                });
            }
        });


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String User_Name = EnterName.getText();
                String User_Password = EnterPassword.getText();


                System.out.println(User_Name);
                System.out.println(User_Password);
                for (int i = 0; i < permissions.length; i++) {
                    System.out.println(permissions[i]);
                }


                HashMap<String, Object> request = new HashMap<>();
                request.put( "type","createUser");
                request.put("username", User_Name);
                request.put("password", User_Password);
                request.put("permissions", permissions);
//                request.put("permissionList", Permission);
                try {
                    Socket socket = Client.getClientSocket();
                    Client.sendRequest(socket, request);

                    HashMap<String, Object> response = Client.getResponse(socket);
                    response.get("message");
                    String message =  (String) response.get("message");
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }


            }

        });


    }


    public static void main(String[] args) {


        JFrame frame = new CreateUser("Create User");
        frame.setLocation(500, 300);
        frame.setSize(550, 550);
        frame.setVisible(true);
    }
}

