import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;


public class CreateUser extends JFrame{
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

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String User_Name = EnterName.getText();

                System.out.println(User_Name);
                ArrayList<String> User_Info = new ArrayList<String>();
                User_Info.add(User_Name);
                System.out.println(User_Info);

            }

        });

        editUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OKButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String User_Name = EnterName.getText();

                        System.out.println(User_Name);

                    }
                });

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

