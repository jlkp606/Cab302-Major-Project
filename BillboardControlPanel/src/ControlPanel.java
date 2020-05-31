import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JFrame{
    private JPanel ControlPanel;
    private JButton createNewBillboardButton;
    private JButton scheduleBillboardButton;
    private JButton editUserButton;
    private JButton modifyExistingBillboardButton;
    private String token;

    public ControlPanel(String title, String token) {
        super(title);
        this.token = token;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(ControlPanel);
        this.pack();

        createNewBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new Createbillboard("Create Billboard", "token", "Username");
                frame.setLocation(500,300);
                frame.setSize(550,550);
                frame.setVisible(true);
            }
        });

        modifyExistingBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new EditUser("Edit User");
                frame.setLocation(500,300);
                frame.setSize(550,550);
                frame.setVisible(true);
            }
        });

    }



    public void main(String[] args) {


        JFrame frame = new ControlPanel("Control Panel", this.token);
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
        //title_info.getText();
    }

}
