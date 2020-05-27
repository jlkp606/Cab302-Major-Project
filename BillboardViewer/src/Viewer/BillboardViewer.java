package Viewer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class BillboardViewer {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        JFrame frame = new JFrame("Billboard Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        MouseListener mouseexit = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                frame.dispose();
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
        KeyListener escapeexit = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        };

        File file = new File("F:\\XMLFile.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("Employees.xml"));

        frame.addMouseListener(mouseexit);
        frame.addKeyListener(escapeexit);
        JLabel ErrorMessage = new JLabel("Error: No XML file found to display.");
        ErrorMessage.setFont(new Font("Century Schoolbook", Font.PLAIN, 48));
        ErrorMessage.setForeground(Color.WHITE);
        ErrorMessage.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(ErrorMessage);
        frame.getContentPane().setBackground(Color.BLUE);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setFocusable(true);

/*        HashMap<String, Object> request = new HashMap<>();
        request.put("display, billboard");
        Socket socket = getClientSocket();
        sendRequest(socket,request);
        HashMap<String,Object> response = getResponse(socket);
        Billboard billboard = res.get("billboard");
        socket.close();*/
    }
}
