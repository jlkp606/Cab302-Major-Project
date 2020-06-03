import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import Server.Client;



public class Createbillboard extends JFrame {
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

/**
 *
 *
 */

    public Createbillboard(String title, String token, String username) throws IOException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

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
                String billboard_title = title_info.getText();
                String billboard_title_colour = title_colour.getText();
                String billboard_bg_colour = background_colour.getText();
                String billboard_message = message.getText();
                String billboard_message_colour = message_colour.getText();
                String billboard_image_data = image_data.getText();
                String billboard_image_url = image_url.getText();
                String billboard_name = name_billboard.getText();
                    Database.Billboard billboard = new Database.Billboard(billboard_name,user,billboard_bg_colour,billboard_title,billboard_title_colour, billboard_image_data,billboard_image_url,billboard_message,billboard_message_colour);
                    try {

                        if(name_billboard.getText().equals("")){
                            JOptionPane.showMessageDialog(null, "Please fill the name for billboard to continue " );
                        }
                        else {
                            System.out.println(billboard.getbName());
                            System.out.println("in else");
                            createBillboardRequest(token, billboard);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
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
                    URL.setSelected(true);
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
                    Data.setSelected(true);
                    Data.setEnabled(true);
                    image_url.setEnabled(false);
                }
            }
        });

        importBillboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame importBillboard = new importBillboard(title_info, title_colour, background_colour, message, message_colour, image_data, image_url);
                importBillboard.setVisible(true);
                importBillboard.setSize(450,150);
                importBillboard.setLocation(450,250);

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

                JFrame exportBillboard = new exportBillboard(  billboard_title,  billboard_title_colour,  billboard_bg_colour,  billboard_message, billboard_message_colour,  billboard_image_data,  billboard_image_url);
                exportBillboard.setVisible(true);
                exportBillboard.setSize(450,150);
                exportBillboard.setLocation(450,250);

            }
        });

    }

//    public static void main(String[] args) throws IOException {
//        String token ="ksjbdkg";
//        String user = "Sid";
//        JFrame frame = new Createbillboard("Create Billboard",token,user);
//        frame.setLocation(500,300);
//        frame.setSize(550,550);
//        frame.setVisible(true);
//    }
    public static void createBillboardRequest(String token, Database.Billboard billboard) throws IOException, ClassNotFoundException {
            System.out.println(token + " in createBillboard");
            Socket socket = Client.getClientSocket();
            HashMap<String , Object> request = new HashMap<String , Object>();
            request.put("type", "createBillboard");
            request.put("token", token);
            request.put("billboard", billboard);
            Client.sendRequest(socket , request);
            HashMap<String , Object> res = Client.getResponse(socket);
            System.out.println((String) res.get("message"));
            socket.close();
    }

}
