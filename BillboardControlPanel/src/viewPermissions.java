import javax.swing.*;
import java.io.IOException;

public class viewPermissions extends JFrame{
    private JTextField EditAllBillboard;
    private JTextField EditUsers;
    private JTextField ScheduleBillboard;
    private JTextField CreateBillboard;
    private JPanel mainPanel;

    public  viewPermissions(String title,String token ,String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        Database.Permissions permissions = List_billboard.GetUserPermission( token,  user);

        //Testing without Server
//        Database.Permissions permissions = new Database.Permissions("Sid","true","true","true","true");

        CreateBillboard.setEnabled(false);
        EditAllBillboard.setEnabled(false);
        ScheduleBillboard.setEnabled(false);
        EditUsers.setEnabled(false);

        if (permissions.getCreateBillboard().equals("true")) {
            CreateBillboard.setText("Permitted");
        } else {
            CreateBillboard.setText("Not Permitted");
        }
        if (permissions.getEditAllBillboards().equals("true")) {
            EditAllBillboard.setText("Permitted");
        } else {
            EditAllBillboard.setText("Not Permitted");
        }
        if (permissions.getEditSchedule().equals("true")) {
            ScheduleBillboard.setText("Permitted");
        } else {
            ScheduleBillboard.setText("Not Permitted");
        }
        if (permissions.getEditUsers().equals("true")) {
            EditUsers.setText("Permitted");
        } else {
            EditUsers.setText("Not Permitted");
        }
    }
    //    Testing without Server
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String userName = "Sid";
//        String token = "Sid";
//        JFrame frame = new viewPermissions(userName + " Permissions",token,userName);
//        frame.setLocation(500, 300);
//        frame.setSize(350, 350);
//        frame.setVisible(true);
//    }
}
