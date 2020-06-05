import Database.Billboard;
import Server.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;
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

    public Edit_billboard(String title, String token, Database.Billboard billboard){
        super(title);
        this.token = token;
        this.billboard = billboard;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

                JFrame BillboardFrame = new JFrame();
                JPanel BillboardElements = new JPanel();


                JLabel BillboardMessage = new JLabel();
                JLabel BillboardInformation = new JLabel();

                BillboardMessage.setText(billboard.getMessage());
                BillboardInformation.setText(billboard.getInfoMessage());
                /* ErrorMessage in the case that the class has no background colour input and is invalid*/
                Color BillboardBackgroundColour = null;
                try {
                    BillboardBackgroundColour = Color.decode(billboard.getColour());
                    ;
                } catch (Exception NumberFormatException) {
                    JLabel ErrorMessage = new JLabel("Error: File not found or invalid.");
                    ErrorMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 48));
                    ErrorMessage.setForeground(Color.WHITE);
                    ErrorMessage.setHorizontalAlignment(JLabel.CENTER);
                    BillboardFrame.getContentPane().add(ErrorMessage);
                    BillboardFrame.getContentPane().setBackground(Color.BLUE);
                    BillboardFrame.setUndecorated(true);
                    BillboardFrame.setFocusable(true);
                    BillboardFrame.setVisible(true);
                }
                Color BillboardMessageColour = null;
                Color BillboardInformationColour = null;
                try {
                    if (!billboard.getMessageColour().equals("")) {
                        BillboardMessageColour = Color.decode(billboard.getMessageColour());
                    }
                } catch(Exception exp){
                    System.out.println("not a valid color");
                }
                try{
                    if(!billboard.getInfoColour().equals("")){
                        BillboardInformationColour = Color.decode(billboard.getInfoColour());
                    }
                }catch(Exception exp){
                    System.out.println("not a valid color");
                }
                BillboardMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 52));
                BillboardInformation.setFont(new Font("Century Schoolbook", Font.PLAIN, 32));

                /*setting colours for messages, information and background*/
                BillboardMessage.setForeground(BillboardMessageColour);
                BillboardInformation.setForeground(BillboardInformationColour);
                BillboardElements.setBackground(BillboardBackgroundColour);

                /*Show all elements present, Base64 image*/
                if (!billboard.getMessage().equals("") &&
                        !billboard.getInfoMessage().equals("") &&
                        !billboard.getPictureData().equals("")){
                    byte[] Base64toImage = Base64.getDecoder().decode(billboard.getPictureData());
                    ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
                    BufferedImage Base64Image = null;
                    try {
                        Base64Image = ImageIO.read(Base64Stream);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                    /*set Element alignments*/
                    BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                    BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                    ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    /*add elements*/
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(BillboardMessage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(ConvertedImage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(BillboardInformation);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show message and Base64 image, no information*/
                else if (!billboard.getMessage().equals("") && billboard.getInfoMessage().equals("") && !billboard.getPictureData().equals("")){
                    byte[] Base64toImage = Base64.getDecoder().decode(billboard.getPictureData());
                    ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
                    BufferedImage Base64Image = null;
                    try {
                        Base64Image = ImageIO.read(Base64Stream);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                    //set Element alignments
                    BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                    ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(BillboardMessage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(ConvertedImage);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show information and Base64 image, no message*/
                else if (billboard.getMessage().equals("") &&
                        !billboard.getInfoMessage().equals("") &&
                        !billboard.getPictureData().equals("")){
                    byte[] Base64toImage = Base64.getDecoder().decode(billboard.getPictureData());
                    ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
                    BufferedImage Base64Image = null;
                    try {
                        Base64Image = ImageIO.read(Base64Stream);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                    //set Element alignments
                    BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                    ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(ConvertedImage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(BillboardInformation);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show all elements present, URL image*/
                else if (!billboard.getMessage().equals("") &&
                        !billboard.getInfoMessage().equals("") &&
                        !billboard.getPictureURL().equals("")){
                    java.net.URL BillboardImageURL = null;
                    try {
                        BillboardImageURL = new URL(billboard.getPictureURL());
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                    BufferedImage URLtoImage = null;
                    try {
                        URLtoImage = ImageIO.read(BillboardImageURL);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                    //set Element alignments
                    BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                    BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                    ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(BillboardMessage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(ConvertedImage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(BillboardInformation);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show message and URL Image, no information*/
                else if (!billboard.getMessage().equals("") &&
                        billboard.getInfoMessage().equals("") &&
                        !billboard.getPictureURL().equals("")){
                    URL BillboardImageURL = null;
                    try {
                        BillboardImageURL = new URL(billboard.getPictureURL());
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                    BufferedImage URLtoImage = null;
                    try {
                        URLtoImage = ImageIO.read(BillboardImageURL);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                    //set Element alignments
                    BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                    ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(BillboardMessage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(ConvertedImage);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show information and URL Image, no message*/
                else if (billboard.getMessage().equals("") &&
                        !billboard.getInfoMessage().equals("") &&
                        !billboard.getPictureURL().equals("")){
                    URL BillboardImageURL = null;
                    try {
                        BillboardImageURL = new URL(billboard.getPictureURL());
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                    BufferedImage URLtoImage = null;
                    try {
                        URLtoImage = ImageIO.read(BillboardImageURL);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                    //set Element alignments
                    BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                    ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(ConvertedImage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(BillboardInformation);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show information and message, no image*/
                else if (!billboard.getMessage().equals("") && !billboard.getInfoMessage().equals("") &&
                        (billboard.getPictureData().equals("")|| billboard.getPictureURL().equals(""))){

                    //set Element alignments
                    BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                    BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(BillboardMessage);
                    BillboardElements.add(Box.createVerticalGlue());
                    BillboardElements.add(BillboardInformation);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show only message*/
                else if (!billboard.getMessage().equals("") && billboard.getInfoMessage().equals("") &&
                        (billboard.getPictureData().equals("")|| billboard.getPictureURL().equals(""))){

                    //set Element alignments
                    BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(BillboardMessage);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show only information*/
                else if (billboard.getMessage().equals("") && !billboard.getInfoMessage().equals("") &&
                        (billboard.getPictureData().equals("")|| billboard.getPictureURL().equals(""))){

                    //set Element alignments
                    BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(BillboardInformation);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show only Base64 Image*/
                else if (billboard.getMessage().equals("") && billboard.getInfoMessage().equals("") &&
                        !billboard.getPictureData().equals("")){
                    byte[] Base64toImage = Base64.getDecoder().decode(billboard.getPictureData());
                    ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
                    BufferedImage Base64Image = null;
                    try {
                        Base64Image = ImageIO.read(Base64Stream);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                    //set Element alignments
                    ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(ConvertedImage);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }

                /*Show only URL Image*/
                else if (billboard.getMessage().equals("") && billboard.getInfoMessage().equals("") &&
                        !billboard.getPictureURL().equals("")){
                    URL BillboardImageURL = null;
                    try {
                        BillboardImageURL = new URL(billboard.getPictureURL());
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                    BufferedImage URLtoImage = null;
                    try {
                        URLtoImage = ImageIO.read(BillboardImageURL);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                    //set Element alignments
                    ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                    //add elements
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                    BillboardElements.add(ConvertedImage);
                    BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
                }
                else {
                    JLabel ErrorMessage = new JLabel("Error: File not found or invalid.");
                    ErrorMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 48));
                    ErrorMessage.setForeground(Color.WHITE);
                    ErrorMessage.setHorizontalAlignment(JLabel.CENTER);
                    BillboardFrame.getContentPane().add(ErrorMessage);
                    BillboardFrame.getContentPane().setBackground(Color.BLUE);
                }
                /*finally adding all parts to the frame*/

                BillboardFrame.getContentPane().add(BillboardElements);
                BillboardFrame.setVisible(true);
                BillboardFrame.setSize(1000,1000);

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
                try {

                    if(name_billboard.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Please fill the name for billboard to continue " );
                    }
                    else {
                        System.out.println(billboard.getbName());
                        System.out.println("in else");
                        String responseServer = Createbillboard.createBillboardRequest(token, billboard);

                        if(responseServer.equals("Success")){
                            CloseJframe();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, responseServer);

                        }
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

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
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JFrame importBillboard = new importBillboard(title_info, title_colour, background_colour, message, message_colour, image_data, image_url);
                        importBillboard.setVisible(true);
                        importBillboard.setSize(450, 150);
                        importBillboard.setLocation(450, 250);
//                importBillboard.dispose();
                    }
                });
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
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JFrame exportBillboard = new exportBillboard(billboard_title, billboard_title_colour, billboard_bg_colour, billboard_message, billboard_message_colour, billboard_image_data, billboard_image_url);
                        exportBillboard.setVisible(true);
                        exportBillboard.setSize(450, 150);
                        exportBillboard.setLocation(450, 250);
                    }
                });
            }
        });

    }

    public void CloseJframe(){
        super.dispose();
    }


}
