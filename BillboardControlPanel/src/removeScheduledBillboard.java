import Database.Schedule;
import Server.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static Server.Client.sendRequest;

public class removeScheduledBillboard extends JFrame {
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JButton button1;

    public removeScheduledBillboard(String title,String token,String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // GUI window will close after Exist button on the top is pressed
        this.setContentPane(mainPanel);
        this.pack();

        ArrayList<Database.Schedule> scheduleList = viewer_billboard_time.ViewSchedule( token);

        for(int i =0;i<scheduleList.size();i++){
            comboBox1.addItem(scheduleList.get(i).getBillboardName());
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user;
                String displayStartTime;
                String displayEndTime;
                String day;
                String repeat;
                String billboardName;

                String bbname= String.valueOf(comboBox1.getSelectedItem());
                for (int i = 0; i < scheduleList.size(); i++) {
                    billboardName = scheduleList.get(i).getBillboardName();
                    if(billboardName.equals(bbname)){
                        user = scheduleList.get(i).getUsername();
                        displayStartTime = scheduleList.get(i).getStartTime();
                        displayEndTime = scheduleList.get(i).getEndTime();
                        day = scheduleList.get(i).getDay();
                        repeat = scheduleList.get(i).getRepeat();
                        Database.Schedule schedule = new Database.Schedule(user, billboardName, displayStartTime, displayEndTime, day, repeat);

                        try {
                                String responseServer = RemoveBillboardFromSchedule(token, schedule);

                                if(responseServer.equals("Success")){
                                    CloseJframe();
                                    JFrame frame = new viewer_billboard_time(" Users", token, user);
                                    frame.setLocation(500, 300);
                                    frame.setSize(850, 650);
                                    frame.setVisible(true);
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, responseServer);

                                }

                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to Connect to server ");

                        }
                        break;
                    }
                }


            }
        });
    }

    public static String RemoveBillboardFromSchedule(String token, Schedule schedule) throws IOException, ClassNotFoundException{
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "removeBillboardFromSchedule");
        request.put("schedule", schedule);
        sendRequest(socket, request);
        HashMap<String , Object> res = Client.getResponse(socket);
        String message = (String) res.get("message");
        socket.close();
        return message;
    }

    public void CloseJframe(){
        super.dispose();
    }
}
