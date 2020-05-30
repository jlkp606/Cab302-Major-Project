import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class SimpleTableTest extends JFrame {

    private JTable table;
    private String[] columnNames;

    private String curr_billboard;
    private String getbillboard_path;

    int width = 2;
    int  height = 3;
    //int[][] array = new int[height][width];


    String[][] dataValues = new String[height][width];

    public SimpleTableTest() throws IOException, SAXException, ParserConfigurationException {
//        replace this with the server response
//        -------------------------------------------------------------------------------------------------------------
        String arr[] = new String[3];

        arr[0] = "billboard.xml";
        arr[1] = "xmlFile.xml";
        arr[2] = "newxmlfile.xml";

        String user[] = new String[3];

        user[0] = "Sid";
        user[1] = "John";
        user[2] = "Jack";


//arr_2 is path of the xml file
        String[][] arr_2 = new String[arr.length][10];
        for (int i = 0; i < arr.length; i++) {
            String xmlFilePath = System.getProperty("user.dir") + "/" + arr[i];
            ReadXMLFile file = new ReadXMLFile();
            arr_2[i] = file.read(xmlFilePath);
        }

//----------------------------------------------------------------------------------------------------


        columnNames = new String[]{" User ", " Billboard "};
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (j==0){
                    dataValues[i][j] = user[i];
                }

                if (j==1){
                    dataValues[i][j] = arr_2[i][1];
                }
            }
        }


        TableModel model = new myTableModel();

        table = new JTable();

        table.setRowHeight(50);

        table.setModel(model);

        JButton Delete = new JButton();
        Delete.setText("Delete");

        table.addMouseListener(new java.awt.event.MouseAdapter() {

                   public void mouseClicked(java.awt.event.MouseEvent e) {

                       int row = table.rowAtPoint(e.getPoint());

                       int col = table.columnAtPoint(e.getPoint());

                       curr_billboard = table.getValueAt(row, col).toString();
                       System.out.println(curr_billboard);

                       Delete.addActionListener(new ActionListener() {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                               JOptionPane.showMessageDialog(null, "Confirm to delete " + curr_billboard );
                               ((DefaultTableModel)table.getModel()).removeRow(row);
                           }
                       });

                       DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

                       DocumentBuilder documentBuilder = null;
                       try {
                           documentBuilder = documentFactory.newDocumentBuilder();
                       } catch (ParserConfigurationException ex) {
                           ex.printStackTrace();
                       }
                       for (int s = 0; s < arr.length; s++) {
                           String path = System.getProperty("user.dir") + "/" + arr[s];
                           Document document = null;
                           try {
                               document = documentBuilder.parse(path);
                           } catch (SAXException ex) {
                               ex.printStackTrace();
                           } catch (IOException ex) {
                               ex.printStackTrace();
                           }

                           NodeList list = document.getElementsByTagName("billboard");

                           for (int i = 0; i < list.getLength(); i++) {
                               Node node = list.item(i);
                               if (node.getNodeType() == Node.ELEMENT_NODE) {
                                   Element element = (Element) node;
                                   String message = element.getElementsByTagName("message").item(0).getTextContent();

                                   String title = message;
                                   if (curr_billboard.equals(title)) {
                                       getbillboard_path = path;
                                       System.out.println(path);
                                       break;
                                   }


                               }
                           }
                       }

                   }

               }
        );


        JLabel label = new JLabel("Choose any billboard");
        JPanel panel = new JPanel();
        panel.add(label);

        JButton Edit = new JButton();
        Edit.setText("Edit");

        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String xmlFilePath = getbillboard_path ;

                ReadXMLFile file = new ReadXMLFile();
                String arr1[] = new String[10];
                try {
                    arr1 = file.read(xmlFilePath);
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new Edit_billboard("Edit " + arr1[1] + " Billboard", arr1[1], arr1[4], arr1[0], arr1[2], arr1[5], arr1[6], arr1[7]);
                frame.setLocation(500, 300);
                frame.setSize(550, 550);
                frame.setVisible(true);

            }
        });


        JButton Preview = new JButton();
        Preview.setText("Preview");

//        JButton Delete = new JButton();
//        Delete.setText("Delete");


        JPanel panel_2 = new JPanel();
        panel_2.add(Edit, BorderLayout.LINE_START);
        panel_2.add(Preview, BorderLayout.CENTER);
        panel_2.add(Delete, BorderLayout.LINE_END);

        JFrame frame = new JFrame();
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panel_2, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

//        System.out.println(dataValues[1][1]);

    }

    public class myTableModel extends DefaultTableModel {

        myTableModel() {

            super(dataValues, columnNames);

            //System.out.println("Inside myTableModel");

        }

        public boolean isCellEditable(int row, int cols) {

            return false;

        }

    }

    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {

        new SimpleTableTest();

    }

}