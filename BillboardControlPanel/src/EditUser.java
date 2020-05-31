import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Edit User class
public class EditUser extends JFrame {
    private JList ListOfExistingUser;
    private JButton modifyUserButton;
    private JButton deleteUserButton;
    private JButton createUserButton;
    private JPanel Heading;

    //edit user constructor
    public EditUser(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Heading);
        this.pack();

        //adding action listeners

        //1. Clicking on Modify Button will take you to Modify User GUI
        modifyUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new ModifyUserDetails("Modify User Details");
                frame.setLocation(500,300);
                frame.setSize(550,550);
                frame.setVisible(true);
            }
        });

        //2. Clicking on create billboard checkbox, will take you to Create User GUI
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new CreateUser("Create User");
                frame.setLocation(500,300);
                frame.setSize(550,550);
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {


        JFrame frame = new EditUser("Edit User");
        frame.setLocation(500, 300);
        frame.setSize(550, 550);
        frame.setVisible(true);
        //title_info.getText();
    }
}