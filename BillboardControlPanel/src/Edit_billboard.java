import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Edit_billboard extends JFrame {
    private JPanel mainPanel;
    private JTextField title_info;
    private JTextField background_colour;
    private JButton uploadButton;
    private JButton previewButton;
    private JCheckBox Data;
    private JTextField message_colour;
    private JTextField title_colour;
    private JEditorPane message;
    private JTextField image_data;
    private JTextField image_url;
    private JCheckBox URL;

    public Edit_billboard(String title, String billboard_title, String billboard_title_colour, String billboard_bg_colour, String billboard_message, String billboard_message_colour, String billboard_image_data, String billboard_image_url){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        title_info.setText(billboard_title);
        background_colour.setText(billboard_bg_colour);
        message_colour.setText(billboard_message_colour);
        title_colour.setText(billboard_title_colour);
        image_data.setText(billboard_image_data);
        image_url.setText(billboard_image_url);
        message.setText(billboard_message);

        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("preview button");
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String billboard_title = title_info.getText();
                String billboard_title_colour = title_colour.getText();
                String billboard_bg_colour = background_colour.getText();
                String billboard_message = message.getText();
                String billboard_message_colour = message_colour.getText();
                String billboard_image_data = image_data.getText();
                String billboard_image_url = image_url.getText();


                //make an xml file
                CreateXMLFile file = new CreateXMLFile(billboard_title,billboard_title_colour,billboard_bg_colour,billboard_message,billboard_message_colour,billboard_image_data,billboard_image_url);
                try {
                    file.start();
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (TransformerException ex) {
                    ex.printStackTrace();
                }

                //send it to server


            }
        });


        Data.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        URL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        final String xmlFilePath = System.getProperty("user.dir")+"/billboard.xml";

        ReadXMLFile file = new ReadXMLFile();
        String arr1[]=new String[10];
        arr1 = file.read(xmlFilePath);
    //public Edit_billboard(String title, String billboard_title, String billboard_title_colour, String billboard_bg_colour, String billboard_message, String billboard_message_colour, String billboard_image_data, String billboard_image_url, String s){

        JFrame frame = new Edit_billboard("Edit " + arr1[1] + " Billboard",arr1[1],arr1[4],arr1[0],arr1[2],arr1[5],arr1[6],arr1[7]);
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
        //title_info.getText();
    }


}
