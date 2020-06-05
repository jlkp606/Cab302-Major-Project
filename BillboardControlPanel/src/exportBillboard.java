import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class exportBillboard extends JFrame {
    private JTextField textField1;
    private JButton button1;
    private JPanel mainPanel;


    public exportBillboard(String billboard_title, String billboard_title_colour, String billboard_bg_colour, String billboard_message, String billboard_message_colour, String billboard_image_data, String billboard_image_url) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        button1.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                //make an xml file
                try {
                    String xmlFilePath = textField1.getText().trim();
                    CreateXMLFile file = new CreateXMLFile(billboard_title, billboard_title_colour, billboard_bg_colour, billboard_message, billboard_message_colour, billboard_image_data, billboard_image_url,xmlFilePath);
                    file.start();
                    CloseJframe();
                } catch (ParserConfigurationException | TransformerException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Please enter a valid file path ");
                }
            }

        });

    }

    public void CloseJframe(){
        super.dispose();
    }
}

