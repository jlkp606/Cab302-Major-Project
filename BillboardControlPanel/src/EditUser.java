import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUser extends JFrame {
    private JList ListOfExistingUser;
    private JButton modifyUserButton;
    private JButton deleteUserButton;
    private JButton createUserButton;
    private JPanel Heading;

    public EditUser(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Heading);
        this.pack();

        modifyUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new ModifyUserDetails("Modify User Details");
                frame.setLocation(500,300);
                frame.setSize(550,550);
                frame.setVisible(true);
            }
        });
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