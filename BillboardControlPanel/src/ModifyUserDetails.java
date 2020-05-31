import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    public ModifyUserDetails(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(ModifyDetails);
        this.pack();
        String[] permissions = {"No","No","No","No"};

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
