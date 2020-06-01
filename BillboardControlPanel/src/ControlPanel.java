import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

                String token = "12435642";
                String user = "Sid";
                JFrame frame = null;
                try {
                    frame = new Createbillboard("Create Billboard",token,user);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                String token= "1342141";
                try {
                    new List_billboard(token);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                }

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
