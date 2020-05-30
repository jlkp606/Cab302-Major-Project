import javax.swing.*;

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
    }

    public static void main(String[] args) {


        JFrame frame = new EditUser("Edit User");
        frame.setLocation(500, 300);
        frame.setSize(550, 550);
        frame.setVisible(true);
        //title_info.getText();
    }
}