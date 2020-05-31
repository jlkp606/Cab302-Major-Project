import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//Modify user Class
public class ModifyUserDetails extends JFrame{
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
    public ModifyUserDetails(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(ModifyDetails);
        this.pack();
        String[] permissions = {"No","No","No","No"};

        // Adding action listeners to the following buttons:

        //1. Clicking on create billboard checkbox, will change the user create permission

        createBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                applyChangesButton.addActionListener(new ActionListener() {
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
        //2. Clicking on edit all billboard checkbox, will change the user Edit all billboard permission

        editAllBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                applyChangesButton.addActionListener(new ActionListener() {
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
        //3. Clicking on schedule billboard checkbox, will change the user schedule billboard permission

        scheduleBillboardsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                applyChangesButton.addActionListener(new ActionListener() {
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
        //4. Clicking on edit user checkbox, will change the user Edit user permission

        editUsersCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                applyChangesButton.addActionListener(new ActionListener() {
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
        // Clicking on the Apply changes button will change all the details provided and will be stored in a db

        applyChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String User_Name = formattedTextField1.getText();
                String User_Password = passwordField1.getText();


                System.out.println(User_Name);
                System.out.println(User_Password);
                for (int i = 0; i < permissions.length; i++) {
                    System.out.println(permissions[i]);
                }
            }
        });

    }



    public static void main(String[] args) {


        JFrame frame = new ModifyUserDetails("Modify Details");
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
        //title_info.getText();
    }
}
