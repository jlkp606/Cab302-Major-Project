import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



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
    public CreateUser(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(SignUp);
        this.pack();
        String[] permissions = {"No","No","No","No"};


        // Adding action listeners to the following buttons:

        //1. Clicking on create billboard checkbox, will give the user create permission
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
        //2. Clicking on edit all billboard checkbox, will give the user Edit all billboard permission
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

        //3. Clicking on schedule billboard checkbox, will give the user schedule billboard permission
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

        //4. Clicking on edit user checkbox, will give the user Edit user permission
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

        // Clicking on the Ok button will take all the details provided and will be stored in a db
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


//                HashMap<String, Object> request = new HashMap<>();
//                request.put( "type","createUser");
//                request.put("username", User_Name);
//                request.put("password", User_Password);
//                request.put("permissions", permissions);
////                request.put("permissionList", Permission);
//                try {
//                    Socket socket = Client.getClientSocket();
//                    Client.sendRequest(socket, request);
//
//                    HashMap<String, Object> response = Client.getResponse(socket);
//                    response.get("message");
//                    String message =  (String) response.get("message");
//                } catch (IOException | ClassNotFoundException ioException) {
//                    ioException.printStackTrace();
//                }


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

