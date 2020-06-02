import Server.Client;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static Server.Client.sendRequest;

public class removeScheduledBillboard extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JPanel mainPanel;

    public removeScheduledBillboard(String title,String token,String user) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // GUI window will close after Exist button on the top is pressed
        this.setContentPane(mainPanel);
        this.pack();
    }
    public static void RemoveBillboardFromSchedule(String token, Database.Schedule schedule) throws IOException, ClassNotFoundException{
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "removeBillboardFromSchedule");
        request.put("schedule", schedule);
        sendRequest(socket, request);
        socket.close();
    }

    public static void main(String[] args) {
        String token = "htdyrd";
        String user = "htdyrd";
        JFrame frame = new  removeScheduledBillboard ("String title", token, user);
        frame.setLocation(500, 300);
        frame.setSize(300, 250);
        frame.setVisible(true);
    }
}
