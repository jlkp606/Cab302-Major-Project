import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Control Panel class

public class ControlPanel extends JFrame{
    //Adding following panel, buttons to the GUI
    private JPanel ControlPanel;
    private JButton createNewBillboardButton;
    private JButton scheduleBillboardButton;
    private JButton editUserButton;
    private JButton modifyExistingBillboardButton;

    //Constructor
    public ControlPanel(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // GUI window will close after Exist button on the top is pressed
        this.setContentPane(ControlPanel);
        this.pack(); // will pack the GUI buttons etc.

        // Adding action listeners to the following buttons:

        //1. Clicking on create new billboard button, will take you to the create billboard GUI
        createNewBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new CreateUser("Create User");
                frame.setLocation(500, 300);
                frame.setSize(550, 550);
                frame.setVisible(true);

            }
        });

        //2. Clicking on edit User button, will take you to the create user GUI
        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new EditUser("Edit User");
                frame.setLocation(500, 300);
                frame.setSize(550, 550);
                frame.setVisible(true);

            }
        });

        //3. Clicking on Schedule Billboard button, will take you to the Schedule billboard GUI
        scheduleBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new viewer_billboard_time("Schedule Billboard");
                frame.setLocation(500, 300);
                frame.setSize(550, 550);
                frame.setVisible(true);

            }
        });

        //4. Clicking on modifying existing billboard, will take you to Edit Billboard GUI
        modifyExistingBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JFrame frame = new Edit_billboard("Edit Billboard",  );
//                frame.setLocation(500, 300);
//                frame.setSize(550, 550);
//                frame.setVisible(true);

            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new ControlPanel("Control Panel");
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
    }

}
