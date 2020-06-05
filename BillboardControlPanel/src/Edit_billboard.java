import Database.Billboard;
import Server.Client;
import javax.swing.*;
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

    public static void createBillboardRequest(String token,Database.Billboard billboard) throws IOException {
        Socket socket = Client.getClientSocket();
        HashMap<String , Object> request = new HashMap<String , Object>();
        request.put("type", "createBillboard");
        request.put("token", token);
        request.put("billboard", billboard);
        Client.sendRequest(socket , request);
    }
}
