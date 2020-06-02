import Database.Billboard;
import Server.Client;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Edit_billboard extends JFrame {
    private  String token;
    private Billboard billboard;
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

    public Edit_billboard(String title, String token, Database.Billboard billboard){
        super(title);
        this.token = token;
        this.billboard = billboard;
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
                String billboard_name = name_billboard.getText();

                Database.Billboard billboard = new Database.Billboard(billboard_name, user, billboard_bg_colour, billboard_title, billboard_title_colour, billboard_image_data, billboard_image_url, billboard_message, billboard_message_colour);
                if (billboard_name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill the name for billboard to continue ");
                } else {
                    try {

                        createBillboardRequest(token, billboard);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
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
                if(arr[6] == null ||arr[6].equals("")){
                    image_url.setEnabled(true);

                }

                else if(arr[7] == null ||arr[7].equals("")){
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

    public static void createBillboardRequest(String token,Database.Billboard billboard) throws IOException {
        Socket socket = Client.getClientSocket();
        HashMap<String , Object> request = new HashMap<String , Object>();
        request.put("type", "createBillboard");
        request.put("token", token);
        request.put("billboard", billboard);
        Client.sendRequest(socket , request);
    }
}
