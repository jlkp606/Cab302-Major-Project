import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class importBillboard extends JFrame {
    private JPanel mainPanel;
    private JButton button1;
    private JTextField textField1;
    String[] arr;

    public importBillboard(JTextField title_info, JTextField title_colour, JTextField background_colour, JEditorPane message, JTextField message_colour, JTextField image_data, JTextField image_url) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String xmlFilePath = textField1.getText().trim();
                    ReadXMLFile file = new ReadXMLFile();
                    arr = file.read(xmlFilePath);
                    title_info.setText(arr[1]);
                    title_colour.setText(arr[4]);
                    background_colour.setText(arr[0]);
                    message.setText(arr[2]);
                    message_colour.setText(arr[5]);
                    image_data.setText(arr[6]);
                    image_url.setText(arr[7]);
                    if (arr[6] == null || arr[6].equals("")) {
                        image_url.setEnabled(true);

                    } else if (arr[7] == null || arr[7].equals("")) {
                        image_data.setEnabled(true);
                    }
                    CloseJframe();
                } catch (ParserConfigurationException | IOException | SAXException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Please enter a valid file path ");

                }

            }

        });

    }

    public void CloseJframe() {
        super.dispose();
    }
}
