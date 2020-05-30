package Viewer;
import Server.Client;
import Database.Billboard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.net.URL;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.HashMap;

public class BillboardGenerator {
    public static final String xmlFilePath = System.getProperty("user.dir") + "/xmlFile.xml";

    public <xmlFilePath> String[] read(String xmlFilePath) throws ParserConfigurationException, IOException, SAXException {
        String path = BillboardGenerator.xmlFilePath;
        String[] arr = new String[10];

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(path);

        NodeList list = document.getElementsByTagName("billboard");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String background = element.getAttribute("background");
                String message = element.getElementsByTagName("message").item(0).getTextContent();
                String info = element.getElementsByTagName("information").item(0).getTextContent();

                System.out.println(background);
                System.out.println(message);
                System.out.println(info);
                arr[0] = background;
                arr[1] = message;
                arr[2] = info;


            }
        }

        NodeList list_1 = document.getElementsByTagName("message");

        for (int i = 0; i < list_1.getLength(); i++) {
            Node node_1 = list_1.item(i);

            if (node_1.getNodeType() == Node.ELEMENT_NODE) {
                Element element_1 = (Element) node_1;
                String colour_message = element_1.getAttribute("colour");
                System.out.println(colour_message);
                //return colour_message;
                arr[4] = colour_message;

            }
        }


        NodeList list_2 = document.getElementsByTagName("information");

        for (int i = 0; i < list_2.getLength(); i++) {
            Node node_2 = list_2.item(i);

            if (node_2.getNodeType() == Node.ELEMENT_NODE) {
                Element element_2 = (Element) node_2;
                String colour_info = element_2.getAttribute("colour");
                System.out.println(colour_info);
                //return colour_info;
                arr[5] = colour_info;
            }
        }

        NodeList list_3 = document.getElementsByTagName("picture");

        for (int i = 0; i < list_3.getLength(); i++) {
            Node node_3 = list_3.item(i);

            if (node_3.getNodeType() == Node.ELEMENT_NODE) {
                Element element_3 = (Element) node_3;
                String image_data = element_3.getAttribute("data");
                String image_url = element_3.getAttribute("url");

                System.out.println(image_data);
                System.out.println(image_url);
                //return image_data;
                //return  image_url;
                arr[6] = image_data;
                arr[7] = image_url;

            }
        }


        return arr;

    }
    public static class TestBillboard implements Comparable<TestBillboard>, Serializable {

        private String BillboardBackgroundColour = "#0000FF";
        private String BillboardMessage = "Welcome to the ____ Corporation's Annual Fundraiser!";
        private String BillboardInformation = "Be sure to check out https://example.com/ for more information.";
        private String BillboardMessageColour = "#FFFF00";
        private String BillboardInformationColour = "#00FFFF";
        private byte[] Base64toImage = null;
        private String BillboardURLImage = "https://upload.wikimedia.org/wikipedia/en/6/67/Chocobo.png";

        @Override
        public int compareTo(TestBillboard o) {
            return 0;
        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException {

            /**Setting up the JFrame and JPanel*/
            JFrame BillboardFrame = new JFrame("Billboard Viewer");
            BillboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            BillboardFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            JPanel BillboardElements = new JPanel();
            BillboardElements.setLayout(new BoxLayout(BillboardElements,BoxLayout.PAGE_AXIS));
            /**Setting up a mouse listener to exit when the mouse is clicked*/
            MouseListener mouseExit = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    BillboardFrame.dispose();
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                }
            };
            /**setting up a key listener to exit when the escape key is released*/
            KeyListener escapeExit = new KeyListener() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {
                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {
                }

                @Override
                public void keyReleased(KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        BillboardFrame.dispose();
                    }
                }
            };

            /**adding the mouse and key listeners to the frame*/
            BillboardFrame.addMouseListener(mouseExit);
            BillboardFrame.addKeyListener(escapeExit);
            /**grabbing the billboard settings from the class grabbed from the server*/
            TestBillboard TestBillboard = new TestBillboard();
            JLabel BillboardMessage = new JLabel(TestBillboard.BillboardMessage);
            JLabel BillboardInformation = new JLabel("<html>" + TestBillboard.BillboardInformation + "</html>");
        /** ErrorMessage in the case that the class has no background colour input and is invalid*/
        Color BillboardBackgroundColour = null;
        try {
            BillboardBackgroundColour = Color.decode(TestBillboard.BillboardBackgroundColour);
            ;
        } catch (Exception NumberFormatException) {
            JLabel ErrorMessage = new JLabel("Error: File not found or invalid.");
            ErrorMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 48));
            ErrorMessage.setForeground(Color.WHITE);
            ErrorMessage.setHorizontalAlignment(JLabel.CENTER);
            BillboardFrame.addMouseListener(mouseExit);
            BillboardFrame.addKeyListener(escapeExit);
            BillboardFrame.getContentPane().add(ErrorMessage);
            BillboardFrame.getContentPane().setBackground(Color.BLUE);
            BillboardFrame.setUndecorated(true);
            BillboardFrame.setFocusable(true);
            BillboardFrame.setVisible(true);
        }
            Color BillboardMessageColour = Color.decode(TestBillboard.BillboardMessageColour);
            Color BillboardInformationColour = Color.decode(TestBillboard.BillboardInformationColour);

//            byte[] Base64toImage = Base64.getDecoder().decode(BillboardSettings[6]);
//            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
//            BufferedImage Base64Image = ImageIO.read(Base64Stream);
//            JLabel ConvertedBase64Image = new JLabel(new ImageIcon(Base64Image));
//
//            URL BillboardImageURL = new URL(BillboardSettings[7]);
//            BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
//            JLabel ConvertedURLImage = new JLabel(new ImageIcon(URLtoImage));

            /**setting a font for the billboard message and information*/
            BillboardMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 48));
            BillboardInformation.setFont(new Font("Century Schoolbook", Font.PLAIN, 28));

            /**setting colours for messages, information and background*/
            BillboardMessage.setForeground(BillboardMessageColour);
            BillboardInformation.setForeground(BillboardInformationColour);
            BillboardElements.setBackground(BillboardBackgroundColour);

            /**Show all elements present, Base64 image*/
            if (TestBillboard.BillboardMessage != "" && TestBillboard.BillboardInformation != "" && TestBillboard.Base64toImage != null){
                ByteArrayInputStream Base64Stream = new ByteArrayInputStream(TestBillboard.Base64toImage);
                BufferedImage Base64Image = ImageIO.read(Base64Stream);
                JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                /**set Element alignments*/
                BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                /**add elements*/
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(BillboardMessage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(ConvertedImage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(BillboardInformation);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show message and Base64 image, no information*/
            else if (TestBillboard.BillboardMessage != "" && TestBillboard.BillboardInformation == "" && TestBillboard.Base64toImage != null){
                ByteArrayInputStream Base64Stream = new ByteArrayInputStream(TestBillboard.Base64toImage);
                BufferedImage Base64Image = ImageIO.read(Base64Stream);
                JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                //set Element alignments
                BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(BillboardMessage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(ConvertedImage);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show information and Base64 image, no message*/
            else if (TestBillboard.BillboardMessage == "" && TestBillboard.BillboardInformation != "" && TestBillboard.Base64toImage != null){
                ByteArrayInputStream Base64Stream = new ByteArrayInputStream(TestBillboard.Base64toImage);
                BufferedImage Base64Image = ImageIO.read(Base64Stream);
                JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                //set Element alignments
                BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(ConvertedImage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(BillboardInformation);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show all elements present, URL image*/
            else if (TestBillboard.BillboardMessage != "" && TestBillboard.BillboardInformation != "" && TestBillboard.BillboardURLImage != ""){
                URL BillboardImageURL = new URL(TestBillboard.BillboardURLImage);
                BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
                JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                //set Element alignments
                BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(BillboardMessage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(ConvertedImage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(BillboardInformation);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show message and URL Image, no information*/
            else if (TestBillboard.BillboardMessage != "" && TestBillboard.BillboardInformation == "" && TestBillboard.BillboardURLImage != ""){
                URL BillboardImageURL = new URL(TestBillboard.BillboardURLImage);
                BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
                JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                //set Element alignments
                BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(BillboardMessage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(ConvertedImage);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show information and URL Image, no message*/
            else if (TestBillboard.BillboardMessage == "" && TestBillboard.BillboardInformation != "" && TestBillboard.BillboardURLImage != ""){
                URL BillboardImageURL = new URL(TestBillboard.BillboardURLImage);
                BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
                JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                //set Element alignments
                BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
                ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(ConvertedImage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(BillboardInformation);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show information and message, no image*/
            else if (TestBillboard.BillboardMessage != "" && TestBillboard.BillboardInformation != "" && (TestBillboard.Base64toImage == null|| TestBillboard.BillboardURLImage == "")){

                //set Element alignments
                BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
                BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(BillboardMessage);
                BillboardElements.add(Box.createVerticalGlue());
                BillboardElements.add(BillboardInformation);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show only message*/
            else if (TestBillboard.BillboardMessage != "" && TestBillboard.BillboardInformation == "" && (TestBillboard.Base64toImage == null|| TestBillboard.BillboardURLImage == "")){

                //set Element alignments
                BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(BillboardMessage);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show only information*/
            else if (TestBillboard.BillboardMessage == "" && TestBillboard.BillboardInformation != "" && (TestBillboard.Base64toImage == null|| TestBillboard.BillboardURLImage == "")){

                //set Element alignments
                BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(BillboardInformation);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show only Base64 Image*/
            else if (TestBillboard.BillboardMessage == "" && TestBillboard.BillboardInformation == "" && TestBillboard.Base64toImage != null){
                ByteArrayInputStream Base64Stream = new ByteArrayInputStream(TestBillboard.Base64toImage);
                BufferedImage Base64Image = ImageIO.read(Base64Stream);
                JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

                //set Element alignments
                ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(ConvertedImage);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }

            /**Show only URL Image*/
            else if (TestBillboard.BillboardMessage == "" && TestBillboard.BillboardInformation == "" && TestBillboard.BillboardURLImage != ""){
                URL BillboardImageURL = new URL(TestBillboard.BillboardURLImage);
                BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
                JLabel ConvertedImage = new JLabel(new ImageIcon(URLtoImage));

                //set Element alignments
                ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                //add elements
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
                BillboardElements.add(ConvertedImage);
                BillboardElements.add(Box.createRigidArea(new Dimension(0, 60)));
            }
            else {
                JLabel ErrorMessage = new JLabel("Error: File not found or invalid.");
                ErrorMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 48));
                ErrorMessage.setForeground(Color.WHITE);
                ErrorMessage.setHorizontalAlignment(JLabel.CENTER);
                BillboardFrame.getContentPane().add(ErrorMessage);
                BillboardFrame.getContentPane().setBackground(Color.BLUE);
            }

            /**finally adding all parts to the frame*/
            BillboardFrame.getContentPane().add(BillboardElements);
            BillboardFrame.setUndecorated(true);
            BillboardFrame.setVisible(true);
            BillboardFrame.setFocusable(true);

//        Socket socket = Client.getClientSocket();
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("type", "getBillboard");
//        Client.sendRequest(socket, request);
//        HashMap<String, Object> response = Client.getResponse(socket);
//        Billboard currentBillboard = (Billboard) response.get("billboard");
        }
    }
