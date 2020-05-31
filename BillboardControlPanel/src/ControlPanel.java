import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JFrame{
    private JPanel ControlPanel;
    private JButton createNewBillboardButton;
    private JButton scheduleBillboardButton;
    private JButton editUserButton;
    private JButton modifyExistingBillboardButton;

    public ControlPanel(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(ControlPanel);
        this.pack();

        createNewBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new Createbillboard("Create Billboard");
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

    public static void main(String[] args) {


        JFrame frame = new ControlPanel("Control Panel");
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
        //title_info.getText();
    }

}
