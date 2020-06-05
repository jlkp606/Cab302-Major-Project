package Viewer;
import Server.Client;
import Database.Billboard;

import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;
import java.net.URL;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.Timer;

import static Server.Client.getResponse;
import static Server.Client.sendRequest;

public class BillboardGenerator {


    public static Billboard GetCurrentBillboard() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();

        request.put("type", "getCurrentBillboard");
        sendRequest(socket, request);
        HashMap<String, Object> response = getResponse(socket);
        Billboard billboard = (Billboard) response.get("billboard");
        socket.close();

        return billboard;

    }

    public static JLabel[] createBillboardViewer( JLabel[] labels, JFrame BillboardFrame, JPanel BillboardElements, MouseListener mouseExit, KeyListener escapeExit) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        Billboard Billboard = GetCurrentBillboard();

        BillboardFrame.getContentPane().remove(labels[0]);
        BillboardFrame.getContentPane().remove(labels[1]);

        JLabel BillboardMessage = new JLabel();
        JLabel BillboardInformation = new JLabel();

        BillboardMessage.setText(Billboard.getMessage());
        BillboardInformation.setText(Billboard.getInfoMessage());
        /* ErrorMessage in the case that the class has no background colour input and is invalid*/
        Color BillboardBackgroundColour = null;
        try {
            BillboardBackgroundColour = Color.decode(Billboard.getColour());
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
        Color BillboardMessageColour = null;
        Color BillboardInformationColour = null;
        try {
            if (!Billboard.getMessageColour().equals("")) {
                BillboardMessageColour = Color.decode(Billboard.getMessageColour());
            }
        } catch(Exception e){
            System.out.println("not a valid color");
        }
        try{
            if(!Billboard.getInfoColour().equals("")){
                BillboardInformationColour = Color.decode(Billboard.getInfoColour());
            }
        }catch(Exception e){
            System.out.println("not a valid color");
        }

//            byte[] Base64toImage = Base64.getDecoder().decode(BillboardSettings[6]);
//            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
//            BufferedImage Base64Image = ImageIO.read(Base64Stream);
//            JLabel ConvertedBase64Image = new JLabel(new ImageIcon(Base64Image));
//
//            URL BillboardImageURL = new URL(BillboardSettings[7]);
//            BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
//            JLabel ConvertedURLImage = new JLabel(new ImageIcon(URLtoImage));

        /*setting a font for the billboard message and information*/
        BillboardMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 52));
        BillboardInformation.setFont(new Font("Century Schoolbook", Font.PLAIN, 32));

        /*setting colours for messages, information and background*/
        BillboardMessage.setForeground(BillboardMessageColour);
        BillboardInformation.setForeground(BillboardInformationColour);
        BillboardElements.setBackground(BillboardBackgroundColour);

        /*Show all elements present, Base64 image*/
        if (!Billboard.getMessage().equals("") &&
                !Billboard.getInfoMessage().equals("") &&
                !Billboard.getPictureData().equals("")){
            byte[] Base64toImage = Base64.getDecoder().decode(Billboard.getPictureData());
            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
            BufferedImage Base64Image = ImageIO.read(Base64Stream);
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
        else if (!Billboard.getMessage().equals("") && Billboard.getInfoMessage().equals("") && !Billboard.getPictureData().equals("")){
            byte[] Base64toImage = Base64.getDecoder().decode(Billboard.getPictureData());
            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
            BufferedImage Base64Image = ImageIO.read(Base64Stream);
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
        else if (Billboard.getMessage().equals("") &&
                !Billboard.getInfoMessage().equals("") &&
                !Billboard.getPictureData().equals("")){
            byte[] Base64toImage = Base64.getDecoder().decode(Billboard.getPictureData());
            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
            BufferedImage Base64Image = ImageIO.read(Base64Stream);
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
        else if (!Billboard.getMessage().equals("") &&
                !Billboard.getInfoMessage().equals("") &&
                !Billboard.getPictureURL().equals("")){
            URL BillboardImageURL = new URL(Billboard.getPictureURL());
            BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
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
        else if (!Billboard.getMessage().equals("") &&
                Billboard.getInfoMessage().equals("") &&
                !Billboard.getPictureURL().equals("")){
            URL BillboardImageURL = new URL(Billboard.getPictureURL());
            BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
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
        else if (Billboard.getMessage().equals("") &&
                !Billboard.getInfoMessage().equals("") &&
                !Billboard.getPictureURL().equals("")){
            URL BillboardImageURL = new URL(Billboard.getPictureURL());
            BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
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
        else if (!Billboard.getMessage().equals("") && !Billboard.getInfoMessage().equals("") &&
                (Billboard.getPictureData().equals("")|| Billboard.getPictureURL().equals(""))){

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
        else if (!Billboard.getMessage().equals("") && Billboard.getInfoMessage().equals("") &&
                (Billboard.getPictureData().equals("")|| Billboard.getPictureURL().equals(""))){

            //set Element alignments
            BillboardMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

            //add elements
            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
            BillboardElements.add(BillboardMessage);
            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
        }

        /*Show only information*/
        else if (Billboard.getMessage().equals("") && !Billboard.getInfoMessage().equals("") &&
                (Billboard.getPictureData().equals("")|| Billboard.getPictureURL().equals(""))){

            //set Element alignments
            BillboardInformation.setAlignmentX(Component.CENTER_ALIGNMENT);

            //add elements
            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
            BillboardElements.add(BillboardInformation);
            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
        }

        /*Show only Base64 Image*/
        else if (Billboard.getMessage().equals("") && Billboard.getInfoMessage().equals("") &&
                !Billboard.getPictureData().equals("")){
            byte[] Base64toImage = Base64.getDecoder().decode(Billboard.getPictureData());
            ByteArrayInputStream Base64Stream = new ByteArrayInputStream(Base64toImage);
            BufferedImage Base64Image = ImageIO.read(Base64Stream);
            JLabel ConvertedImage = new JLabel(new ImageIcon(Base64Image));

            //set Element alignments
            ConvertedImage.setAlignmentX(Component.CENTER_ALIGNMENT);

            //add elements
            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
            BillboardElements.add(ConvertedImage);
            BillboardElements.add(Box.createRigidArea(new Dimension(0, 135)));
        }

        /*Show only URL Image*/
        else if (Billboard.getMessage().equals("") && Billboard.getInfoMessage().equals("") &&
                !Billboard.getPictureURL().equals("")){
            URL BillboardImageURL = new URL(Billboard.getPictureURL());
            BufferedImage URLtoImage = ImageIO.read(BillboardImageURL);
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
        BillboardFrame.validate();
        BillboardFrame.repaint();
        labels = new JLabel[]{BillboardMessage, BillboardInformation};
        return labels;
    }


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException, NoSuchAlgorithmException {

        /*Setting up the JFrame and JPanel*/
        JFrame BillboardFrame = new JFrame("Billboard Viewer");
        BillboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BillboardFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel BillboardElements = new JPanel();
        BillboardElements.setLayout(new BoxLayout(BillboardElements,BoxLayout.PAGE_AXIS));

        /*Setting up a mouse listener to exit when the mouse is clicked*/
        MouseListener mouseExit = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                BillboardFrame.dispose();
                System.exit(0);
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
        /*setting up a key listener to exit when the escape key is released*/
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
                    System.exit(0);
                }
            }
        };

        /*adding the mouse and key listeners to the frame*/
        BillboardFrame.addMouseListener(mouseExit);
        BillboardFrame.addKeyListener(escapeExit);
        /*grabbing the billboard settings from the class grabbed from the server*/


        JLabel BillboardMessage = new JLabel();
        JLabel BillboardInformation = new JLabel();

        JLabel[] labels = {BillboardMessage, BillboardInformation};

        Timer timer = new Timer();
        TimerTask task = new UpdateBillboard(/*BillboardMessage, BillboardInformation,*/ labels, BillboardFrame, BillboardElements, mouseExit, escapeExit);
        timer.schedule(task, 0, 15000);

        BillboardFrame.setUndecorated(true);
        BillboardFrame.setVisible(true);
        BillboardFrame.setFocusable(true);
    }
}