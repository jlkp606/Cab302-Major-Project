import javax.swing.*;
import java.io.IOException;

public class viewPermissions extends JFrame{
    private JLabel EditAllBillboard;
    private JLabel EditUsers;
    private JLabel ScheduleBillboard;
    private JLabel CreateBillboard;
    private JPanel mainPanel;

    public  viewPermissions(String title,String token ,String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        Database.Permissions permissions = List_billboard.GetUserPermission( token,  user);



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
}
