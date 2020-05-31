import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JTextField name_billboard;
    private JButton importBillboardButton;
    private JButton export_billboard;
    String xmlFilePath = System.getProperty("user.dir")+"/billboard.xml";

    public Edit_billboard(String title,String token,Billboard billboard){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        String user = billboard.getUsername();

        title_info.setText( billboard.getbName());
        background_colour.setText(billboard.getColour());
        message_colour.setText(billboard.getMessage());
        title_colour.setText(billboard.getMessageColour());
        image_data.setText(billboard.getPictureData());
        image_url.setText(billboard.getPictureURL());
        message.setText(billboard.getInfoMessage());
        message_colour.setText(billboard.getInfoColour());
        name_billboard.setText(billboard.getbName());

        String billboard_title = title_info.getText();
        String billboard_title_colour = title_colour.getText();
        String billboard_bg_colour = background_colour.getText();
        String billboard_message = message.getText();
        String billboard_message_colour = message_colour.getText();
        String billboard_image_data = image_data.getText();
        String billboard_image_url = image_url.getText();
        String billboard_name = name_billboard.getText();

        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("preview button");
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if(billboard_name.equals("")){
                    JOptionPane.showMessageDialog(null, "Please fill the name for billboard to continue " );
                }
                else {
                    Billboard billboard = new Billboard(billboard_name,user,billboard_bg_colour,billboard_title,billboard_title_colour, billboard_image_data,billboard_image_url,billboard_message,billboard_message_colour);
//                    send the  billboard info to server along with session token
                }

            }
        });

        Data.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    URL.setEnabled(false);
                    image_url.setEnabled(false);

                }

                else{
                    URL.setEnabled(true);
                    image_url.setEnabled(true);
                }
            }
        });

        URL.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Data.setEnabled(false);
                    image_data.setEnabled(false);
                }

                else{
                    Data.setEnabled(true);
                    image_data.setEnabled(true);
                }
            }
        });

        importBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadXMLFile file = new ReadXMLFile();
                String arr[]=new String[10];
                try {
                    arr = file.read(xmlFilePath);
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }
                if(arr[6].equals("")){
//                    Data.setEnabled(false);
                    image_url.setEnabled(true);

                }

                else if(arr[7].equals("")){
//                    URL.setEnabled(false);
                    image_data.setEnabled(true);
                }
                title_info.setText(arr[1]);
                title_colour.setText( arr[4]);
                background_colour.setText(arr[0]);
                message.setText( arr[2]);
                message_colour.setText(arr[5]);
                image_data.setText(arr[6]);
                image_url.setText(arr[7]);


            }
        });

        export_billboard.addActionListener(new ActionListener() {
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
            }
        });

    }


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
//        final String xmlFilePath = System.getProperty("user.dir")+"/billboard.xml";
//
//        ReadXMLFile file = new ReadXMLFile();
//        String arr1[]=new String[10];
//        arr1 = file.read(xmlFilePath);
    //public Edit_billboard(String title, String billboard_title, String billboard_title_colour, String billboard_bg_colour, String billboard_message, String billboard_message_colour, String billboard_image_data, String billboard_image_url, String s){
        String token = "12435642";
        Billboard billboard = new Billboard("Sidd's Billboard" ,"Sid" ,"Blue","hard work",  "10",null,"hdgjfhgdlfaldgsfl","jhefhwldjfggfgwiuefgif","Black");

        JFrame frame = new Edit_billboard("Edit Billboard",token,billboard);
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
        //title_info.getText();
    }


}
