import org.xml.sax.SAXException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class List_billboard {

    public List_billboard() throws IOException, SAXException, ParserConfigurationException {
        String arr[]=new String[3];
        arr[0] = "billboard.xml";
        arr[1] = "xmlFile.xml";
        arr[2] = "newxmlfile.xml";

        String [][] arr_2=new String[arr.length][10];
        for (int i = 0 ; i<arr.length; i++) {
            String xmlFilePath = System.getProperty("user.dir") + "/" + arr[i];
            ReadXMLFile file = new ReadXMLFile();
            arr_2[i] = file.read(xmlFilePath);

        }


        Object[][] data
                = {{"Sid",arr_2[0][1] },
                {"John", arr_2[1][1] },
                {"Jack", arr_2[2][1] }};
        String[] cols = {"User", "Billboard"};

        JTable table = new JTable(data, cols);
        table.setCellSelectionEnabled(true);
        ListSelectionModel select= table.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final String[] Data = {null};
        select.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int[] row = table.getSelectedRows();
                int[] columns = table.getSelectedColumns();
                for (int i = 0; i < row.length; i++) {
                    for (int j = 0; j < columns.length; j++) {
                        Data[0] = (String) table.getValueAt(row[i], columns[j]);
                        //System.out.println("Table element selected is: " + Data);
                    }
                }
            }


        });
        System.out.println("Table element selected is: " + Data[0]);
        //System.out.println("Table element selected is: " + Data);
        JLabel label = new JLabel("Choose any billboard");
        JPanel panel = new JPanel();
        panel.add(label);

        JButton Edit = new JButton();
        Edit.setText("Edit");
        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String xmlFilePath = System.getProperty("user.dir")+"/billboard.xml";

                ReadXMLFile file = new ReadXMLFile();
                String arr1[]=new String[10];
                try {
                    arr1 = file.read(xmlFilePath);
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }
                //public Edit_billboard(String title, String billboard_title, String billboard_title_colour, String billboard_bg_colour, String billboard_message, String billboard_message_colour, String billboard_image_data, String billboard_image_url, String s){

                JFrame frame = new Edit_billboard("Edit " + arr1[1] + " Billboard",arr1[1],arr1[4],arr1[0],arr1[2],arr1[5],arr1[6],arr1[7]);
                frame.setLocation(500,300);
                frame.setSize(550,550);
                frame.setVisible(true);

            }
        });
        JButton Preview = new JButton();
        Preview.setText("Preview");

        JButton Delete = new JButton();
        Delete.setText("Delete");

        JPanel panel_2 = new JPanel();
        panel_2.add(Edit,BorderLayout.LINE_START);
        panel_2.add(Preview,BorderLayout.CENTER);
        panel_2.add(Delete,BorderLayout.LINE_END);

        JFrame frame = new JFrame();
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panel_2, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new List_billboard();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}



