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

import Server.Client;


public class Createbillboard extends JFrame {
//    private final Object Billboard;
    private JPanel mainPanel;
    private JEditorPane message;
    private JButton previewButton;
    private JButton uploadButton;
    private JCheckBox Data;
    private JTextField title_info;
    private JTextField background_colour;
    private JTextField title_colour;
    private JTextField message_colour;
    private JTextField image_data;
    private JTextField image_url;
    private JCheckBox URL;
    private JLabel Name_bb;
    private JTextField name_billboard;
    private JButton importBillboardButton;
    private JButton export_billboard;
    String xmlFilePath = System.getProperty("user.dir")+"/billboard.xml";


    public Createbillboard(String title, String token, String username) throws IOException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        String billboard_title = title_info.getText();
        String billboard_title_colour = title_colour.getText();
        String billboard_bg_colour = background_colour.getText();
        String billboard_message = message.getText();
        String billboard_message_colour = message_colour.getText();
        String billboard_image_data = image_data.getText();
        String billboard_image_url = image_url.getText();
        String billboard_name = name_billboard.getText();
        String user = username;

        image_data.setEnabled(false);
        image_url.setEnabled(false);
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
//                    System.out.println("null");
                }
                else {
                    Billboard billboard = new Billboard(billboard_name,user,billboard_bg_colour,billboard_title,billboard_title_colour, billboard_image_data,billboard_image_url,billboard_message,billboard_message_colour);
//                    send the  billboard info to server along with session token
                    try {
                        createBillboardRequest(token,billboard);
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
                    image_data.setEnabled(true);
                    image_url.setText("");


                }

                else{
                    URL.setEnabled(true);
                    image_data.setEnabled(false);

                }
            }
        });

        URL.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Data.setEnabled(false);
                    image_url.setEnabled(true);
                    image_data.setText("");
                }

                else{
                    Data.setEnabled(true);
                    image_url.setEnabled(false);
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

    public static void main(String[] args) throws IOException {

        String token = "12435642";
        String user = "Sid";
        JFrame frame = new Createbillboard("Create Billboard",token,user);
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
    }

    public static void createBillboardRequest(String token,Billboard billboard) throws IOException {
            Socket socket = Client.getClientSocket();
            HashMap<String , Object> request = new HashMap<String , Object>();
            request.put("type", "createBillboard");
            request.put("token", token);
            request.put("billboard", billboard);
            Client.sendRequest(socket , request);
    }

}
