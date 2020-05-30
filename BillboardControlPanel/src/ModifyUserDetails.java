import javax.swing.*;

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

    }



    public static void main(String[] args) {


        JFrame frame = new ModifyUserDetails("Modify Details");
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
        //title_info.getText();
    }
}
