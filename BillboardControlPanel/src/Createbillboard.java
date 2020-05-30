import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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


    public Createbillboard(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

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
//
//
//                System.out.println(billboard_title);
//                System.out.println(billboard_title_colour);
//                System.out.println(billboard_bg_colour);
//                System.out.println(billboard_message);
//                System.out.println(billboard_message_colour);
//                System.out.println(billboard_image_data);
//                System.out.println(billboard_image_url);

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


//        Data.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//
//            }
//        });

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
//        URL.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
    }

    public static void main(String[] args) {


        JFrame frame = new Createbillboard("Create Billboard");
        frame.setLocation(500,300);
        frame.setSize(550,550);
        frame.setVisible(true);
        //title_info.getText();
    }


}
